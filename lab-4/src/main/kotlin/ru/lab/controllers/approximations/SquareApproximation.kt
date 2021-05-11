package ru.lab.controllers.approximations

import ru.lab.controllers.Approximation
import ru.lab.controllers.SlaeController
import tornadofx.Controller
import kotlin.math.pow


class SquareApproximation : Approximation, Controller() {
    private val slaeController: SlaeController by inject()

    override fun getFunction(
        xValues: List<Double>,
        yValues: List<Double>
    ): Pair<String, String> {
        val n = xValues.size.toDouble()
        val sx = xValues.sum()
        val sx2 = xValues.sumOf { it.pow(2) }
        val sx3 = xValues.sumOf { it.pow(3) }
        val sx4 = xValues.sumOf { it.pow(4) }
        val sy = yValues.sum()

        var sxy = 0.0
        for (i in xValues.indices) {
            sxy += xValues[i] * yValues[i]
        }

        var sx2y = 0.0
        for (i in xValues.indices) {
            sx2y += xValues[i].pow(2) * yValues[i]
        }

        val (a0, a1, a2) = slaeController.solve(
            arrayOf(
                doubleArrayOf(n, sx, sx2, sy),
                doubleArrayOf(sx, sx2, sx3, sxy),
                doubleArrayOf(sx2, sx3, sx4, sx2y)
            )
        )

        return Pair(
            "$a0+${a1}x+${a2}x^2",
            "${String.format("%.3f", a0)}+${String.format("%.3f", a1)}x+${String.format("%.3f", a2)}x^2"
        )
    }
}