// Builds weighted graph from (edge start; edge end; edge weight) ternaries.
// Graph structure is reprsented by dictionary, where
// `v is list of vertices
// `e is list of lists of vertices adjustent to vertices from list `v
// `w is list of lists of weights of `v->`w edges
// Example .graph.wg(0 1 10; 0 2 5; 1 3 1; 1 2 2; 2 1 3; 2 3 9; 2 4 2; 3 4 4; 4 0 7; 4 3 6)
// return `v`e`w!(`u#0 1 2 3 4;(1 2;3 2;1 3 4;enlist 4;0 3);(10 5;1 2j;3 9 2;enlist 4;7 6))
.graph.wg: {
   x: flip x;
   g: group x 0;
   `v`e`w!(`u#key g;x[1]@/:value g;x[2]@/:value g)
 };


// Returns shortest path from vertex start to vertex end in weighted-graph G
// Shortest path is discovered with Dijkstra algorithm
// @G - weighted graph with non-negative weights in `v`e`w format (like in .graph.wg)
// @start - start vertex
// @end - end vertex
.graph.dijkstra: {[G;start;end]
    S: enlist[start]!enlist 0;
    while[not end in key S
        ; S,: {[G;S]
            v: key S;
            i: G[`v]?v;
            nextE: raze G[`e]i;
            nextW: raze (G[`w]i) + (count each G[`e]i)#'(value S);
            filter: where not nextE in v;
            nextE: nextE@filter;
            nextW: nextW@filter;
            minW: min nextW;
            nextV: nextE@first where nextW=minW;
            enlist[nextV]!enlist min minW
        }[G;S];
    ];
    S@end
 };




