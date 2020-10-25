# Matrix Determinant
Project has implementation of matrix determinant calculation by [Laplace expansion] with single and multiple threads support.

[JMH] is used for performance measurements. Performance is measured on 3.1 GHz Intel Core i7 Mac

|MatrixDeterminantBenchmark                          |Mode  |Cnt    |Score    |Error    |Units  |
| -------------------------------------------------- | ---- | ----- | ------- | ------- | ----- |
|testLaplaceSingleMatrixDeterminant10x10             |avgt  |20     |648.470  |±  7.831 |  ms/op|
|testLaplaceForkJoinMatrixDeterminant10x10_Thread_1  |avgt  |20     |994.116  |± 77.118 |  ms/op|
|testLaplaceForkJoinMatrixDeterminant10x10_Thread_2  |avgt  |20     |449.475  |± 15.673 |  ms/op|
|testLaplaceForkJoinMatrixDeterminant10x10_Thread_3  |avgt  |20     |442.007  |± 38.435 |  ms/op|
|testLaplaceForkJoinMatrixDeterminant10x10_Thread_4  |avgt  |20     |400.167  |± 19.480 |  ms/op|
|testLaplaceForkJoinMatrixDeterminant10x10_Thread_6  |avgt  |20     |399.002  |± 30.869 |  ms/op|
|testLaplaceForkJoinMatrixDeterminant10x10_Thread_8  |avgt  |20     |414.089  |± 26.009 |  ms/op|
|testLaplaceForkJoinMatrixDeterminant10x10_Thread_16 |avgt  |20     |526.183  |± 76.577 |  ms/op|

[Laplace expansion]: <https://en.wikipedia.org/wiki/Laplace_expansion>
[JMH]: <http://openjdk.java.net/projects/code-tools/jmh/>