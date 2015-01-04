//none|daily|weekly|monthly|quarterly|annual
/**
 *
 * @param trimStart
 * @param trimEnd
 * @param ticker - stock ticker
 * @param collapse - aggregating period none|daily|weekly|monthly|quarterly|annual
 * @returns {string} - url to access quandl stock data
 */
function getQuandlUrl(trimStart, trimEnd, ticker, collapse) {
    var url = 'https://www.quandl.com/api/v1/datasets/WIKI/';
    var token = 'xxsYxkwJkWzuH2zDpQNz'
    var format = d3.time.format('%Y-%m-%d');
    return url + ticker + '.csv?sort_order=asc&collapse=' + collapse
            + '&trim_start=' + format(trimStart)
            + '&trim_end=' + format(trimEnd)
            + '&auth_token=' + token;

}