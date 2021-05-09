package ru.lab.views

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.scene.Cursor
import javafx.scene.chart.NumberAxis
import javafx.scene.chart.XYChart
import tornadofx.View
import tornadofx.linechart
import tornadofx.vbox


class GraphView : View() {
    private val seriesList: ObservableList<XYChart.Series<Number, Number>> = FXCollections.observableArrayList()

    fun clear() = seriesList.clear()

    fun addSeries(series: XYChart.Series<Number, Number>) = seriesList.add(series)

    override val root = vbox {
        linechart("Function graph", NumberAxis(), NumberAxis()) {
            cursor = Cursor.CROSSHAIR
            setData(seriesList)
        }
    }
}