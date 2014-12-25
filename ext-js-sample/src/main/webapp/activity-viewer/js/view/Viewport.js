Ext.define('LiveGridApp.view.Viewport', {
    extend: 'Ext.container.Viewport'
    , requires: ['LiveGridApp.view.QuoteGridToolbar', 'LiveGridApp.view.QuoteGrid']
    , layout: 'border'
    , initComponent: function() {
        this.items = {
            title: 'Quotes Monitoring'
            , region: 'center'
            , layout: 'border'
            , items: [
                {
                    title: ''
                    , region: 'center'
                    , flex: 1
                    , layout: 'fit'
                    , items: [{xtype : 'quotegrid'}]
                }
            ]
        };
        this.callParent();
    }

})