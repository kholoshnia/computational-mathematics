package ru.lab.ui

import tornadofx.*

class RootView : View() {
    private var a: Int = 0;

    override val root = vbox {
        val label = label(a.toString())
        button("Press me").setOnMouseClicked { a++; label.text = a.toString() }
    }
}