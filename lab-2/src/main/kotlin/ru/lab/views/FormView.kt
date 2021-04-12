package ru.lab.views

import com.fasterxml.jackson.core.JacksonException
import javafx.geometry.Orientation
import javafx.scene.control.Alert
import javafx.scene.control.ComboBox
import javafx.scene.control.TextField
import ru.lab.controllers.MethodController
import ru.lab.controllers.SettingsController
import ru.lab.model.Method
import tornadofx.View
import tornadofx.action
import tornadofx.alert
import tornadofx.button
import tornadofx.combobox
import tornadofx.field
import tornadofx.fieldset
import tornadofx.filterInput
import tornadofx.form
import tornadofx.hbox
import tornadofx.isDouble
import tornadofx.separator
import tornadofx.singleAssign
import tornadofx.textfield
import tornadofx.toObservable

class FormView : View() {
    private val graphView: GraphView by inject()
    private val settingsController: SettingsController by inject()
    private val methodController: MethodController by inject()

    var functionTextField: TextField by singleAssign()
    var leftTextField: TextField by singleAssign()
    var rightTextField: TextField by singleAssign()
    var stepTextField: TextField by singleAssign()
    var accuracyTextField: TextField by singleAssign()
    var methodComboBox: ComboBox<String> by singleAssign()

    private fun isDoubleInput(input: String): Boolean {
        return input
            .replace("^[-+]$".toRegex(), "0")
            .replace(",".toRegex(), ".")
            .isDouble()
    }

    override val root = form {
        fieldset("Function") {
            field {
                functionTextField = textfield("x^2-2")
            }
        }

        fieldset("Parameters") {
            field("Left boundary:") {
                leftTextField = textfield("0") {
                    filterInput { isDoubleInput(it.controlNewText) }
                }
            }

            field("Right boundary:") {
                rightTextField = textfield("3") {
                    filterInput { isDoubleInput(it.controlNewText) }
                }
            }

            field("Step:") {
                stepTextField = textfield("0.5") {
                    filterInput { isDoubleInput(it.controlNewText) }
                }
            }

            field("Accuracy:") {
                accuracyTextField = textfield("0.01") {
                    filterInput { isDoubleInput(it.controlNewText) }
                }
            }

            field("Method:") {
                methodComboBox = combobox {
                    items = Method.values()
                        .map { it.method }
                        .toList()
                        .toObservable()
                    selectionModel.selectFirst()
                }
            }
        }

        hbox(20) {
            fieldset("Results") {
                field {
                    button("Draw") {
                        action {
                            try {
                                graphView.renderGraph()
                            } catch (e: IllegalArgumentException) {
                                alert(Alert.AlertType.WARNING, "Function error", e.message)
                            }
                        }
                    }
                }

                field {
                    button("Show & Compute") {
                        action {
                            try {
                                methodController.showResults()
                            } catch (e: IllegalArgumentException) {
                                alert(Alert.AlertType.WARNING, "Results error", e.message)
                            } catch (e: ArithmeticException) {
                                alert(
                                    Alert.AlertType.WARNING,
                                    "Results error",
                                    "Cannot compute not continuous function: ${e.message}"
                                )
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
                                val settings = settingsController.importSettings()

                                if (settings != null) {
                                    functionTextField.text = settings.function
                                    leftTextField.text = settings.left
                                    rightTextField.text = settings.right
                                    accuracyTextField.text = settings.accuracy
                                }
                            } catch (e: JacksonException) {
                                alert(Alert.AlertType.WARNING, "Import error", e.message)
                            }
                        }
                    }
                }

                field {
                    button("Export") {
                        action {
                            try {
                                if (methodController.isLastResultsInitialized()) {
                                    val results = methodController.lastResults
                                    settingsController.exportSettings(results)
                                } else {
                                    alert(Alert.AlertType.WARNING, "Export error", "No last results found.")
                                }
                            } catch (e: JacksonException) {
                                alert(Alert.AlertType.WARNING, "Export error", e.message)
                            }
                        }
                    }
                }
            }
        }
    }
}