package ru.lab.views

import javafx.geometry.Orientation
import javafx.scene.control.Alert
import javafx.scene.control.TextField
import ru.lab.controllers.ComputeController
import ru.lab.controllers.FileController
import tornadofx.View
import tornadofx.action
import tornadofx.alert
import tornadofx.button
import tornadofx.field
import tornadofx.fieldset
import tornadofx.filterInput
import tornadofx.form
import tornadofx.hbox
import tornadofx.isDouble
import tornadofx.separator
import tornadofx.singleAssign
import tornadofx.textfield


class FormView : View() {
    companion object {
        private const val X_VALUES = "1.1 2.3 3.7 4.5 5.4 6.8 7.5 8.1 9.2 10.5 11.8 12.5"
        private const val Y_VALUES = "2.73 5.12 7.74 8.91 10.59 12.75 13.43 15.6 17.2 18.9 19.2 20.5"
    }

    private val fileController: FileController by inject()
    private val computeController: ComputeController by inject()
    private val resultsView: ResultsView by inject()

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

        hbox(20) {
            fieldset("Actions") {
                field {
                    button("Compute") {
                        action {
                            try {
                                computeController.compute()
                            } catch (e: Exception) {
                                alert(Alert.AlertType.WARNING, "Results error", e.message)
                            }
                        }
                    }
                }

                field {
                    button("Show") {
                        action {
                            val results = resultsView.getResults()

                            if (results.isEmpty()) {
                                alert(
                                    Alert.AlertType.INFORMATION,
                                    "No results",
                                    "Please, compute results use show"
                                )
                            } else {
                                resultsView.openWindow()
                            }
                        }
                    }
                }
            }

            separator(Orientation.VERTICAL)

            fieldset("File") {
                field {
                    button("Import") {
                        action {
                            try {
                                val values = fileController.importTable()

                                if (values != null) {
                                    xValuesTextField.text = values.first
                                    yValuesTextField.text = values.second
                                }
                            } catch (e: Exception) {
                                alert(Alert.AlertType.WARNING, "Import error", e.message)
                            }
                        }
                    }
                }

                field {
                    button("Export") {
                        action {
                            try {
                                val results = resultsView.getResults()

                                if (results.isEmpty()) {
                                    alert(
                                        Alert.AlertType.INFORMATION,
                                        "No results",
                                        "Please, compute results to use export"
                                    )
                                } else {
                                    fileController.exportResults(results)
                                }
                            } catch (e: Exception) {
                                alert(Alert.AlertType.WARNING, "Export error", e.message)
                            }
                        }
                    }
                }
            }
        }
    }
}