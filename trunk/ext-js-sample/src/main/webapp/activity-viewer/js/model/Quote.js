Ext.define('LiveGridApp.model.Quote', {
    extend: 'Ext.data.Model'
    , fields: [
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