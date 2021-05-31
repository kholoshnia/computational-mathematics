package ru.lab.controllers.methods

import ru.lab.model.Ode
import ru.lab.model.Results
import ru.lab.views.ResultsView
import tornadofx.Controller


class EulerMethod : Controller() {
    private val resultsView: ResultsView by inject()

    fun getFunction(
        ode: Ode,
        initialY: Double,
        step: Double,
        left: Double,
        right: Double,
    ): Pair<List<Double>, List<Double>> {
        resultsView.rows.clear()

        val xValues = ArrayList<Double>()
        val yValues = ArrayList<Double>()

        var x = left
        var y = initialY
        var i = 0

        xValues.add(x)
        yValues.add(y)

        while (x < right) {
            val odeValue = ode(x, y)
            resultsView.rows.add(Results(i, x, y, odeValue))
            i++

            y += step * odeValue
            x += step

            xValues.add(x)
            yValues.add(y)
        }

        resultsView.rows.add(Results(i, x, y, ode(x, y)))
        return Pair(xValues, yValues)
    }
}