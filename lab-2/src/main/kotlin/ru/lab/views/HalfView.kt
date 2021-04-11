package ru.lab.views

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import ru.lab.model.Half
import tornadofx.View
import tornadofx.readonlyColumn
import tornadofx.tableview
import tornadofx.vbox

class HalfView : View() {
    var rows: ObservableList<Half> = FXCollections.observableArrayList()

    override val root = vbox {
        tableview(rows) {
            readonlyColumn("a", Half::a)
            readonlyColumn("b", Half::b)
            readonlyColumn("x", Half::x)
            readonlyColumn("f(a)", Half::fa)
            readonlyColumn("f(b)", Half::fb)
            readonlyColumn("f(x)", Half::fx)
            readonlyColumn("|a-b|", Half::ab)

            // columnResizePolicy = SmartResize.POLICY
        }
    }
}