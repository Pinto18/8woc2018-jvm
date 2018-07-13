package com.example.demo.view
import javafx.scene.paint.Color
import tornadofx.*;

class DatagridDemo: View("Datagrid Demo") {

    val pad = 60.0;
    private val home = pane() {
        val background = rectangle {
            x = 0.0;
            y = 0.0;
            height = 100.0;
            width = 300.0;
            fill = c("#E56060");
        }

        val body = rectangle {
            x = 125.0;
            y = 40.0;
            height = 40.0;
            width = 50.0;
            fill = Color.WHITE;
        }

        val door = rectangle {
            x = 140.0;
            y = 60.0;
            height = 20.0;
            width = 20.0;
            fill = c("#E56060");
        }

        val roof = polygon {
            getPoints().setAll(
                    125.0, 40.0,
                    175.0, 40.0,
                    150.0, 20.0
            )
            fill = Color.WHITE;
        }

        add(background);
        add(body);
        add(roof);
    }

    //set the root of the view to the welcomeScreen
    override val root = home;
}

