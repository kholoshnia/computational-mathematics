package ru.lab.views

import javafx.scene.control.Alert
import javafx.scene.control.TextField
import ru.lab.controllers.ComputeController
import tornadofx.View
import tornadofx.action
import tornadofx.alert
import tornadofx.button
import tornadofx.field
import tornadofx.fieldset
import tornadofx.filterInput
import tornadofx.form
import tornadofx.isDouble
import tornadofx.singleAssign
import tornadofx.textfield


class FormView : View() {
    companion object {
        private const val X_VALUES = "-11 -9 -8 -7 -6 -5 -4 -3 -2 -1 0 1 2 3 4 5 6 7 8 9 10"
        private const val Y_VALUES = "172 124 103 84 67 52 39 28 19 12 7 4 3 4 7 12 19 28 39 52 67"
    }

    private val computeController: ComputeController by inject()

    var xValuesTextField: TextField by singleAssign()
    var yValuesTextField: TextField by singleAssign()
    var leftTextField: TextField by singleAssign()
    var rightTextField: TextField by singleAssign()
    var stepTextField: TextField by singleAssign()

    private fun isDoubleInput(input: String) = input
        .replace("^[-+]$".toRegex(), "0")
        .replace(",".toRegex(), ".")
        .isDouble()

    override val root = form {
        fieldset("Values") {
            field("X:") {
                xValuesTextField = textfield(X_VALUES)
            }

            field("Y:") {
                yValuesTextField = textfield(Y_VALUES)
            }
        }

        fieldset("Parameters") {
            field("Left boundary:") {
                leftTextField = textfield {
                    filterInput { isDoubleInput(it.controlNewText) }
                }
            }

            field("Right boundary:") {
                rightTextField = textfield {
                    filterInput { isDoubleInput(it.controlNewText) }
                }
            }

            field("Step:") {
                stepTextField = textfield("0.5") {
                    filterInput { isDoubleInput(it.controlNewText) }
                }
            }
        }

        fieldset("Actions") {
            field {
                button("Show & Compute") {
                    action {
                        try {
                            computeController.compute()
                        } catch (e: Exception) {
                            alert(Alert.AlertType.WARNING, "Results error", e.message)
                        }
                    }
                }
            }
        }
    }
}