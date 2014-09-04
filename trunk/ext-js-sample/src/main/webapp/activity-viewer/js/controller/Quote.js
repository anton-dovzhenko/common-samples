Ext.define('LiveGridApp.controller.Quote', {
    extend: 'Ext.app.Controller'
    , requires: ['LiveGridApp.view.QuoteGrid']
    //, views:  ['QuoteGrid']
    , models: ['Quote']

    , init: function() {
        var quoteGrid = this.getQuoteGrid();
        var quoteStore = this.getQuotesStore();

        console.log(Ext.getClassName(quoteGrid));
        console.log(Ext.getClassName(quoteStore));

        for (var m in quoteGrid) {
            if (typeof quoteGrid[m] == "function") {
                console.log(m);
            }
        }

        var socket = atmosphere;
        var transport = 'websocket';

        // We are now ready to cut the request
        var request = { url: '/atmosphere/quote',
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
            for (var i = 0; i < quoteCount; i++) {
                var quote = quotes[i];
                quoteStore.insert(0, quote);
                quoteGrid.highlightRow(quote.id)
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
