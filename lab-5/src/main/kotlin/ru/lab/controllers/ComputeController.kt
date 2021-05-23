package ru.lab.controllers

import ru.lab.controllers.methods.LagrangeMethod
import ru.lab.controllers.methods.NewtonMethod
import ru.lab.model.Method
import ru.lab.model.Variant
import ru.lab.views.GraphView
import ru.lab.views.ResultsView
import tornadofx.Controller


class ComputeController : Controller() {
    private val formController: FormController by inject()
    private val functionController: FunctionController by inject()
    private val graphView: GraphView by inject()
    private val resultsView: ResultsView by inject()

    private val lagrangeMethod: LagrangeMethod by inject()
    private val newtonMethod: NewtonMethod by inject()

    fun compute() {
        val variant = formController.getVariant()
        val (xValues, yValues) = when (variant) {
            Variant.FUNCTION -> functionController.getValues(
                formController.getFunction(),
                formController.getLeftBoundary(),
                formController.getRightBoundary(),
                formController.getPartitioning()
            )
            else -> Pair(formController.getXValues(), formController.getYValues())
        }

        if (xValues.size != yValues.size) {
            throw IllegalArgumentException("X and Y values are not equal in size")
        }

        val step = formController.getStep()
        val searchValue = formController.getSearchValue()

        val method = formController.getMethod()

        graphView.clear()
        resultsView.clear()

        val (functionString, name) = when (method) {
            Method.LAGRANGE -> Pair(lagrangeMethod.getFunction(xValues, yValues), "Lagrange")
            else -> Pair(newtonMethod.getFunction(xValues, yValues), "Newton")
        }

        val function = functionController.getFunction(functionString)

        val lagrange = functionController.getSeries(
            name,
            function,
            xValues.first(),
            xValues.last(),
            step
        )

        graphView.addSeries(lagrange)
        resultsView.setPolynomial(functionString)

        val search = functionController.getSeries(
            "Search value",
            searchValue,
            function(searchValue)
        )

        graphView.addSeries(search)

        val source = functionController.getSeries("Source", xValues, yValues)
        graphView.addSeries(source)
    }
}
