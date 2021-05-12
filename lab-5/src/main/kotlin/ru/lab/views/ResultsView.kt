package ru.lab.views

import javafx.scene.control.TextField
import tornadofx.View
import tornadofx.hbox
import tornadofx.label
import tornadofx.paddingAll
import tornadofx.singleAssign
import tornadofx.textfield


class ResultsView : View() {
    private var seriesValueLabel: TextField by singleAssign()

    fun clear() {
        seriesValueLabel.text = ""
    }

    fun setPolynomial(polynomial: String) {
        seriesValueLabel.text = polynomial
    }

    override val root = hbox {
        paddingAll = 5.0

        label("Series: ")
        seriesValueLabel = textfield {
            isEditable = false
            prefWidth = 1000.0
        }
    }
}