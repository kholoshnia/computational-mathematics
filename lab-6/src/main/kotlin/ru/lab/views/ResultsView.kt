package ru.lab.views

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.scene.control.TableView
import ru.lab.model.Results
import tornadofx.View
import tornadofx.readonlyColumn
import tornadofx.tableview
import tornadofx.vbox


class ResultsView : View() {
    var rows: ObservableList<Results> = FXCollections.observableArrayList()

    override val root = vbox {
        title = "Results"

        tableview(rows) {
            prefWidth = 1000.0
            prefHeight = 500.0
            columnResizePolicy = TableView.CONSTRAINED_RESIZE_POLICY

            readonlyColumn("i", Results::i)
            readonlyColumn("xi", Results::xi)
            readonlyColumn("yi", Results::yi)
            readonlyColumn("fxy", Results::fxy)
        }
    }
}