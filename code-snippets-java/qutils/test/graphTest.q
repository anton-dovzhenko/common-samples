//.graph.wg
if[not (`v`e`w!(`u#0 1 2 3 4;(1 2;3 2;1 3 4;enlist 4;0 3);(10 5;1 2;3 9 2;enlist 4;7 6)))
    ~ .graph.wg (0 1 10; 0 2 5; 1 3 1; 1 2 2; 2 1 3; 2 3 9; 2 4 2; 3 4 4; 4 0 7; 4 3 6)
    ; '"[AssertionException] .graph.wg case1"
 ];

//.graph.dijkstra see [Cormen] Introduction to Algorithms ed 3. 24.3 Dijkstra's algorithm
if[not 0 8 5 9 7~.graph.dijkstra[.graph.wg (0 1 10; 0 2 5; 1 3 1; 1 2 2; 2 1 3; 2 3 9; 2 4 2; 3 4 4; 4 0 7; 4 3 6);0]'[til 5]
    ; '"[AssertionException] .graph.dijkstra case1"
 ];
