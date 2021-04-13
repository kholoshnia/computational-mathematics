package ru.lab.views.tables

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.scene.control.TableView
import ru.lab.model.results.SimpleIterationsResults
import tornadofx.View
import tornadofx.readonlyColumn
import tornadofx.tableview
import tornadofx.vbox

class SimpleIterationsView : View() {
    var rows: ObservableList<SimpleIterationsResults> = FXCollections.observableArrayList()

    override val root = vbox {
        title = "Simple iterations"

        tableview(rows) {
            columnResizePolicy = TableView.CONSTRAINED_RESIZE_POLICY

            readonlyColumn("№", SimpleIterationsResults::n)
            readonlyColumn("xk", SimpleIterationsResults::xk)
            readonlyColumn("fxk", SimpleIterationsResults::xkNext)
            readonlyColumn("xk+1", SimpleIterationsResults::xkNext)
            readonlyColumn("fi(xk)", SimpleIterationsResults::fiXk)
            readonlyColumn("│xk − xk+1│", SimpleIterationsResults::xkXkNext)
        }
    }
}