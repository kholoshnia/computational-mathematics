package ru.lab.views

import javafx.scene.control.Label
import tornadofx.View
import tornadofx.hbox
import tornadofx.label
import tornadofx.paddingTop
import tornadofx.singleAssign
import tornadofx.vbox


class ResultsView : View() {
    private var valueLabel: Label by singleAssign()
    private var partitioningLabel: Label by singleAssign()

    var valueValue: Label by singleAssign()
    var partitioningValue: Label by singleAssign()

    override val root = vbox(20) {
        paddingTop = 10

        hbox {
            valueLabel = label("Value: ")
            valueValue = label()
        }

        hbox {
            partitioningLabel = label("Partitioning: ")
            partitioningValue = label()
        }
    }
}