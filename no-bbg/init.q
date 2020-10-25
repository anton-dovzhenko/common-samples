apiConnections: ([] handle: 0#0Ni; updateTime: 0#0Np; method: 0#`; params: ());
currencyPairConfig: flip `sym`indicativeRate`pipSize!(
    `AUDUSD`EURCZK`EURDKK`EURHUF`EURNOK`EURPLN`EURRON`EURSEK`EURUSD`GBPUSD`NZDUSD`USDCAD`USDCHF`USDCNH`USDHKD`USDJPY`USDMXN`USDRUB`USDSGD`USDTHB`USDTRY`USDZAR;
    0.72 26 7.46 321 9.55 4.3 4.67 10.3 1.12 1.3 0.67 1.32 1.01 6.62 7.85 113.83 20.21 66 1.37 32.82 5.35 14.08;
    4 3 4 2 4 4 4 4 4 4 4 4 4 4 4 2 3 2 4 3 4 3
);
marketData: update `g#sym from ([]time: 0#0Np; sym: 0#`; mid: 0#0n);
marketLevels: ([]sym: 0#`; period: 0#0N; low: 0#0n; high: 0#0n);
marketPeriods: ("5 min"; "30 min"; "1 hour"; "3 hours"; "6 hours"; "1 day")!5 30 60 180 360 1440;
.ui.lineWidth: (value marketPeriods)!1.5 1.3 1.1 1 0.8 0.7;
1440*60

{
    N: 24*60*60;
    ST: .z.p-1D;
    Times: ST + 00:00:01*til N;
    {[N;Times;cfg]`marketData insert (Times;N#cfg`sym;cfg[`indicativeRate]+sums (10 xexp neg 1+cfg`pipSize)*-0.5+N?1.0)}[N;Times] each currencyPairConfig;
    marketData:: `time xasc marketData;
 }`;



.api.getMarketData: {[params]
    .debug.api.getMarketData.params: params;
    SYMS: params`SYMS;
    minutes: `long$params`minutes;
    updateTime: $[`updateTime in key params;params`updateTime;0Np];
    ST: $[not null updateTime;updateTime;.z.p - 00:01*minutes];
    data: 0!select time, mid by sym from marketData where sym in `$SYMS, time>=ST;
    data: data lj `sym xkey select sym, low, high from marketLevels where period=minutes;
    data: update lineWidth: .ui.lineWidth minutes from data;
    data
 };


.api.getFilterSettings: {
    symbols: asc currencyPairConfig`sym;
    `symbols`timeLabels`timeValues!(symbols;key marketPeriods;marketPeriods)
 };


.z.ws: {
    0N!"[request] ", string .z.w;
    if[not 4h=type x;'"[IllegalArgumentException] WS call must be serialized"];
    x: -9!x;
    0N!x;
    if[not 99h=type x;'"[IllegalArgumentException] WS call must be dictionary"];
    if[not `method in key x;'"[IllegalArgumentException] WS call must have method"];
    if[not `params in key x;'"[IllegalArgumentException] WS message must have params"];
    if[not `incremental in key x;'"[IllegalArgumentException] WS message must have incremental"];
    .debug.x: x;
    response: x[`method]@x`params;
    now: .z.p;
    response: `topic`snapshot`payload!(x`method;1b;response);
    0N!response;
    if[x`incremental
        ; apiConnections:: delete from apiConnections where handle=.z.w, method=x`method
        ; `apiConnections insert (.z.w;now;x`method;x`params)];
    neg[.z.w] -8!response;
 };


.z.wc: {
    0N!"[Logout] Handles left: ", "," sv string key .z.W;
    apiConnections:: delete from apiConnections where not handle in key .z.W;
 };


//Scheduled functions
.z.ts: {
    now: .z.p;
    updateMarketData now;
    revalMarketLevels now;
    publishUpdates now;
 };


publishUpdates: {[now]
    {
        data: x[`method]@(x`params),enlist[`updateTime]!enlist x`updateTime;
        data: -8!`topic`snapshot`payload!(x`method;0b;data);
        neg[x`handle] data;
    } each apiConnections;
    apiConnections:: update updateTime: now from apiConnections;
 };


updateMarketData: {[now]
    snap: 0!select by sym from marketData;
    lastTime: exec max time from snap;
    gap: now-lastTime;
    N: (`ss$gap)+(60*`mm$gap)+60*60*`hh$gap;
    data: `time xasc raze {[N;lu]
        pipSize: exec first pipSize from currencyPairConfig where sym=lu`sym;
        ([]time: lu[`time]+00:00:01*1 +til N; sym: N#lu`sym; mid: lu[`mid]+sums (10 xexp neg 1+pipSize)*-0.5+N?1.0)
    }[N] each snap;
    marketData:: marketData, data;
 };


revalMarketLevels: {[now]
    data: select from marketData where time>=now - 00:01*max value marketPeriods;
    data: raze{[data;now;period] 
        0!update period:period from select low:min mid, high: max mid by sym from data where time>=now-00:01*period
    }[data;now] each value marketPeriods;
    marketLevels:: data;
 };


\t 5000

date:: .z.d

date
