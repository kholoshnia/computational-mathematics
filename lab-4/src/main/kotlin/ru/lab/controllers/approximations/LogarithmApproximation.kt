package ru.lab.controllers.approximations

import ru.lab.controllers.Approximation
import ru.lab.controllers.SlaeController
import tornadofx.Controller
import kotlin.math.ln
import kotlin.math.pow


class LogarithmApproximation : Approximation, Controller() {
    private val slaeController: SlaeController by inject()

    override fun getFunction(
        xValues: List<Double>,
        yValues: List<Double>
    ): Pair<String, String>? {
        if (xValues.any { it <= 0 }) {
            return null
        }

        val n = xValues.size.toDouble()
        val sx = xValues.sumOf { ln(it) }
        val sx2 = xValues.sumOf { ln(it).pow(2) }
        val sy = yValues.sumOf { it }

        var sxy = 0.0
        for (i in xValues.indices) {
            sxy += ln(xValues[i]) * yValues[i]
        }

        val (a, b) = slaeController.solve(
            arrayOf(
                doubleArrayOf(sx2, sx, sxy),
                doubleArrayOf(sx, n, sy)
            )
        )

        return Pair(
            "${b}log(x)+$a",
            "${String.format("%.3f", b)}log(x)+${String.format("%.3f", a)}"
        )
    }
}