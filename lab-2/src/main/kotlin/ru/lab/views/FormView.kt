package ru.lab.views

import javafx.scene.control.TextField
import tornadofx.View
import tornadofx.action
import tornadofx.filterInput
import tornadofx.hbox
import tornadofx.isDouble
import tornadofx.label
import tornadofx.singleAssign
import tornadofx.textfield
import tornadofx.vbox

class FormView : View() {
    private val graphView: GraphView by inject()
    var function: TextField by singleAssign()
    var left: TextField by singleAssign()
    var right: TextField by singleAssign()
    var step: TextField by singleAssign()

    private fun refreshGraph() {
        try {
            graphView.renderGraph()
        } catch (e: IllegalArgumentException) {
            print(e.message)
        }
    }

    override val root = vbox {
        hbox {
            label("Function:")
            function = textfield("1.62x^3-8.15*x^2+4.39*x+4.29") {
                action { refreshGraph() }
            }
        }

        hbox {
            label("Left:")
            left = textfield("-20") {
                filterInput { it.controlNewText.isDouble() }
                action { refreshGraph() }
            }
        }

        hbox {
            label("Right:")
            right = textfield("20") {
                filterInput { it.controlNewText.isDouble() }
                action { refreshGraph() }
            }
        }

        hbox {
            label("Step:")
            step = textfield("0.5") {
                filterInput { it.controlNewText.isDouble() }
                action { refreshGraph() }
            }
        }
    }
}