package ru.lab.views

import javafx.scene.layout.Priority
import tornadofx.View
import tornadofx.borderpane
import tornadofx.hboxConstraints
import tornadofx.hgrow
import tornadofx.vbox
import tornadofx.vgrow


class RootView : View() {
    private val graphView: GraphView by inject()
    private val formView: FormView by inject()

    init {
        title = "Lab 2"
    }

    override val root = borderpane {
        left = vbox {
            hboxConstraints {
                hGrow = Priority.ALWAYS
            }

            add(formView)
        }

        right = vbox {
            hgrow = Priority.ALWAYS
            vgrow = Priority.ALWAYS

            hboxConstraints {
                hGrow = Priority.ALWAYS
            }

            add(graphView)
        }
    }
}