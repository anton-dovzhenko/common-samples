# Pi calculation
Project has several implementations of Pi calculation using [Leibniz formula].

[JMH] is used for performance measurements. Performance is measured on 3.1 GHz Intel Core i7 Mac


|PiCalculatorBenchmark                        |Mode  |Cnt |Score   |Error     |Units|
|---------------------------------------------|------|----|--------|----------|-----|
|testSinglePiCalculator1_000_000N             |avgt  |20  | 84.312 |±  0.906  |ms/op|
|testStreamPiCalculator1_000_000N             |avgt  |20  | 27.687 |±  0.405  |ms/op|
|testThreadPiCalculator_1_Thread1_000_000N    |avgt  |20  | 93.178 |±  0.756  |ms/op|
|testThreadPiCalculator_2_Thread1_000_000N    |avgt  |20  | 52.778 |±  0.157  |ms/op|
|testThreadPiCalculator_4_Thread1_000_000N    |avgt  |20  | 30.467 |±  0.224  |ms/op|
|testThreadPiCalculator_6_Thread1_000_000N    |avgt  |20  | 39.600 |± 31.860  |ms/op|
|testThreadPiCalculator_8_Thread1_000_000N    |avgt  |20  | 30.180 |±  0.296  |ms/op|
|testThreadPiCalculator_16_Thread1_000_000N   |avgt  |20  | 30.642 |±  0.375  |ms/op|
|testSinglePiCalculator10_000_000N            |avgt  |20  |842.373 |±  5.750  |ms/op|
|testStreamPiCalculator10_000_000N            |avgt  |20  |271.443 |±  2.740  |ms/op|
|testThreadPiCalculator_1_Thread10_000_000N   |avgt  |20  |937.830 |±  7.993  |ms/op|
|testThreadPiCalculator_2_Thread10_000_000N   |avgt  |20  |528.210 |±  2.811  |ms/op|
|testThreadPiCalculator_4_Thread10_000_000N   |avgt  |20  |305.269 |±  2.130  |ms/op|
|testThreadPiCalculator_6_Thread10_000_000N   |avgt  |20  |299.152 |±  9.948  |ms/op|
|testThreadPiCalculator_8_Thread10_000_000N   |avgt  |20  |301.198 |±  2.766  |ms/op|
|testThreadPiCalculator_16_Thread10_000_000N  |avgt  |20  |307.133 |±  1.055  |ms/op|




[Leibniz formula]: <https://en.wikipedia.org/wiki/Leibniz_formula_for_%CF%80>
[JMH]: http://openjdk.java.net/projects/code-tools/jmh/
