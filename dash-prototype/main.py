import json

from dash import Dash, html, dcc, Output, Input, State, dash_table
from dash.dash_table.Format import Format, Group, Scheme, Trim
import numpy as np
import pandas as pd

trade_num = 24
client_trades = pd.DataFrame({
    'time': [pd.Timestamp('2022-01-02T12'), pd.Timestamp('2022-01-02T12:01'), pd.Timestamp('2022-01-02T12:02'),
             pd.Timestamp('2022-01-02T12:03:01'), pd.Timestamp('2022-01-02T12:03:02'), pd.Timestamp('2022-01-02T12:03:03'),
             pd.Timestamp('2022-01-02T12:03:01'), pd.Timestamp('2022-01-02T12:03:02'), pd.Timestamp('2022-01-02T12:03:03'),
             pd.Timestamp('2022-01-02T12:03:01'), pd.Timestamp('2022-01-02T12:03:02'), pd.Timestamp('2022-01-02T12:03:03'),
             pd.Timestamp('2022-01-02T12:03:01'), pd.Timestamp('2022-01-02T12:03:02'), pd.Timestamp('2022-01-02T12:03:03'),
             pd.Timestamp('2022-01-02T12:03:01'), pd.Timestamp('2022-01-02T12:03:02'), pd.Timestamp('2022-01-02T12:03:03'),
             pd.Timestamp('2022-01-02T12:03:01'), pd.Timestamp('2022-01-02T12:03:02'), pd.Timestamp('2022-01-02T12:03:03'),
             pd.Timestamp('2022-01-02T12:03:01'), pd.Timestamp('2022-01-02T12:03:02'), pd.Timestamp('2022-01-02T12:03:03'),
             ],
    'region': ['LON', 'LON', 'NYC', 'NYC', 'SIN', 'SIN', 'SIN', 'SIN', 'SIN', 'SIN', 'SIN', 'SIN',
               'LON', 'LON', 'NYC', 'NYC', 'SIN', 'SIN', 'SIN', 'SIN', 'SIN', 'SIN', 'SIN', 'SIN'],
    'account': ['ACC_1', 'ACC_1', 'ACC_1', 'ACC_2', 'ACC_2', 'ACC_2', 'BCC_1', 'BCC_1', 'BCC_1', 'ACC_1', 'ACC_1', 'ACC_1',
                'ACC_1', 'ACC_1', 'ACC_1', 'ACC_2', 'ACC_2', 'ACC_2', 'BCC_1', 'BCC_1', 'BCC_1', 'ACC_1', 'ACC_1', 'ACC_1'],
    'security': ['EUR/USD', 'AUD/USD', 'EUR/USD', 'AUD/USD', 'EUR/USD', 'AUD/USD', 'EUR/JPY', 'EUR/NOK', 'NZD/EUR', 'USD/JPY',
                 'USD/JPY', 'USD/JPY', 'EUR/USD', 'AUD/USD', 'EUR/USD', 'AUD/USD', 'EUR/USD', 'AUD/USD', 'EUR/JPY', 'EUR/NOK',
                 'NZD/EUR', 'USD/JPY', 'USD/JPY', 'USD/JPY'],
    'qtyUsd': [1e6, 1e6, 2e6, 2e6, 5e6, 5e6, 1e6, 1e6, 1e6, 2.5e6, 2.5e6, 2.5e6,
               1e6, 1e6, 2e6, 2e6, 5e6, 5e6, 1e6, 1e6, 1e6, 2.5e6, 2.5e6, 2.5e6],
    'PnL': np.random.uniform(low=-1000, high=2000, size=(trade_num,))
})

client_trades = pd.concat([client_trades] * 4)
exec_trades = client_trades.copy(deep=True)
exec_trades['account'] = 'autohedger'
exec_trades.drop('PnL', inplace=True, axis=1)
exec_trades['mtm5s'] = np.random.uniform(low=-100, high=100, size=(len(exec_trades),))
exec_trades['mtm30s'] = exec_trades['mtm5s'] * 0.8
exec_trades['mtm60s'] = exec_trades['mtm5s'] * 0.6
exec_trades['mtm5min'] = np.random.uniform(low=-350, high=350, size=(len(exec_trades),))

