package Test;

import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;

public class Main{

    public static void main(String[] args) throws IOException {
        SimpleDoubleProperty doubleProperty1 = new SimpleDoubleProperty();

        SimpleDoubleProperty doubleProperty2 = new SimpleDoubleProperty();

        doubleProperty1.bind(doubleProperty2);

        Pane pane = new Pane();

        pane.layoutXProperty().bind(pane.layoutYProperty());

        pane.layoutXProperty().unbind();

        doubleProperty2.set(10);

        doubleProperty1.unbind();

        System.out.println(doubleProperty1.get() + " " + doubleProperty2.get());

        doubleProperty2.set(1);

        System.out.println(doubleProperty1.get() + " " + doubleProperty2.get());
    }
}
