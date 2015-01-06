function CandleStickWidget(spec) {

    var instance = {};
    var lastBrushDomain;

    instance.getLastBrushDomain = function() {
        return lastBrushDomain;
    }

    instance.render = function(data, candleTimeInMs) {

        var totalHeight = spec.height + spec.margin.top + spec.margin.bottom
            + spec.margin.top2 + spec.margin.bottom2;
        var totalWidth = spec.width + spec.margin.left + spec.margin.right;
        var mainChartHeight = spec.height * (1 - spec.xFocusRelativeSize);
        var focusChartHeight = spec.height * spec.xFocusRelativeSize;

        var yMin = d3.min(data, function(d) {return d['Low'];}) - spec.yScaleMargin;
        var yMax = d3.max(data, function(d) {return d['High'];}) + spec.yScaleMargin;
        var xMin = d3.min(data, function(d) {return d['Time'];});
        var xMax = d3.max(data, function(d) {return d['Time'];});
        xMax = new Date(xMax.getTime() + candleTimeInMs);

        var xScale = d3.time.scale().range([0, spec.width]).domain([xMin, xMax]);
        var yScale = d3.scale.linear().range([mainChartHeight, 0]).domain([yMin, yMax]);
        var xFocusScale = d3.time.scale().range([0, spec.width]).domain([xMin, xMax]);
        var yFocusScale = d3.scale.linear().range([focusChartHeight, 0]).domain([yMin, yMax]);

        var xAxis = d3.svg.axis().scale(xScale).orient('bottom').ticks(5)
            .tickFormat(d3.time.format(spec.xAxisFormat)).tickSize(-mainChartHeight);
        var yAxis = d3.svg.axis().scale(yScale).orient('left').ticks(5)
            .tickFormat(d3.format(spec.yAxisFormat)).tickSize(-spec.width);
        var xFocusAxis = d3.svg.axis().scale(xFocusScale).orient('bottom').ticks(5)
            .tickFormat(d3.time.format(spec.xFocusAxisFormat));
        var yFocusAxis = d3.svg.axis().scale(yFocusScale).orient('left').ticks(2)
            .tickFormat(d3.format(spec.yAxisFormat)).tickSize(-spec.width);

        var svg = d3.select(spec.element).attr('height', totalHeight).attr('width', totalWidth);

        var brush = d3.svg.brush().x(xFocusScale)
            .extent([xMin, xMax])
            .on('brush', brushed);

        var div = d3.select('body').append('div').attr('class', 'tooltip');

        //Creating candlesticks
        var chartContainer = svg.append('g')
            .attr('transform', 'translate(' + spec.margin.left + ', ' + spec.margin.top + ')');
        createCandlesticks();

        var gy = chartContainer.append('g')
            .attr('class', 'y axis')
            .attr('transform', 'translate(0, 0)')
            .style('fill', 'steelblue')
            .call(yAxis);
        gy.selectAll('g').filter(function(d) { return d; })
            .classed('minor', true);

        var gx = chartContainer.append('g')
            .attr('class', 'x axis')
            .attr('transform', 'translate(' + 0  + ', ' + (mainChartHeight) + ')')
            .style('fill', 'steelblue')
            .call(xAxis);
        gx.selectAll('g').filter(function(d) { return d; })
            .classed('minor', true);

        //Create Focus Chart
        var chartFocusContainer = svg.append('g')
            .attr('transform', 'translate(' + spec.margin.left + ', '
            + (spec.margin.top + spec.margin.bottom + mainChartHeight) + ')');
        var line = d3.svg.line()
            .x(function(d) { return xFocusScale(new Date(d['Time'].getTime() + candleTimeInMs/2)); })
            .y(function(d) { return yFocusScale((d['High'] + d['Low']) / 2 ); });
        chartFocusContainer.append('path')
            .datum(data)
            .attr('class', 'line')
            .attr('d', line);
        var gfy = chartFocusContainer.append('g')
            .attr('class', 'y axis')
            .style('fill', 'steelblue')
            .call(yFocusAxis);
        gfy.selectAll('g').filter(function(d) { return d; })
            .classed('minor', true);

        var gfx = chartFocusContainer.append('g')
            .attr('class', 'x axis')
            .attr('transform', 'translate(0, ' + focusChartHeight + ')')
            .style('fill', 'steelblue')
            .call(xFocusAxis);
        gfx.selectAll('g').filter(function(d) { return d; })
            .classed('minor', true);

        //debugger;
        chartFocusContainer.append('g')
            .attr('class', 'x brush')
            .call(brush)
            .selectAll('rect')
            .attr('y', -6)
            .attr('height', focusChartHeight + 7);

        function createCandlesticks() {
            var xWidth = spec.width / (xScale.domain()[1] - xScale.domain()[0]) * candleTimeInMs;
            //var xWidth = (xScale(xMax) - xScale(xMin)) / data.length;

            //Adding candlesticks
            var g = chartContainer.selectAll('g').data(data).enter()
                .append('g')
                .attr('class', 'candlestick')
                .attr('style', function(d) {return getCandleGroupStyle(d)});
            g.append('rect')
                .attr('x', 0)
                .attr('style', 'stroke:white;stroke-width:2;');
            g.append('line')
                .attr('y1', 0)
                .attr('stroke-width', 1);
            //Update candlesticks size and position
            updateCandlesticks();

            g.on('mouseover', function(d) {
                var candle = d3.select(this);
                candle.attr('style', 'fill:' + spec.highlightColor + ';stroke:' + spec.highlightColor + ';');
                var candleX = d3.transform(candle.attr('transform')).translate[0];
                var candleY = d3.transform(candle.attr('transform')).translate[1];
                div.style('display', 'block');
                if (candleX < 85) {
                    var left = (candleX + spec.margin.left + xWidth + 10);
                } else {
                    left = (candleX + spec.margin.left - 95);
                }
                div.style('left', left + 'px');
                div.style('top', (candleY + spec.margin.top) + 'px');
                div.html(d3.time.format(spec.labelTimeFormat)(d['Time'])
                + '<br/>Open: ' + d['Open'] + '<br/>High: ' + d['High']
                + '<br/>Low: ' + d['Low'] + '<br/>Close: ' + d['Close']);
            });
            g.on('mouseout', function(d) {
                d3.select(this).attr('style', getCandleGroupStyle(d));
                div.style('display', 'none');
            });
        }

        function updateCandlesticks() {
            var xWidth = spec.width / (xScale.domain()[1] - xScale.domain()[0]) * candleTimeInMs;
            var g = chartContainer.selectAll('.candlestick');
            g.attr('transform', function(d) {return 'translate(' + xScale(d['Time']) + ', ' + yScale(d['High'])+ ')'});
            g.attr('visibility', function(d) { return isCandleVisible(xScale, candleTimeInMs, d) ? 'visible' : 'hidden';});
            g.selectAll('rect').attr('width', xWidth)
                .attr('y', function(d) {return Math.min(yScale(d['Open']), yScale(d['Close'])) - yScale(d['High']);})
                .attr('height', function(d) {
                    return Math.abs(yScale(d['Open']) - yScale(d['Close']));
                })
            ;
            g.selectAll('line').attr('width', xWidth)
                .attr('x1', xWidth / 2)
                .attr('x2', xWidth / 2)
                .attr('y2', function(d) {return - yScale(d['High']) + yScale(d['Low']);});
        }

        function brushed() {
            lastBrushDomain = brush.empty() ? xScale.domain() : brush.extent();
            xScale.domain(lastBrushDomain);
            var filteredDate = data.filter(function(d) { return isCandleVisible(xScale, candleTimeInMs, d); });
            var yMin = d3.min(filteredDate, function(d) {return d['Low'];}) - spec.yScaleMargin;
            var yMax = d3.max(filteredDate, function(d) {return d['High'];}) + spec.yScaleMargin;
            yScale.domain([yMin, yMax]);
            chartContainer.select('.x.axis').call(xAxis)
                .selectAll('g').filter(function(d) { return d; })
                .classed('minor', true);
            chartContainer.select('.y.axis').call(yAxis)
                .selectAll('g').filter(function(d) { return d; })
                .classed('minor', true);
            updateCandlesticks();
        }

        instance.setBrushDomain = function(xMin, xMax) {
            brush.extent([xMin, xMax]);
            brush(d3.select('.brush').transition());
            brushed();
        };

        instance.setZoomBrushDomain = function(zoom) {
            var dateFormat = d3.time.format('%Y-%m-%d');
            var xTempMax = xScale.domain()[1];
            var temp = new Date(xTempMax.getTime());
            if (zoom == '1d') {
                temp = d3.time.day.offset(temp, -2);
            } else if(zoom == '5d' ) {
                temp = d3.time.day.offset(temp, -5);
            } else if (zoom == '1m') {
                temp = d3.time.month.offset(temp, -1);
            } else if (zoom == '3m') {
                temp = d3.time.month.offset(temp, -3);
            } else if (zoom == '6m') {
                temp = d3.time.month.offset(temp, -6);
            } else if (zoom == 'YTD') {
                temp = new Date(xMax);
                temp.setMonth(0, 1);
                xTempMax = new Date(xMax);
            }
            var xTempMin = new Date(Math.max(temp.getTime(), xMin.getTime()));
            instance.setBrushDomain(xTempMin, xTempMax);
        };

        return instance;
    };

    function getCandleGroupStyle(d) {
        if (d['Close'] >= d['Open']) {
            return 'fill:' + spec.upColor + ';stroke:' + spec.upColor + ';';
        } else {
            return 'fill:' + spec.downColor + ';stroke:' + spec.downColor + ';';
        }
    }

    function isCandleVisible(xScale, candleTimeInMs, d) {
        return d['Time'] >= xScale.domain()[0] && d['Time'].getTime() + candleTimeInMs <= xScale.domain()[1].getTime();
    }

    return instance;
}