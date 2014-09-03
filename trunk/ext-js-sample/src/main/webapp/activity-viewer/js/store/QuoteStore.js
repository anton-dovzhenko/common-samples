Ext.define('LiveGridApp.store.QuoteStore', {
    extend: 'Ext.data.Store'
    , requires: ['LiveGridApp.model.Quote']
    , model: 'LiveGridApp.model.Quote'
    , clearOnPageLoad: false
    , proxy: {
        type: 'ajax',
        url: 'rest/quotes.do',
        reader: {type: 'json', root: 'quotes'}
    }
});