package Test;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;

public class Main extends Application {

    public static void main(String[] args) throws IOException {

        File file = new File("/Users/belle/Desktop/flowChart.fc");
        FileReader fileReader = new FileReader(file);
        BufferedReader reader = new BufferedReader(fileReader);
        StringBuffer json = new StringBuffer();
        String string;
        while((string = reader.readLine()) != null){
            json.append(string);
        }
        System.out.println(json.toString());
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane root = new Pane();
        root.setPrefSize(500,500);

        Button button = new Button("打开文件");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
