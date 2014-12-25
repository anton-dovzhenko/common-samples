Ext.define('LiveGridApp.view.QuoteGrid', {
    extend: 'Ext.grid.Panel'
    , requires: ['LiveGridApp.view.QuoteGridToolbar']
    , alias: 'widget.quotegrid'
    , tbar: {xtype : 'quotegridtoolbar'}
    , store:'Quotes'
    , columns: [
        {text: 'Id', width: 50, dataIndex: 'id', sortable: true, align: 'right', style: 'text-align: left'}
        , {text: 'Version', width: 90, dataIndex: 'version', sortable: true, align: 'right', style: 'text-align: left'}
        , {text: 'Creation date', width: 150, dataIndex: 'creationDate', sortable: true, xtype: 'datecolumn', format:'d M Y h:i:s'}
        , {text: 'Update date', width: 150, dataIndex: 'updateDate', sortable: true, xtype: 'datecolumn', format:'d M Y h:i:s'}
        , {text: 'Status', width: 90, dataIndex: 'status', sortable: true}
        , {text: 'Currency Pair', width: 100, dataIndex: 'ccyPair', sortable: true}
        , {text: 'Direction', width: 90, dataIndex: 'direction', sortable: true}
        , {text: 'Price', width: 120, dataIndex: 'price', sortable: true, align: 'right', style: 'text-align: left'}
        , {text: 'Base ccy amount', width: 150, dataIndex: 'amount', sortable: true, xtype: 'numbercolumn'
                , format:'0,000', align: 'right', style: 'text-align: left'}
        , {text: 'Requester id',  width: 120, dataIndex: 'requesterId', sortable: true, align: 'right', style: 'text-align: left'}
    ]
    , forceFit: true
    , split: true
});