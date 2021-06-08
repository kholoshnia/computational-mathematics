package ru.lab.controllers.methods

import ru.lab.model.Function
import ru.lab.model.Ode
import ru.lab.model.Results
import ru.lab.views.ResultsView
import tornadofx.Controller
import kotlin.math.abs


class AdamsMethod : Controller() {
    private val resultsView: ResultsView by inject()
    private val eulerMethod: EulerMethod by inject()

    private fun getListOf4(list: List<Double>): ArrayList<Double> {
        val result = ArrayList<Double>()
        result.add(list.first())
        result.add(list[(list.size / 3.0).toInt()])
        result.add(list[(list.size / 3.0 * 2.0).toInt()])
        result.add(list.last())
        return result
    }

    fun getFunction(
        ode: Ode,
        initialY: Double,
        accuracy: Double,
        step: Double,
        left: Double,
        right: Double,
        solution: Function,
    ): Pair<List<Double>, List<Double>> {
        resultsView.rows.clear()

        val resultX = ArrayList<Double>()
        val resultY = ArrayList<Double>()

        var i = 0

        val (eulerX, eulerY) = eulerMethod.getFunction(
            ode, initialY, step, left, left + 3 * step,
            solution, accuracy, true
        )

        val xList = getListOf4(eulerX)
        val yList = getListOf4(eulerY)

        resultX.addAll(xList)
        resultY.addAll(yList)

        var x = left + 3 * step
        while (x < right) {
            val fi = ode(xList[3], yList[3])
            val fi1 = ode(xList[2], yList[2])
            val fi2 = ode(xList[1], yList[1])
            val fi3 = ode(xList[0], yList[0])

            var pred = yList[3] + step / 24.0 * (55.0 * fi - 59.0 * fi1 + 37.0 * fi2 - 9.0 * fi3)

            do {
                val fiPlus1 = ode(x, pred)
                val corr = yList[3] + step / 24.0 * (9.0 * fiPlus1 + 19.0 * fi - 5 * fi1 + fi2)

                if (accuracy > abs(corr - pred) / 29.0) {
                    break
                }

                pred = corr
            } while (true)

            for (xi in 0 until xList.size - 1) {
                xList[xi] = xList[xi + 1]
            }

            for (yi in 0 until yList.size - 1) {
                yList[yi] = yList[yi + 1]
            }

            xList[3] = x
            yList[3] = pred

            x += step

            resultX.add(x)
            resultY.add(pred)
            i++
            resultsView.rows.add(Results(i, x, pred, ode(x, pred)))
        }

        return Pair(resultX, resultY)
    }
}