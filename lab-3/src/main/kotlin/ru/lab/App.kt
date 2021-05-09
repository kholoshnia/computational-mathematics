package ru.lab

import com.apple.eawt.Application
import javafx.scene.image.Image
import javafx.stage.Stage
import ru.lab.views.RootView
import tornadofx.App
import java.awt.Toolkit


class App : App(RootView::class) {
    override fun start(stage: Stage) {
        val os = System.getProperty("os.name", "generic").toLowerCase()

        if (os.indexOf("mac") >= 0 || os.indexOf("darwin") >= 0) {
            val application = Application.getApplication()
            application.dockIconImage = Toolkit.getDefaultToolkit()
                .getImage("icon.png")
        }

        stage.icons.clear()
        stage.icons.add(Image("icon.png"))
        stage.titleProperty().unbind()
        stage.title = "Lab 3"
        stage.isResizable = false

        super.start(stage)
    }
}