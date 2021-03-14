package ru.lab.calculator


/**
 * SLAE calculator implementation.
 * @see SlaeCalculator
 */
class SlaeCalculatorImpl(private val slaeMatrix: Array<DoubleArray>) : SlaeCalculator {
    override fun determinant(triangular: Array<DoubleArray>): Double {
        return 0.0
    }

    override fun triangular(): Array<DoubleArray> {
        val n = slaeMatrix.size
        val a = slaeMatrix.clone()

        /*for (i in (0 until n)) {
            for (k in (i + 1 until n)) {
                if (abs(a[i][i]) < abs(a[k][i])) {
                    for (j in (0..n)) {
                        val temp = a[i][j]
                        a[i][j] = a[k][j]
                        a[k][j] = temp
                    }
                }
            }
        }*/

        for (i in (0 until n - 1)) {
            for (k in (i + 1 until n)) {
                val c = a[k][i] / a[i][i]
                for (j in (0..n)) {
                    a[k][j] = a[k][j] - c * a[i][j]
                }
            }
        }

        return a
    }

    override fun roots(triangular: Array<DoubleArray>): DoubleArray {
        val n = triangular.size
        val a = triangular.clone()
        val x = DoubleArray(n)

        for (i in (n - 1 downTo 0)) {
            x[i] = a[i][n]
            for (j in (i + 1 until n)) {
                if (j != i) {
                    x[i] = x[i] - a[i][j] * x[j]
                }
            }
            x[i] = x[i] / a[i][i]
        }

        return x
    }

    override fun residuals(triangular: Array<DoubleArray>): DoubleArray {
        return doubleArrayOf()
    }
}
