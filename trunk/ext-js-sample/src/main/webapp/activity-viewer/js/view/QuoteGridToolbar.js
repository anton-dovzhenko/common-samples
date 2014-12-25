Ext.define('LiveGridApp.view.QuoteGridToolbar', {
    extend: 'Ext.toolbar.Toolbar'
    , alias: 'widget.quotegridtoolbar'
    , items  : [{
        xtype: 'tbtext',
        text: 'Sorting order:',
        reorderable: false
    }]
});