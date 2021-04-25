package ru.lab.controllers

import ru.lab.controllers.methods.RectangleMethod
import ru.lab.controllers.methods.SimpsonsMethod
import ru.lab.controllers.methods.TrapezoidsMethod
import ru.lab.model.Function
import ru.lab.model.Method
import ru.lab.model.Rectangle
import ru.lab.views.ResultsView
import tornadofx.Controller
import kotlin.math.abs

class MethodController : Controller() {
    private val formController: FormController by inject()
    private val resultsView: ResultsView by inject()

    private val rectangleMethod: RectangleMethod by inject()
    private val trapezoidsMethod: TrapezoidsMethod by inject()
    private val simpsonsMethod: SimpsonsMethod by inject()

    private fun compute(
        method: Method,
        function: Function,
        leftBoundary: Double,
        rightBoundary: Double,
        partitioning: Int,
        rectangle: Rectangle
    ): Double {
        return when (method) {
            Method.SIMPSONS -> {
                simpsonsMethod.compute(
                    function,
                    leftBoundary,
                    rightBoundary,
                    partitioning
                )
            }
            Method.TRAPEZOIDS -> {
                trapezoidsMethod.compute(
                    function,
                    leftBoundary,
                    rightBoundary,
                    partitioning
                )
            }
            else -> {
                rectangleMethod.compute(
                    function,
                    leftBoundary,
                    rightBoundary,
                    partitioning,
                    rectangle
                )
            }
        }
    }

    fun showResults() {
        val function = formController.getFunction()
        val leftBoundary = formController.getLeftBoundary()
        val rightBoundary = formController.getRightBoundary()
        val partitioning = formController.getPartitioning()
        val method = formController.getMethod()
        val rectangle = formController.getRectangle()

        val useAccuracy = formController.useAccuracy()
        val accuracy = formController.getAccuracy()

        var iterPartitioning = partitioning
        var result = compute(
            method,
            function,
            leftBoundary,
            rightBoundary,
            iterPartitioning,
            rectangle
        )

        var newResult: Double = result
        if (useAccuracy) {
            do {
                iterPartitioning *= 2
                result = newResult
                newResult = compute(
                    method,
                    function,
                    leftBoundary,
                    rightBoundary,
                    iterPartitioning,
                    rectangle
                )
            } while (abs(newResult - result) > accuracy)
        }

        resultsView.valueValue.text = newResult.toString()
        resultsView.partitioningValue.text = iterPartitioning.toString()
    }
}