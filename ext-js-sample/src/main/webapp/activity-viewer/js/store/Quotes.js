Ext.define('LiveGridApp.store.Quotes', {
    extend: 'Ext.data.Store'
    , requires: ['LiveGridApp.model.Quote']
    , model: 'LiveGridApp.model.Quote'
    , clearOnPageLoad: false
    , proxy: {
        type: 'ajax',
        url: 'rest/quotes.do',
        reader: {type: 'json', root: 'quotes'}
    }
    //, autoLoad: true
});