package com.example.demo.view

import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.paint.Color
import tornadofx.*

//a temporary substitute for the users' icons, just a circle
class UserIconWidget(rad: Double): Fragment() {
    override val root = hbox {
        circle {
            radius = rad;
            fill = Color.CORAL;
        }
    }
}

//purple rectangle, temporary home button
class HomeWidget: Fragment() {
    override val root = hbox {
        rectangle {
            fill = Color.DARKORCHID
            width = 50.0
            height = 25.0
        }
    }
}

class PlusWidget: Fragment() {
    override val root = button {
        label("+");
    }
}

class IconAndHomeWidget(label: String): Fragment() {
    override val root = vbox {
        padding = Insets(20.0);
        val myIcon = UserIconWidget(25.0);
        val myHome = HomeWidget();
        myIcon.root.alignment = Pos.TOP_CENTER;
        myHome.root.alignment = Pos.BOTTOM_CENTER;
        add(myIcon);
        add(myHome);
        label("user " + label);
    }
}