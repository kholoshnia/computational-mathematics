package ru.lab.views

import tornadofx.View
import tornadofx.borderpane
import tornadofx.vbox

class RootView : View() {
    private val graphView: GraphView by inject()

    override val root = borderpane {
        center = vbox {
            add(graphView)
        }
    }
}