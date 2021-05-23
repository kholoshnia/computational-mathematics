package ru.lab.views

import javafx.geometry.Insets
import javafx.scene.control.TextField
import tornadofx.*


class ResultsView : View() {
    private var seriesValueLabel: TextField by singleAssign()
    private var valueValueLabel: TextField by singleAssign()

    fun clear() {
        seriesValueLabel.text = ""
        valueValueLabel.text = ""
    }

    fun setPolynomial(polynomial: String) {
        seriesValueLabel.text = polynomial
    }

    fun setValue(value: String) {
        valueValueLabel.text = value
    }

    override val root = vbox(10) {
        paddingLeft = 10.0

        hbox(10) {
            label("Series: ")
            seriesValueLabel = textfield {
                isEditable = false
                prefWidth = 1000.0
            }
        }

        hbox(10) {
            paddingBottom = 10.0

            label("Value: ")
            valueValueLabel = textfield {
                isEditable = false
            }
        }
    }
}