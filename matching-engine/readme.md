## Simple order book project



### Assumptions
- Order amendment is not supported. Only cancel and reinsert.
- Only GTC orders are supported
- No minimum order or fill size


### Roadmap

#### Different OrderBook construction approaches
- [x] Two priority queues with orders
- [ ] Two TreeSets with orders
- [ ] Two TreeMaps with PriceLevels
- [ ] Two arrays with Order Lists, where array element represents price level
- [ ] Deferred matching

