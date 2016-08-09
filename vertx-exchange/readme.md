## Vert.x-exchange
Prototype of bid|ask matching by instrument using vert.x <br>
Ideally verticles should be deployed on different JVMs.

### vert.x
###### pros
1. Very simple API (especially compared to Akka)

###### cons
1. Objects have to be Serialized, even if verticles run inside same JVM
2. Can't use native Java Serialization directly (MessageConverter has to be implemented)