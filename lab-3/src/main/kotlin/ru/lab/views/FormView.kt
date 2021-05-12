package ru.lab.views

import javafx.geometry.Orientation
import javafx.scene.control.Alert
import javafx.scene.control.CheckBox
import javafx.scene.control.ComboBox
import javafx.scene.control.TextField
import ru.lab.controllers.FormController
import ru.lab.controllers.MethodController
import ru.lab.model.Method
import ru.lab.model.Rectangle
import tornadofx.Field
import tornadofx.View
import tornadofx.action
import tornadofx.alert
import tornadofx.button
import tornadofx.checkbox
import tornadofx.combobox
import tornadofx.field
import tornadofx.fieldset
import tornadofx.filterInput
import tornadofx.form
import tornadofx.hbox
import tornadofx.isDouble
import tornadofx.isInt
import tornadofx.paddingTop
import tornadofx.separator
import tornadofx.singleAssign
import tornadofx.textfield
import tornadofx.toObservable


class FormView : View() {
    private val graphView: GraphView by inject()
    private val resultsView: ResultsView by inject()
    private val methodController: MethodController by inject()
    private val formController: FormController by inject()

    var functionTextField: TextField by singleAssign()
    var leftTextField: TextField by singleAssign()
    var rightTextField: TextField by singleAssign()
    var stepTextField: TextField by singleAssign()
    var accuracyCheckbox: CheckBox by singleAssign()
    var accuracyTextField: TextField by singleAssign()
    var initialPartitioningTextField: TextField by singleAssign()
    var methodComboBox: ComboBox<String> by singleAssign()
    var rectangleComboBox: ComboBox<String> by singleAssign()

    private var rectangleField: Field by singleAssign()

    private fun isDoubleInput(input: String) = input
        .replace("^[-+]$".toRegex(), "0")
        .replace(",".toRegex(), ".")
        .isDouble()

    private fun isIntInput(input: String) = input
        .replace("^[-+]$".toRegex(), "0")
        .isInt()

    override val root = form {
        fieldset("Function") {
            field {
                functionTextField = textfield("2x^3-5*x^2-3*x+21")
            }
        }

        fieldset("Parameters") {
            field("Left boundary:") {
                leftTextField = textfield("0") {
                    filterInput { isDoubleInput(it.controlNewText) }
                }
            }

            field("Right boundary:") {
                rightTextField = textfield("2") {
                    filterInput { isDoubleInput(it.controlNewText) }
                }
            }

            field("Step:") {
                stepTextField = textfield("0.5") {
                    filterInput { isDoubleInput(it.controlNewText) }
                }
            }

            field("Initial partitioning:") {
                initialPartitioningTextField = textfield("10") {
                    filterInput { isIntInput(it.controlNewText) }
                }
            }

            hbox(20) {
                field {
                    paddingTop = 10
                    accuracyCheckbox = checkbox("Use accuracy") {
                        isSelected = false
                    }
                }

                field("Accuracy:") {
                    accuracyTextField = textfield("0.001") {
                        filterInput { isDoubleInput(it.controlNewText) }
                        disableProperty().bind(!accuracyCheckbox.selectedProperty())
                    }
                }
            }

            hbox(20) {
                field("Method:") {
                    methodComboBox = combobox {
                        items = Method.values()
                            .map { it.method }
                            .toList()
                            .toObservable()
                        selectionModel.selectFirst()

                        setOnAction {
                            rectangleField.isVisible = formController.getMethod() == Method.RECTANGLE
                            resultsView.valueValue.text = ""
                            resultsView.partitioningValue.text = ""
                        }
                    }
                }

                rectangleField = field("Rectangle:") {
                    rectangleComboBox = combobox {
                        items = Rectangle.values()
                            .map { it.method }
                            .toList()
                            .toObservable()
                        selectionModel.selectFirst()
                        maxWidth = 85.0

                        setOnAction {
                            resultsView.valueValue.text = ""
                            resultsView.partitioningValue.text = ""
                        }
                    }
                }
            }
        }

        hbox(20) {
            fieldset("Actions") {
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
                    button("Compute") {
                        action {
                            try {
                                methodController.computeResults()
                            } catch (e: IllegalArgumentException) {
                                alert(Alert.AlertType.WARNING, "Results error", e.message)
                            }
                        }
                    }
                }
            }

            separator(Orientation.VERTICAL)

            fieldset("Results") {
                add(resultsView)
            }
        }
    }
}