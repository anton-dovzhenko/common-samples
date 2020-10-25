initSchema: {
    now: .z.p;
    marketData:: update `s#time, `g#sym from flip `time`sym`bid`ask ! (2#now;`AUDUSD`EURUSD;0.759 1.059;0.75901 1.05901);
    subscriptions:: 2!flip `handle`topic`function`updateTime`params ! (`int$();`$();`$();`timestamp$();());
 };


updateMarketData: {sendUpdateToSubscribers
      now: .z.p; 
      data: 0!select last bid by sym from marketData;
      data: update bid: bid + ((count i)?0.0001)-0.00005 from data;
      data: update ask: bid * 1 + (count i)?0.0001 from data;
      data: update time: now from data;
      data: (cols marketData) xcols data;
      marketData,: data;
 };
 
 
sendUpdateToSubscribers: {
    {[dict]
        neg[dict`handle] -8!executeApi `function`topic`params 
            ! (dict`function;`$string[dict`topic], ".update";dict[`params], enlist[`time]!enlist dict`updateTime);
         }'[0!subscriptions]; 
    subscriptions:: update updateTime: .z.p from subscriptions;
 };
 
 
.api.getSyms: {
    `NA, asc exec distinct sym from marketData    
 };
 
 
.api.getSymData: {[dict]   
    whereSpec: enlist (=;`sym;enlist dict`sym);
    if[`time in key dict; whereSpec,: enlist (>;`time;dict`time)];
    flip ?[marketData;whereSpec;0b;()]
 };
 
 
.z.ts: {
    updateMarketData[];    
    sendUpdateToSubscribers[];
 };
 
 
executeApi: {
    0N!"executeApi";
    0N!.z.w;
    0N!x`function;
    0N!x`topic;
    0N!.z.p;
    0N!x`params;
     if[`update in key x; if[x`update; `subscriptions upsert (.z.w;x[`function];x`topic;.z.p;x`params)]];
    `topic`response ! (x[`topic];x[`function]@x[`params])
 };
 
 
.z.wc: {
    HANDLE: .z.w;
    0N!"[Connection Closed] ", string HANDLE;
    subscriptions:: delete from subscriptions where handle=handle;
 };


.z.ws: {
    //deserialize if x is byte stream
    if[4=type x; x: -9!x];
    0N!x; 
    result: $[-10 = type x;eval parse x; executeApi x];
    0N!result; 
    neg[.z.w] -8!result;
 };


initSchema[];
\t 5000
