package ru.lab.views

import tornadofx.View
import tornadofx.hbox
import tornadofx.vbox


class RootView : View() {
    private val graphView: GraphView by inject()
    private val formView: FormView by inject()

    init {
        title = "Lab 5"
    }

    override val root = vbox {
        hbox {
            add(formView)
            add(graphView)
        }
    }
}