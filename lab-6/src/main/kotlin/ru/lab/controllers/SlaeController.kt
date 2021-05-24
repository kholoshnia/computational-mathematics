package ru.lab.controllers

import tornadofx.Controller


class SlaeController : Controller() {
    private fun triangular(matrix: Array<DoubleArray>): Array<DoubleArray> {
        val n = matrix.size
        val a = Array(n) { DoubleArray(n + 1) }
        a.forEachIndexed { i, row ->
            row.forEachIndexed { j, _ ->
                a[i][j] = matrix[i][j]
            }
        }

        for (i in (0 until n - 1)) {
            if (a[i][i] == 0.0) {
                val temp = a[i]
                a[i] = a[i + 1]
                a[i + 1] = temp
            }

            for (k in (i + 1 until n)) {
                val c = a[k][i] / a[i][i]
                for (j in (0..n)) {
                    a[k][j] = a[k][j] - c * a[i][j]
                }
            }
        }

        return a
    }

    fun solve(matrix: Array<DoubleArray>): DoubleArray {
        val triangular = triangular(matrix)
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

        x.reverse()
        return x
    }

}