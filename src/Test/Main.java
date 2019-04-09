package Test;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane root = new Pane();

        Pane pane = new Pane();
        pane.setShape(new Circle(100,Color.GREEN));

        root.getChildren().add(pane);
        primaryStage.setScene(new Scene(root, 800, 800));
        primaryStage.setTitle("Javafx test");
        primaryStage.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}
