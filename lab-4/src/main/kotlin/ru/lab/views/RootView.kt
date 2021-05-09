package ru.lab.views

import tornadofx.View
import tornadofx.borderpane
import tornadofx.hbox


class RootView : View() {
    private val graphView: GraphView by inject()
    private val formView: FormView by inject()

    init {
        title = "Lab 4"
    }

    override val root = borderpane {
        left = hbox {
            add(formView)
        }

        right = hbox {
            add(graphView)
        }
    }
}