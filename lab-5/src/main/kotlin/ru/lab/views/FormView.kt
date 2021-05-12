package ru.lab.views

import javafx.scene.control.Alert
import javafx.scene.control.ComboBox
import javafx.scene.control.TextField
import ru.lab.controllers.ComputeController
import ru.lab.controllers.FormController
import ru.lab.model.Method
import ru.lab.model.Variant
import tornadofx.Fieldset
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
import tornadofx.singleAssign
import tornadofx.textfield
import tornadofx.toObservable


class FormView : View() {
    companion object {
        private const val X_VALUES = "1.1 2.3 3.7 4.5 5.4 6.8 7.5 8.1 9.2 10.5 11.8 12.5"
        private const val Y_VALUES = "2.73 5.12 7.74 8.91 10.59 12.75 13.43 15.6 17.2 18.9 19.2 20.5"
        private const val FUNCTION = "sin(x)"
    }

    private val computeController: ComputeController by inject()
    private val resultsView: ResultsView by inject()
    private val formController: FormController by inject()

    private var tableFieldset: Fieldset by singleAssign()
    private var functionFieldset: Fieldset by singleAssign()

    var variantComboBox: ComboBox<String> by singleAssign()
    var xValuesTextField: TextField by singleAssign()
    var yValuesTextField: TextField by singleAssign()
    var functionTextField: TextField by singleAssign()
    var leftBoundaryTextField: TextField by singleAssign()
    var rightBoundaryTextField: TextField by singleAssign()
    var partitioningTextField: TextField by singleAssign()
    var methodComboBox: ComboBox<String> by singleAssign()
    var searchValueTextField: TextField by singleAssign()
    var stepTextField: TextField by singleAssign()

    private fun isDoubleInput(input: String) = input
        .replace("^[-+]$".toRegex(), "0")
        .replace(",".toRegex(), ".")
        .isDouble()

    override val root = form {
        fieldset("Function") {
            field {
                variantComboBox = combobox {
                    items = Variant.values()
                        .map { it.variant }
                        .toList()
                        .toObservable()
                    selectionModel.selectFirst()

                    setOnAction {
                        tableFieldset.isVisible = formController.getVariant() == Variant.TABLE
                        xValuesTextField.text = ""
                        yValuesTextField.text = ""

                        functionFieldset.isVisible = formController.getVariant() == Variant.FUNCTION
                        functionTextField.text = ""
                        leftBoundaryTextField.text = ""
                        rightBoundaryTextField.text = ""
                        partitioningTextField.text = ""
                    }
                }
            }
        }


        tableFieldset = fieldset("Table") {
            field("X:") {
                xValuesTextField = textfield(X_VALUES)
            }

            field("Y:") {
                yValuesTextField = textfield(Y_VALUES)
            }

            managedProperty().bind(visibleProperty())
        }

        functionFieldset = fieldset("Function") {
            field {
                functionTextField = textfield(FUNCTION)
            }

            field("Left boundary:") {
                leftBoundaryTextField = textfield {
                    filterInput { isDoubleInput(it.controlNewText) }
                }
            }

            field("Right boundary:") {
                rightBoundaryTextField = textfield {
                    filterInput { isDoubleInput(it.controlNewText) }
                }
            }

            field("Partitioning:") {
                partitioningTextField = textfield()
            }

            isVisible = false
            managedProperty().bind(visibleProperty())
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

            field("Search value:") {
                searchValueTextField = textfield("5") {
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
            fieldset {
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
}