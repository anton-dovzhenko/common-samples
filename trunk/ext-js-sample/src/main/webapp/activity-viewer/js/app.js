
Ext.application({
    name   : 'LiveGridApp',

    launch : function() {

        Ext.define('Quote', {
            extend: 'Ext.data.Model',
            fields: [
                {name:'id', type: 'int'}
                , {name:'version', type: 'int'}
                , {name:'creationDate', type: 'date', dateFormat: 'time'}
                , {name: 'updateDate', type: 'date', dateFormat: 'time'}
                , {name: 'status', type: 'string'}
                , {name: 'ccyPair', type: 'string'}
                , {name: 'direction', type: 'string'}
                , {name: 'price', type: 'float'}
                , {name: 'amount', type: 'float'}
                , {name: 'requesterId', type: 'int'}
            ]
        });

        var quoteStore = Ext.create('Ext.data.Store', {
            model: 'Quote'
            , storeId: 'Quote'
            , autoLoad: 'true'
            , proxy: {
                type: 'ajax',
                url: 'rest/quotes.do',
                reader: {type: 'json', root: 'quotes'}
            }
        });

        var quoteGrid = Ext.create('Ext.grid.Panel', {
            store: quoteStore
            , columns: [
                {text: 'Id', width: 50, dataIndex: 'id', sortable: true, align: 'right', style: 'text-align: left'}
                , {text: 'Version', width: 90, dataIndex: 'version', sortable: true, align: 'right', style: 'text-align: left'}
                , {text: 'Creation date', width: 150, dataIndex: 'creationDate', sortable: true, xtype: 'datecolumn', format:'d M Y h:i:s'}
                , {text: 'Update date', width: 150, dataIndex: 'updateDate', sortable: true, xtype: 'datecolumn', format:'d M Y h:i:s'}
                , {text: 'Status', width: 90, dataIndex: 'status', sortable: true}
                , {text: 'Currency Pair', width: 100, dataIndex: 'ccyPair', sortable: true}
                , {text: 'Direction', width: 90, dataIndex: 'direction', sortable: true}
                , {text: 'Price', width: 120, dataIndex: 'price', sortable: true, align: 'right', style: 'text-align: left'}
                , {text: 'Base ccy amount', width: 150, dataIndex: 'amount', sortable: true, xtype: 'numbercolumn', format:'0,000', align: 'right', style: 'text-align: left'}
                , {text: 'Requester id',  width: 120, dataIndex: 'requesterId', sortable: true, align: 'right', style: 'text-align: left'}
            ]
            , forceFit: true
            , split: true
        });

        Ext.create('Ext.container.Viewport', {
            width: 500,
            height: 300,
            layout: 'border',
            items: [
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

    }
});