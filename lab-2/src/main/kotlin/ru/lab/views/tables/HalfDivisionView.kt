package ru.lab.views.tables

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.scene.control.TableView
import ru.lab.model.results.HalfDivisionResults
import tornadofx.View
import tornadofx.readonlyColumn
import tornadofx.tableview
import tornadofx.vbox

class HalfDivisionView : View() {
    var rows: ObservableList<HalfDivisionResults> = FXCollections.observableArrayList()

    override val root = vbox {
        title = "Half division"

        tableview(rows) {
            columnResizePolicy = TableView.CONSTRAINED_RESIZE_POLICY

            readonlyColumn("№", HalfDivisionResults::n)
            readonlyColumn("a", HalfDivisionResults::a)
            readonlyColumn("b", HalfDivisionResults::b)
            readonlyColumn("x", HalfDivisionResults::x)
            readonlyColumn("f(a)", HalfDivisionResults::fa)
            readonlyColumn("f(b)", HalfDivisionResults::fb)
            readonlyColumn("f(x)", HalfDivisionResults::fx)
            readonlyColumn("|a-b|", HalfDivisionResults::ab)
        }
    }
}