
Ext.application({
	name : 'ServiceMonitoringApp',

	launch : function() {
		
		Ext.define('Service', {
	        extend: 'Ext.data.Model',
	        fields: [
	              {name:'id', type: 'int'} 
                 , {name:'name', type: 'string'} 
                 , {name: 'status', type: 'string'}
                 , {name: 'server', type: 'string'}
	        ]
	    });
		
		var serviceStore = Ext.create('Ext.data.Store', {
	        model: 'Service'
	        , storeId: 'Service'
	        , autoLoad: 'true'
	        , proxy: {
	            type: 'ajax',
	            url: 'json/services.json',
	            reader: {type: 'json', root: 'services'}
	        }
	    });
		
		var serviceGrid = Ext.create('Ext.grid.Panel', {
	        store: serviceStore
	        , columns: [
	            {text: 'Name', flex: 1, dataIndex: 'name', sortable: true}
	            , {text: 'Status', flex: 0.5, dataIndex: 'status', sortable: true}
	            , {text: 'Server', flex: 1, dataIndex: 'server', sortable: true}
	        ]
	        , forceFit: true
	        , split: true
	        , region: 'north'
	        , listeners : {
	        	itemdblclick: function(dv, record, item, index, e) {
	        		serviceDetailsForm.getForm().setValues({
        				name: record.data.name
        				, status: record.data.status
        				, server: record.data.server
	        		});
	            }
	        }
	    });
		
		var serviceDetailsForm = Ext.create('Ext.form.Panel', {
		    title: ''
		    , bodyPadding: 10
		    , defaultType: 'textfield'
		    , forceFit: true
		    , items: [
		        {
		            fieldLabel: 'Name'
		            , name: 'name'
		            , disabled: true
		        }
		        , {
		            fieldLabel: 'Status'
		            , name: 'status'
		            , disabled: true
		        }
		        , {
		            fieldLabel: 'Server'
		            , name: 'server'
		            , disabled: true
		        }
		    ]
		});
		
		var serviceDetailsTabPanel = Ext.create('Ext.tab.Panel', {
	        activeTab: 0
	        , plain: true
	        , defaults :{
	            autoScroll: true,
	            bodyPadding: 10
	        },
	        items: [
	            {
	                title: 'Tab 1',
	                html: 'Chart 1'
	            }
	            , {
	                title: 'Tab 2',
	                html: 'Chart 2'
	            }
	            , {
	                title: 'Tab 3',
	                html: 'Chart 3'
	            }
	        ]
	    });

		Ext.create('Ext.container.Viewport', {
		    width: 500,
		    height: 300,
		    title: 'Service Monitoring',
		    layout: 'border',
		    items: [
				{
				    region: 'north'
				    , height: 25
				    , xtype: 'container'
				    , title: 'Service Monitoring' 
				}
				, {
				    title: 'Services List'
				    , region: 'west'
				    , flex: .5
				    , collapsible: true
		            , split: true
		            , titleCollapse: true
		            , layout: 'fit'
		            , items: [
		                serviceGrid
		            ]
				}
				, {
				    title: 'Service Details'
				    , region: 'center'
				    , layout: 'border'
				    , items: [
						{
							title: ''
						    , region: 'north'
						    , flex: 0.5
						    , layout: 'fit'
						    , items: [serviceDetailsForm]
						}
						, {
						    title: 'Center'
						    , region: 'center'
						    , flex: 0.5
						    , layout: 'fit'
						    , items: [serviceDetailsTabPanel]
						}
				    ]
				}
		    ]
		});

	}
});