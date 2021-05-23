package ru.lab.views

import javafx.geometry.Insets
import javafx.scene.control.TextField
import tornadofx.*


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

        label("Series: ") {
            paddingRight = 10.0
        }

        seriesValueLabel = textfield {
            isEditable = false
            prefWidth = 1000.0
        }
    }
}