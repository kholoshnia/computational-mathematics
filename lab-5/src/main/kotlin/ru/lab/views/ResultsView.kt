package ru.lab.views

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.scene.control.Label
import javafx.scene.control.TableView
import ru.lab.model.Results
import tornadofx.View
import tornadofx.hbox
import tornadofx.label
import tornadofx.paddingAll
import tornadofx.readonlyColumn
import tornadofx.singleAssign
import tornadofx.tableview
import tornadofx.vbox


class ResultsView : View() {
    companion object {
        const val NAME = "Name"
        const val APPROXIMATION = "Approximation"
        const val DEVIATION_MEASURE = "Deviation measure"
        const val RMS_DEVIATION = "RMS deviation"
        const val R_SQUARE = "r^2"
        const val OTHER = "Other"
    }

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
            paddingAll = 5.0

            label("Best approximation: ")
            bestValueLabel = label()
        }

        tableview(rows) {
            prefWidth = 1000.0
            prefHeight = 200.0
            columnResizePolicy = TableView.CONSTRAINED_RESIZE_POLICY

            readonlyColumn(NAME, Results::name)
            readonlyColumn(APPROXIMATION, Results::approximation)
            readonlyColumn(DEVIATION_MEASURE, Results::deviationMeasure)
            readonlyColumn(RMS_DEVIATION, Results::rmsDeviation)
            readonlyColumn(R_SQUARE, Results::rSquare)
            readonlyColumn(OTHER, Results::other)
        }
    }
}