package ru.lab.calculator


/**
 * SLAE calculator implementation.
 * @see SlaeCalculator
 */
class SlaeCalculatorImpl(private val slaeMatrix: Array<DoubleArray>) : SlaeCalculator {
    override fun determinant(triangular: Array<DoubleArray>): Double {
        var result = 1.0
        triangular.forEachIndexed { i, _ -> result *= triangular[i][i] }
        return result
    }

    override fun triangular(): Array<DoubleArray> {
        val n = slaeMatrix.size
        val a = Array(n) { DoubleArray(n + 1) }
        a.forEachIndexed { i, row ->
            row.forEachIndexed { j, _ ->
                a[i][j] = slaeMatrix[i][j]
            }
        }

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
        val x = DoubleArray(n)

        for (i in (n - 1 downTo 0)) {
            x[i] = triangular[i][n]
            for (j in (i + 1 until n)) {
                if (j != i) {
                    x[i] = x[i] - triangular[i][j] * x[j]
                }
            }
            x[i] = x[i] / triangular[i][i]
        }

        return x
    }

    override fun residuals(roots: DoubleArray): DoubleArray {
        val n = slaeMatrix.size
        val r = DoubleArray(n)

        slaeMatrix.forEachIndexed { i, row ->
            var result = 0.0
            for (j in 0 until n) {
                result += row[j] * roots[j]
            }
            r[i] = result - row.last()
        }

        return r
    }
}
