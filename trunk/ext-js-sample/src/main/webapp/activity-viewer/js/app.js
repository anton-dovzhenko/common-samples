
Ext.application({
    requires: ['LiveGridApp.store.Quotes', 'LiveGridApp.view.QuoteGrid']
    , appFolder: 'activity-viewer/js'
    , name   : 'LiveGridApp'

    , launch : function() {

        var quoteStore = Ext.create('LiveGridApp.store.Quotes', {});
        var quoteGrid = Ext.create('LiveGridApp.view.QuoteGrid', {
            store: quoteStore
        });

        Ext.create('Ext.container.Viewport', {
            layout: 'border'
            , items: [
                {
                    title: 'Quotes Monitoring'
                    , region: 'center'
                    , layout: 'border'
                    , items: [
                        {
                            title: ''
                            , region: 'center'
                            , flex: 1
                            , layout: 'fit'
                            , items: [quoteGrid]
                        }
                    ]
                }
            ]
        });

        //***********************************
        //*** Atmosphere integration part ***
        //***********************************

        var socket = atmosphere;
        var transport = 'websocket';

        // We are now ready to cut the request
        var request = { url: 'atmosphere/quote',
            contentType : 'application/json',
            transport : 'websocket'
        };

        request.onOpen = function(response) {
            transport = response.transport;
            request.uuid = response.request.uuid;
            quoteStore.load();
            console.log('Transport: '+ transport);
            console.log('UUID: '+ response.request.uuid);
        };

        request.onClientTimeout = function(r) {
            console.log('ClientTimeout');
            setTimeout(function () {
                subSocket = socket.subscribe(request);
            }, request.reconnectInterval);
        }

        request.onMessage = function (response) {
            var quotes = JSON.parse(response.responseBody);
            var quoteCount = quotes.length;
            var ids = [];
            for (var i = 0; i < quoteCount; i++) {
                var quote = quotes[i];
                quoteStore.add(quote);
                ids.push(quote.id);
            }
            for (var i = 0; i < ids.length; i++) {
                var record = quoteStore.getById(ids[i]);
                var idx = quoteStore.indexOf(record);
                var row = quoteGrid.getView().getRow(idx);
                if (row != null) {
                    Ext.fly(row).highlight("FFFF33", {attr: 'backgroundColor', duration: 2000, alternate: true});
                }
            }
        }

        request.onClose = function(response) {
            console.log('Close');
        }

        request.onError = function(response) {
            console.log('Error');
        }

        var subSocket = socket.subscribe(request);

    }
});