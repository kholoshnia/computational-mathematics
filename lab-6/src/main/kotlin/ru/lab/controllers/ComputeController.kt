package ru.lab.controllers

import ru.lab.controllers.methods.AdamsMethod
import ru.lab.controllers.methods.EulerMethod
import ru.lab.model.Method
import ru.lab.views.GraphView
import tornadofx.Controller


class ComputeController : Controller() {
    private val formController: FormController by inject()
    private val functionController: FunctionController by inject()
    private val graphView: GraphView by inject()

    private val eulerMethod: EulerMethod by inject()
    private val adamsMethod: AdamsMethod by inject()

    fun compute() {
        val method = formController.getMethod()
        val left = formController.getLeftBoundary()
        val right = formController.getRightBoundary()

        val (xValues, yValues) = when (method) {
            Method.ADAMS -> adamsMethod.getFunction(
                formController.getOde(),
                formController.getInitialY(),
                formController.getAccuracy(),
                formController.getStep(),
                left,
                right,
                formController.getSolution()
            )
            else -> eulerMethod.getFunction(
                formController.getOde(),
                formController.getInitialY(),
                formController.getStep(),
                left,
                right,
                formController.getSolution(),
                formController.getAccuracy(),
                true
            )
        }

        val series = functionController.getSeries(
            method.method,
            xValues,
            yValues,
        )

        graphView.clear()
        graphView.addSeries(series)

        val solution = formController.getSolution()
        val drawStep = formController.getDrawStep()

        val solutionSeries = functionController.getSeries(
            "Solution",
            solution,
            left,
            right,
            drawStep
        )

        graphView.addSeries(solutionSeries)
    }
}
