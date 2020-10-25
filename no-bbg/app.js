const reportSettings = {minutes: 5, symbols: []};

function _getSymbolColumns(symbols) {
    return symbols.map(function(x) {return {view: "toggle", minWidth: 75, name: x, label: x}});
}


function _getTimeOptions(timeLabels) {
    return timeLabels.map(function(x) {return {value: x}});
}


function createUi(settings) {

    reportSettings.settings = settings;
    const filterPanelElements = new Array(2);
    filterPanelElements[0] = {id: "symbolPanel",
        cols: [
            {view: "label", label: "Instruments:", width: 100},
            {rows: _gridLayout(_getSymbolColumns(settings["symbols"]), 11)}
        ]
    };
    filterPanelElements[1] = {
        cols: [
            {view: "label", label: "Time:", width: 100},
            {view: "segmented", id: "timeFilter", options: _getTimeOptions(settings["timeLabels"])},
            {view: "button", label: "Refresh", type: "danger", width: 100, click: refreshBtnClickHandler}
        ]
    };

    const filterPanel = {view: "form", id:"formView", elements: filterPanelElements};
    const portletPanel = {
        view: "scrollview",
        id: "chartScrollPanel",
        body: {view: "flexlayout", id: "flexlayout", cols: []}};

    const ui_schema = {
        view: "layout",
        responsive: true,
        rows: [filterPanel, portletPanel]
    };
    webix.ui(ui_schema);
    webix.ui.fullScreen();

}



function refreshBtnClickHandler() {
    const flexlayout = $$('flexlayout');
    const values = $$('formView').getValues();
    const periodVal = reportSettings.settings.timeValues[$$('timeFilter').getValue()];
    const symbols = [];


    for (let key in values) {
        if (values.hasOwnProperty(key)) {
            if (values[key] === 1) {
                symbols.push(key);
            }
        }
    }
    //TODO: check if Plotly.purge must be invoked
    const children = flexlayout.getChildViews();
    for (let i = 0; i < children.length; i++) {
        flexlayout.removeView(children[i]);
    }
    flexlayout.reconstruct();
    WsConnection.ws.send(serialize(
        {
            method: "`.api.getMarketData",
            params: {SYMS: symbols, minutes: periodVal},
            incremental: true
        })
    );

    reportSettings.minutes = periodVal;
    reportSettings.symbols = symbols;


}


function createPlots(data) {
    const flexlayout = $$('flexlayout');
    for (let i = 0; i < data.length; i++) {
        const item = data[i];
        const divId = _getPlotId(item["sym"]);
        flexlayout.addView({template: "<div id='" + divId + "' class='bigChart'></div>", height: 250, width: 400});
        const plotData = [{type: "line", line: {width: item["lineWidth"]}, x: item["time"], y: item["mid"]}];
        Plotly.newPlot(divId, plotData,
            {
                title: item["sym"],
                xaxis: {tickformat: "%H:%M", tickfont: {size: 9}},
                yaxis: {tickfont: {size: 9}},
                margin: {l: 50, r: 20, t: 25, b: 20}
            });
    }
}


function updatePlots(data) {
    const flexlayout = $$('flexlayout');
    for (let i = 0; i < data.length; i++) {
        const item = data[i];
        const divId = _getPlotId(item["sym"]);
        const lastTime = _last(item["time"]);
        const cutTime = new Date(lastTime.getTime() - reportSettings.minutes * 60 * 1000);
        //TODO: cut off data and redraw the chart and when overall range >= 2 * visible range
        Plotly.extendTraces(divId, {x: [item["time"]], y: [item["mid"]]}, [0]);
        Plotly.relayout(divId, {"xaxis.range": [cutTime, lastTime], "yaxis.range": _multiplyRange(item["low"], item["high"], 1.1)});
    }
}


function _getPlotId(symbol) {
    return symbol + "Chart";
}


/******************************
Helper functions
 *****************************/
function _getRandomWalk(start, pipSize, size) {
    const rw = new Array(size);
    rw[0] = start;
    for (let i = 1; i < size; i++) {
        rw[i] = rw[i - 1] + pipSize * (Math.random() - 0.5);
    }
    return rw;
}


function _til(size) {
    const array = new Array(size);
    for (let i = 0; i < size; i++) {
        array[i] = i;
    }
    return array;
}


function _last(array) {
    return array[array.length - 1];
}


function _multiplyRange(a, b, m) {
    const d = 0.5 * (m - 1) * (b - a);
    return [a - d, b + d];
}


function _gridLayout(elements, maxCount) {
    const rowCount = Math.ceil(elements.length / maxCount);
    let e = 0;
    const rows = [];
    for (let i = 0; i < rowCount; i++) {
        let row = [];
        rows.push({cols: row});
        for (j = 0; j < maxCount; j++) {
            if (e === elements.length) {
                row.push({});
            } else {
                row.push(elements[e]);
                e++;
            }
        }
    }
    return rows;
}