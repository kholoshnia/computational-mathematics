package ru.lab.views

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.scene.control.Label
import javafx.scene.control.TableView
import ru.lab.model.Results
import tornadofx.View
import tornadofx.hbox
import tornadofx.label
import tornadofx.readonlyColumn
import tornadofx.singleAssign
import tornadofx.tableview
import tornadofx.vbox


class ResultsView : View() {
    private var rows: ObservableList<Results> = FXCollections.observableArrayList()

    private var bestValueLabel: Label by singleAssign()

    fun clear() = rows.clear()

    fun addResults(results: Results) = rows.add(results)

    fun getResults() = rows.toList()

    fun setBest(name: String) {
        bestValueLabel.text = name
    }

    override val root = vbox {
        title = "Results"
        hbox {
            label("Best approximation: ")
            bestValueLabel = label()
        }

        tableview(rows) {
            columnResizePolicy = TableView.CONSTRAINED_RESIZE_POLICY

            readonlyColumn("Name", Results::name)
            readonlyColumn("Approximation", Results::approximation)
            readonlyColumn("Deviation measure", Results::deviationMeasure)
            readonlyColumn("RMS deviation", Results::rmsDeviation)
            readonlyColumn("r^2", Results::rSquare)
            readonlyColumn("Other", Results::other)
        }
    }
}