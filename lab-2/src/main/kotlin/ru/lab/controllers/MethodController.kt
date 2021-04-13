package ru.lab.controllers

import ru.lab.controllers.methods.HalfDivisionMethod
import ru.lab.controllers.methods.NewtonMethod
import ru.lab.controllers.methods.SimpleIterationsMethod
import ru.lab.model.Function
import ru.lab.model.Method
import ru.lab.model.Results
import ru.lab.views.tables.HalfDivisionView
import ru.lab.views.tables.NewtonView
import ru.lab.views.tables.SimpleIterationsView
import tornadofx.Controller
import tornadofx.runLater

class MethodController : Controller() {
    private val formController: FormController by inject()

    private val halfDivisionView: HalfDivisionView by inject()
    private val newtonView: NewtonView by inject()
    private val simpleIterationsView: SimpleIterationsView by inject()

    private val halfDivisionMethod: HalfDivisionMethod by inject()
    private val newtonMethod: NewtonMethod by inject()
    private val simpleIterationsMethod: SimpleIterationsMethod by inject()

    lateinit var lastResults: Results
    fun isLastResultsInitialized() = ::lastResults.isInitialized

    private fun hasRoots(
        function: Function,
        left: Double,
        right: Double,
    ) = function(left) * function(right) < 0

    private fun moreThanOneRoot(
        function: Function,
        left: Double,
        right: Double,
        accuracy: Double
    ): Boolean {
        var x = left
        val rise = function.derivative(x, accuracy)

        while (x <= right) {
            val derivative = function.derivative(x, accuracy)
            x += accuracy

            if (derivative * rise <= 0) {
                return true
            }
        }

        return false
    }

    fun showResults() {
        val function = formController.getFunction()
        val leftBoundary = formController.getLeftBoundary()
        val rightBoundary = formController.getRightBoundary()
        val accuracy = formController.getAccuracy()
        val method = formController.getMethod()

        if (!hasRoots(function, leftBoundary, rightBoundary)) {
            throw IllegalArgumentException("There are no roots in the selected area.")
        } else if (moreThanOneRoot(function, leftBoundary, rightBoundary, accuracy)) {
            throw IllegalArgumentException("There is more than one root in the selected area.")
        } else {
            when (method) {
                Method.NEWTON -> {
                    newtonView.openWindow()
                    runLater {
                        newtonMethod.compute(function, leftBoundary, rightBoundary, accuracy)
                    }
                }
                Method.SIMPLE_ITERATIONS -> {
                    simpleIterationsView.openWindow()
                    runLater {
                        simpleIterationsMethod.compute(function, leftBoundary, rightBoundary, accuracy)
                    }
                }
                else -> {
                    halfDivisionView.openWindow()
                    runLater {
                        halfDivisionMethod.compute(function, leftBoundary, rightBoundary, accuracy)
                    }
                }
            }
        }
    }

    fun findX0(
        function: Function,
        left: Double,
        right: Double,
        accuracy: Double
    ): Double {
        var x0 = left
        var x = left

        while (x <= right) {
            x0 = function(x) * function.derivative(function.derivative(x, accuracy), accuracy)
            if (x0 > 0) {
                break
            }
            x += accuracy
        }

        return x0
    }
}