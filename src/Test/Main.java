package Test;

import javafx.application.Application;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;


public class Main extends Application {

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane pane = new Pane();
        Text text = new Text("你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好");
        pane.getChildren().add(text);
        Scene scene = new Scene(pane,400,400);
        primaryStage.setScene(scene);
        primaryStage.show();
        text.setWrappingWidth(pane.getWidth());
        text.setTextAlignment(TextAlignment.CENTER);
        Bounds bounds = text.getBoundsInParent();
        double height = bounds.getHeight();
        text.setY(pane.getHeight() / 2 - height);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
