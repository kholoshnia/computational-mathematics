package ru.lab.views

import tornadofx.View
import tornadofx.borderpane
import tornadofx.vbox

class RootView : View() {
    private val graphView: GraphView by inject()
    private val formView: FormView by inject()

    override val root = borderpane {
        left = vbox {
            add(formView)
        }
        center = vbox {
            add(graphView)
        }
    }
}