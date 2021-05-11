package ru.lab.controllers

import javafx.stage.FileChooser
import ru.lab.model.Results
import tornadofx.Controller


class FileController : Controller() {
    private val resultsController: ResultsController by inject()
    private val fileChooser = FileChooser()

    init {
        fileChooser.extensionFilters.add(
            FileChooser.ExtensionFilter("Supported files (*.txt, *.csv)", "*.txt", "*.csv")
        )
    }

    fun importTable(): Pair<String, String>? {
        val file = fileChooser.showOpenDialog(primaryStage)
            ?: return null

        val lines = file.readLines().map { it.replace("[,;] *".toRegex(), " ") }
        return Pair(lines.first(), lines.last())
    }

    fun exportResults(resultsList: List<Results>) {
        val file = fileChooser.showSaveDialog(primaryStage)
            ?: return

        val resultsText = if (file.extension == "csv") {
            resultsController.getResultsTable(resultsList)
        } else {
            resultsController.getResultsString(resultsList)
        }

        file.writeText(resultsText)
    }
}