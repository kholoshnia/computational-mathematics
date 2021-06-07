package ru.lab.views

import javafx.scene.control.Alert
import javafx.scene.control.ComboBox
import javafx.scene.control.TextField
import ru.lab.controllers.ComputeController
import ru.lab.model.Method
import tornadofx.Field
import tornadofx.View
import tornadofx.action
import tornadofx.alert
import tornadofx.button
import tornadofx.combobox
import tornadofx.field
import tornadofx.fieldset
import tornadofx.filterInput
import tornadofx.form
import tornadofx.isDouble
import tornadofx.singleAssign
import tornadofx.textfield
import tornadofx.toObservable
import kotlin.math.E


class FormView : View() {
    companion object {
        private const val ODE = "-2y"
        private const val SOLUTION = "2*$E^(-2x)"
        private const val INITIAL_Y = "2"
        private const val LEFT_BOUNDARY = "0"
        private const val RIGHT_BOUNDARY = "2"
        private const val STEP = "0.1"
        private const val ACCURACY = "0.01"
    }

    private val computeController: ComputeController by inject()
    private val resultsView: ResultsView by inject()

    private var accuracyField: Field by singleAssign()

    var odeTextField: TextField by singleAssign()
    var solutionTextField: TextField by singleAssign()
    var initialYTextField: TextField by singleAssign()
    var leftBoundaryTextField: TextField by singleAssign()
    var rightBoundaryTextField: TextField by singleAssign()
    var stepTextField: TextField by singleAssign()
    var accuracyTextField: TextField by singleAssign()
    var methodComboBox: ComboBox<String> by singleAssign()
    var drawStepTextField: TextField by singleAssign()

    private fun isDoubleInput(input: String) = input
        .replace("^[-+]$".toRegex(), "0")
        .replace(",".toRegex(), ".")
        .isDouble()

    override val root = form {
        fieldset("Function") {
            field("ODE:") {
                odeTextField = textfield(ODE)
            }

            field("Solution:") {
                solutionTextField = textfield(SOLUTION)
            }

            field("Initial y:") {
                initialYTextField = textfield(INITIAL_Y) {
                    filterInput { isDoubleInput(it.controlNewText) }
                }
            }

            field("Left boundary:") {
                leftBoundaryTextField = textfield(LEFT_BOUNDARY) {
                    filterInput { isDoubleInput(it.controlNewText) }
                }
            }

            field("Right boundary:") {
                rightBoundaryTextField = textfield(RIGHT_BOUNDARY) {
                    filterInput { isDoubleInput(it.controlNewText) }
                }
            }

            field("Step:") {
                stepTextField = textfield(STEP) {
                    filterInput { isDoubleInput(it.controlNewText) }
                }
            }

            accuracyField = field("Accuracy:") {
                accuracyTextField = textfield(ACCURACY) {
                    filterInput { isDoubleInput(it.controlNewText) }
                }
            }
        }

        fieldset("Parameters") {
            field("Method") {
                methodComboBox = combobox {
                    items = Method.values()
                        .map { it.method }
                        .toList()
                        .toObservable()
                    selectionModel.selectFirst()
                }
            }

            field("Draw step:") {
                drawStepTextField = textfield("0.1") {
                    filterInput { isDoubleInput(it.controlNewText) }
                }
            }
        }

        fieldset("Actions") {
            field {
                button("Compute") {
                    action {
                        try {
                            computeController.compute()
                        } catch (e: Exception) {
                            alert(Alert.AlertType.WARNING, "Compute error", e.message)
                        }
                    }
                }

                button("Results") {
                    action {
                        try {
                            resultsView.openWindow()
                        } catch (e: Exception) {
                            alert(Alert.AlertType.WARNING, "Results error", e.message)
                        }
                    }
                }
            }
        }
    }
}