client_regions = sorted(client_trades['region'].unique())
client_securities = sorted(client_trades['security'].unique())
client_accounts = sorted(client_trades['account'].unique())

client_groups = ['time', 'region', 'account', 'security']
client_values = ['qtyUsd', 'PnL']

exec_groups = ['time', 'region', 'account', 'security']
exec_values = ['qtyUsd', 'mtm5s', 'mtm30s', 'mtm60s', 'mtm5min']

exec_regions = sorted(exec_trades['region'].unique())
exec_securities = sorted(exec_trades['security'].unique())
exec_accounts = sorted(exec_trades['account'].unique())

tabs_styles = {'height': '30px'}
inline_style = {'display': 'inline-block', 'minWidth': '300px', 'padding': '0px 50px 0px 20px'}


def get_column_config(name):
    conf = {'name': name, 'id': name}
    if name in 'time':
        conf['type'] = 'numeric'
    if name in 'qtyUsd':
        conf['type'] = 'numeric'
        conf['format'] = Format().group(True)
    if name in ['PnL', 'mtm5s', 'mtm30s', 'mtm60s', 'mtm5min']:
        conf['type'] = 'numeric'
        conf['format'] = Format(precision=2, scheme=Scheme.fixed, trim=Trim.yes).group(True)
    return conf


app = Dash(__name__)

app.layout = html.Div(children=[
    html.H3('Sample Reports', style={'textAlign': 'center', 'color': 'green'}),
    dcc.Tabs([
        dcc.Tab(label='Client Stats', children=[
            html.Br(),
            html.Div(style={'align-items': 'center', 'display': 'flex'}, children=[
                html.Label('Account'),
                dcc.Dropdown(client_accounts, [], multi=True, id='account-client-input', style=inline_style),
                html.Label('Region'),
                dcc.Dropdown(client_regions, [], multi=True, id='region-client-input', style=inline_style),
                html.Label('Security'),
                dcc.Dropdown(client_securities, [], multi=True, id='security-client-input', style=inline_style),
                html.Button(id='run-client-stats', n_clicks=0, children='Refresh')
            ]),
            html.Div(style={'align-items': 'center', 'display': 'flex'}, children=[
                html.Label('Group By'),
                dcc.Dropdown(client_groups, client_groups, multi=True, id='groupby-client-input', style=inline_style)
            ]),
            html.Div(style={'align-items': 'center', 'display': 'flex'}, children=[
                html.Label('Aggregate By'),
                dcc.Dropdown(client_values, client_values, multi=True, id='value-client-input', style=inline_style)
            ]),
            dash_table.DataTable(client_trades.to_dict('records'), [get_column_config(i) for i in client_trades.columns],
                                 id='client-table',
                                 fixed_rows={'headers': True},
                                 page_size=50, sort_action='native', sort_mode='multi', filter_action='native',
                                 style_table={'height': '900px', 'minWidth': '100%'},
                                 style_cell={'textAlign': 'left'},
                                 style_cell_conditional=[{'if': {'column_id': 'qtyUsd'}, 'textAlign': 'right'},
                                                         {'if': {'column_id': 'PnL'}, 'textAlign': 'right'}],
                                 style_data_conditional=[
                                     {'if': {'filter_query': '{PnL} < -200', 'column_id': 'PnL'}, 'color': 'tomato'},
                                     {'if': {'filter_query': '{PnL} > 200', 'column_id': 'PnL'}, 'color': 'green'}
                                 ]),
            html.Div(id='dummy-client-filter')
        ]),
        dcc.Tab(label='Exec Stats', children=[
            html.Br(),
            html.Div(style={'align-items': 'center', 'display': 'flex'}, children=[
                html.Label('Account'),
                dcc.Dropdown(exec_accounts, [], multi=True, id='account-exec-input', style=inline_style),
                html.Label('Region'),
                dcc.Dropdown(exec_regions, [], multi=True, id='region-exec-input', style=inline_style),
                html.Label('Security'),
                dcc.Dropdown(exec_securities, [], multi=True, id='security-exec-input', style=inline_style),
                html.Button(id='run-exec-stats', n_clicks=0, children='Refresh')
            ]),
            dash_table.DataTable(exec_trades.to_dict('records'), [get_column_config(i) for i in exec_trades.columns],
                                 id='exec-table',
                                 fixed_rows={'headers': True},
                                 page_size=50, sort_action='native', sort_mode='multi', filter_action='native',
                                 style_table={'height': '900px', 'minWidth': '100%'},
                                 style_cell={'textAlign': 'left'},
                                 style_cell_conditional=[{'if': {'column_id': x}, 'textAlign': 'right'}
                                                         for x in ('qtyUsd', 'mtm5s', 'mtm30s', 'mtm60s', 'mtm5min')],
                                 style_data_conditional=[
                                     {'if': {'filter_query': '{mtm5s} < -50', 'column_id': 'mtm5s'}, 'color': 'tomato'},
                                     {'if': {'filter_query': '{mtm5s} > 50', 'column_id': 'mtm5s'}, 'color': 'green'},
                                     {'if': {'filter_query': '{mtm5s} < -50', 'column_id': 'mtm30s'}, 'color': 'tomato'},
                                     {'if': {'filter_query': '{mtm5s} > 50', 'column_id': 'mtm30s'}, 'color': 'green'},
                                     {'if': {'filter_query': '{mtm5s} < -50', 'column_id': 'mtm60s'}, 'color': 'tomato'},
                                     {'if': {'filter_query': '{mtm5s} > 50', 'column_id': 'mtm60s'}, 'color': 'green'},
                                     {'if': {'filter_query': '{mtm5s} < -50', 'column_id': 'mtm5min'}, 'color': 'tomato'},
                                     {'if': {'filter_query': '{mtm5s} > 50', 'column_id': 'mtm5min'}, 'color': 'green'},
                                 ]),
            html.Div(id='dummy-exec-filter')

        ])
    ], style=tabs_styles),

])


