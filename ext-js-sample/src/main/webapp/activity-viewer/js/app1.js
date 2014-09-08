Ext.application({
    name: 'LiveGridApp'
    , appFolder: 'activity-viewer/js'
    , autoCreateViewport: true
    , models: ['Quote']
    , stores: ['Quotes']
    , controllers: ['Quote']
});