package ru.lab.controllers.approximations

import ru.lab.controllers.Approximation
import tornadofx.Controller
import kotlin.math.pow

class SquareApproximation : Approximation, Controller() {
    override fun getFunction(
        xValues: List<Double>,
        yValues: List<Double>
    ): String {
        val n = xValues.size
        val sx = xValues.sum()
        val sxx = xValues.sumOf { it.pow(2) }
        val sy = yValues.sumOf { it.pow(2) }

        var sxy = 0.0
        for (i in xValues.indices) {
            sxy += xValues[i] * yValues[i]
        }

        val delta = sxx * n - sx * sy
        val delta1 = sxy * n - sx * sy
        val delta2 = sxx * sy - sx * sxy

        val a = delta1 / delta
        val b = delta2 / delta

        return "${a}x+$b"
    }
}