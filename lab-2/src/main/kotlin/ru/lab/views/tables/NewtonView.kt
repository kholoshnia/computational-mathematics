package ru.lab.views.tables

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.scene.control.TableView
import ru.lab.model.results.NewtonResults
import tornadofx.View
import tornadofx.readonlyColumn
import tornadofx.tableview
import tornadofx.vbox

class NewtonView : View() {
    var rows: ObservableList<NewtonResults> = FXCollections.observableArrayList()

    override val root = vbox {
        title = "Newton"

        tableview(rows) {
            columnResizePolicy = TableView.CONSTRAINED_RESIZE_POLICY

            readonlyColumn("№", NewtonResults::n)
            readonlyColumn("xk", NewtonResults::xk)
            readonlyColumn("fxk", NewtonResults::fxk)
            readonlyColumn("dxk", NewtonResults::dxk)
            readonlyColumn("xk+1", NewtonResults::xkNext)
            readonlyColumn("|xk - xk+1|", NewtonResults::xkXkNext)
        }
    }
}