@app.callback(
    [Output('dummy-client-filter', 'children'),
     Output('client-table', 'data'),
     Output('client-table', 'columns')],
    Input('run-client-stats', 'n_clicks'),
    State('account-client-input', 'value'),
    State('region-client-input', 'value'),
    State('security-client-input', 'value'),
    State('groupby-client-input', 'value'),
    State('value-client-input', 'value'))
def update_filter(n_clicks, account_val, region_val, security_val, group_cols, value_cols):
    print(account_val, region_val, security_val, group_cols, value_cols)
    filtered = client_trades
    if len(account_val) > 0:
        filtered = filtered[filtered['account'].isin(account_val)]
    if len(region_val) > 0:
        filtered = filtered[filtered['region'].isin(region_val)]
    if len(security_val) > 0:
        filtered = filtered[filtered['security'].isin(security_val)]

    filtered = filtered.groupby(group_cols, as_index=False)
    aggregation = {}
    for val_col in value_cols:
        aggregation[val_col] = np.sum
    filtered = filtered.agg(aggregation)

    report_config = json.dumps({'group_cols': group_cols,
                                'value_cols': value_cols,
                                'account': account_val,
                                'region': region_val,
                                'security': security_val})

    return f"Settings: {report_config}", \
           filtered.to_dict('records'), \
           [get_column_config(x) for x in filtered.columns.values.tolist()]


@app.callback(
    [Output(component_id='dummy-exec-filter', component_property='children'),
     Output(component_id='exec-table', component_property='data')],
    Input('run-exec-stats', 'n_clicks'),
    State('account-exec-input', 'value'),
    State('region-exec-input', 'value'),
    State('security-exec-input', 'value'))
def update_filter(n_clicks, account_val, region_val, security_val):
    print(account_val, region_val, security_val)
    filtered = exec_trades
    if len(account_val) > 0:
        filtered = filtered[filtered['account'].isin(account_val)]
    if len(region_val) > 0:
        filtered = filtered[filtered['region'].isin(region_val)]
    if len(security_val) > 0:
        filtered = filtered[filtered['security'].isin(security_val)]

    return json.dumps({'account': account_val, 'region': region_val, 'security': security_val}), filtered.to_dict('records')


if __name__ == '__main__':
    print('Start')
    print(client_trades)
    print(client_regions)
    print(client_securities)
    print(client_accounts)
    app.run_server(debug=True)
