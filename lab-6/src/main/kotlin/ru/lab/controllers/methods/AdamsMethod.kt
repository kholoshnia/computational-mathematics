package ru.lab.controllers.methods

import ru.lab.model.AdamsResult
import ru.lab.model.Ode
import ru.lab.model.Results
import ru.lab.views.ResultsView
import tornadofx.Controller
import kotlin.math.abs
import kotlin.math.pow


class AdamsMethod : Controller() {
    private val resultsView: ResultsView by inject()
    private val eulerMethod: EulerMethod by inject()

    fun getFunction(
        ode: Ode,
        initialY: Double,
        accuracy: Double,
        step: Double,
        left: Double,
        right: Double,
    ): Pair<List<Double>, List<Double>> {
        resultsView.rows.clear()

        val resultX = ArrayList<Double>()
        val resultY = ArrayList<Double>()

        var i = 0
        var h = step

        do {
            resultX.clear()
            resultY.clear()

            val (eulerX, eulerY) = eulerMethod.getFunction(ode, initialY, h, left, left + 3 * h)

            resultX.addAll(eulerX)
            resultY.addAll(eulerY)

            val (yh, newI, xValues, yValues) = compute(
                ode, eulerX, eulerY, left + 4 * h, right,
                h, i, true
            )

            i = newI
            resultX.addAll(xValues)
            resultY.addAll(yValues)

            val (y2h, _, _, _) = compute(
                ode, eulerX, eulerY, left + 4 * h * 2, right,
                h * 2, i, false
            )

            h /= 2
        } while (accuracy < abs((yh - y2h) / 15))

        return Pair(resultX, resultY)
    }

    private fun compute(
        ode: Ode,
        xs: List<Double>,
        ys: List<Double>,
        left: Double,
        right: Double,
        step: Double,
        index: Int,
        main: Boolean
    ): AdamsResult {
        var i = index
        var x = left
        val xValues = ArrayList<Double>()
        val yValues = ArrayList<Double>()

        val xList = ArrayList<Double>(xs)
        val yList = ArrayList<Double>(ys)

        while (x <= right || yValues.size < 3) {
            val fi = ode(xList[3], yList[3])
            val fi1 = ode(xList[2], yList[2])
            val fi2 = ode(xList[1], yList[1])
            val fi3 = ode(xList[0], yList[0])

            val deltaFi = fi - fi1
            val deltaFi2 = fi - 2 * fi1 + fi2
            val deltaFi3 = fi - 3 * fi1 + 3 * fi2 - fi3

            val y = yList[3] + step * fi + step.pow(2) * deltaFi +
                    5 / 12 * step.pow(3) * deltaFi2 +
                    3 / 8 * step.pow(4) * deltaFi3
            x += step

            for (xi in 0 until xList.size - 1) {
                xList[xi] = xList[xi + 1]
            }

            for (yi in 0 until yList.size - 1) {
                yList[yi] = yList[yi + 1]
            }

            xList[3] = x
            yList[3] = y

            xValues.add(x)
            yValues.add(y)

            if (main) {
                resultsView.rows.add(Results(i, x, y, ode(x, y)))
                i++
            }
        }

        if (main) {
            return AdamsResult(yValues[2], i, xValues, yValues)
        }

        return AdamsResult(yValues[1], i, xValues, yValues)
    }
}