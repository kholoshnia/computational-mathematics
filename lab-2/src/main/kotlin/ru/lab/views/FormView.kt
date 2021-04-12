package ru.lab.views

import javafx.geometry.Orientation
import javafx.scene.control.Alert
import javafx.scene.control.ComboBox
import javafx.scene.control.TextField
import ru.lab.controllers.ComputeController
import ru.lab.controllers.FileController
import ru.lab.controllers.Method
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
    private val halfView: HalfView by inject()
    private val fileController: FileController by inject()
    private val computeController: ComputeController by inject()

    var function: TextField by singleAssign()
    var left: TextField by singleAssign()
    var right: TextField by singleAssign()
    var accuracy: TextField by singleAssign()
    var method: ComboBox<Method> by singleAssign()

    private fun refreshGraph() {
        try {
            graphView.renderGraph()
        } catch (e: IllegalArgumentException) {
            alert(Alert.AlertType.WARNING, "Computing error", e.message)
        }
    }

    override val root = form {
        fieldset("Function") {
            field {
                function = textfield("x^2-2") {
                    action { refreshGraph() }
                }
            }
        }

        fieldset("Parameters") {
            field("Left boundary:") {
                left = textfield("0") {
                    filterInput { it.controlNewText.isDouble() }
                    action { refreshGraph() }
                }
            }

            field("Right boundary:") {
                right = textfield("3") {
                    filterInput { it.controlNewText.isDouble() }
                    action { refreshGraph() }
                }
            }

            field("Accuracy:") {
                accuracy = textfield("0.005") {
                    filterInput { it.controlNewText.isDouble() }
                    action { refreshGraph() }
                }
            }

            field("Method:") {
                method = combobox {
                    items = Method.values().toList().toObservable()
                }
            }
        }

        hbox(20) {
            fieldset("Results") {
                field {
                    button("Show & Compute") {
                        action {
                            refreshGraph()

                            if (method.value == Method.HALF) {
                                /*computeController.halfDivision(

                                )*/
                            }
                        }
                    }
                }

                field {
                    button("Calculation table") {
                        action {
                            if (method.value == Method.HALF) {
                                halfView.openWindow()
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
                            val settings = fileController.importSettings()
                            if (settings != null) {
                                function.text = settings.function
                                left.text = settings.left
                                right.text = settings.right
                                accuracy.text = settings.accuracy
                            }
                        }
                    }
                }

                field {
                    button("Export") {
                        action {
                            // TODO export results
                        }
                    }
                }
            }
        }
    }
}