const WsConnection = {connected: false, url: "ws://localhost:5556"};

function createSocketAndLoadSettings() {

    WsConnection.ws = new WebSocket(WsConnection.url);

    WsConnection.ws.onopen = function (e) {
        console.log("[WS] Connected");
        WsConnection.connected = true;
        WsConnection.ws.send(serialize(
            {
                method: "`.api.getFilterSettings",
                params: true,
                incremental: false
            })
        );
    };


    WsConnection.ws.onmessage = function (e) {
        console.log("[WS] Received");
        const fileReader = new FileReader();
        fileReader.onload = function () {

            const data = deserialize(this.result);
            console.log(data);

            const topic = data['topic'];
            const payload = data['payload'];
            const snapshot = data['snapshot'];

            if (topic === '.api.getMarketData') {
                if (snapshot) {
                    createPlots(payload);
                } else {
                    updatePlots(payload);
                }

            } else if (topic === '.api.getFilterSettings') {
                createUi(payload)
            }
        };
        fileReader.readAsArrayBuffer(e.data);
    };


    WsConnection.ws.onerror = function (e) {
        console.error("[WS] Error");
        console.error(deserialize(e));
    };
}

