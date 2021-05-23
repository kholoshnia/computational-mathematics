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
        private const val X_VALUES = "0.25 0.30 0.35 0.40 0.45 0.50 0.55"
        private const val Y_VALUES = "1.2557 2,1764 3,1218 4,0482 5,9875 6,9195 7,8359"
        private const val SEARCH_VALUE = "0.283"
        private const val FUNCTION = "sin(x)"
        private const val LEFT_BOUNDARY = "-5"
        private const val RIGHT_BOUNDARY = "5"
        private const val PARTITIONING = "10"
    }

    private val computeController: ComputeController by inject()
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
                        functionFieldset.isVisible = formController.getVariant() == Variant.FUNCTION
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
                leftBoundaryTextField = textfield(LEFT_BOUNDARY) {
                    filterInput { isDoubleInput(it.controlNewText) }
                }
            }

            field("Right boundary:") {
                rightBoundaryTextField = textfield(RIGHT_BOUNDARY) {
                    filterInput { isDoubleInput(it.controlNewText) }
                }
            }

            field("Partitioning:") {
                partitioningTextField = textfield(PARTITIONING)
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
                searchValueTextField = textfield(SEARCH_VALUE) {
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