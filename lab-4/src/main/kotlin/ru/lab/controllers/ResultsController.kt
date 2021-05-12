package ru.lab.controllers

import ru.lab.model.Results
import ru.lab.views.ResultsView
import tornadofx.Controller

class ResultsController : Controller() {
    companion object {
        private const val NEW_LINE = "\n"
        private const val COLON_SPACE = ": %s"
        private const val COMMA = ","
    }

    fun getResultsString(results: List<Results>): String {
        return results.joinToString(NEW_LINE) {
            var result = ResultsView.NAME + COLON_SPACE.format(it.name) + NEW_LINE +
                    ResultsView.APPROXIMATION + COLON_SPACE.format(it.approximation) + NEW_LINE +
                    ResultsView.DEVIATION_MEASURE + COLON_SPACE.format(it.deviationMeasure) + NEW_LINE +
                    ResultsView.RMS_DEVIATION + COLON_SPACE.format(it.rmsDeviation) + NEW_LINE +
                    ResultsView.R_SQUARE + COLON_SPACE.format(it.rSquare) + NEW_LINE

            if (it.other.isNotBlank()) {
                result += ResultsView.OTHER + COLON_SPACE.format(it.other) + NEW_LINE
            }

            result
        }
    }

    fun getResultsTable(results: List<Results>): String {
        val table = ResultsView.NAME + COMMA +
                ResultsView.APPROXIMATION + COMMA +
                ResultsView.DEVIATION_MEASURE + COMMA +
                ResultsView.RMS_DEVIATION + COMMA +
                ResultsView.R_SQUARE + COMMA +
                ResultsView.OTHER + NEW_LINE

        return table + results.joinToString(NEW_LINE) {
            it.name + COMMA +
                    it.approximation + COMMA +
                    it.deviationMeasure + COMMA +
                    it.rmsDeviation + COMMA +
                    it.rSquare + COMMA +
                    it.other
        }
    }
}