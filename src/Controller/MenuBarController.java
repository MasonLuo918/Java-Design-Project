package Controller;

import FileMenu.Exporter;
import FileMenu.Reader;
import FileMenu.Writer;
import FileMenu.newPane;
import Main.MainApp;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;


public class MenuBarController extends Controller {

    private MainApp mainApp;

    private Stage stage = new Stage();

    @Override
    public void init() {
        MenuBar menuBar = mainApp.getMenuBar();
        Menu fileMenu = new Menu("File");
        Menu editMenu = new Menu("Edit");
        Menu helpMenu = new Menu("Help");
        menuBar.getMenus().addAll(fileMenu, editMenu, helpMenu);

        MenuItem saveItem = new MenuItem("Save");
        MenuItem newItem = new MenuItem("New");
        MenuItem saveAsItem = new MenuItem("Save As");
        MenuItem openItem = new MenuItem("Open");
        MenuItem exportItem = new MenuItem("Export");
        fileMenu.getItems().addAll(newItem,saveItem, saveAsItem, openItem,exportItem);
        saveItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setInitialFileName("flowChart");
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter(".fc", "*.fc")
                );
                try {
                    File file = fileChooser.showSaveDialog(stage);
                    if(file != null){
                        Writer writer = new Writer();
                        writer.save(file,getMainApp().getRightPane());
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        });

        saveAsItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter(".fc","*.fc")
                );
                try{
                    File file = fileChooser.showOpenDialog(stage);
                    if(file != null){
                        Writer writer = new Writer();
                        writer.save(file,getMainApp().getRightPane());
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        });

        openItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter(".fc","*.fc")
                );
                try{
                    File file = fileChooser.showOpenDialog(stage);
                    if(file != null){
                        Reader reader = new Reader();
                        String json = reader.read(file);
                        RightPaneController.getRightPaneController().generate(json);
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        });

        exportItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser fileChoose = new FileChooser();
                fileChoose.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("png图片","*.png"),
                        new FileChooser.ExtensionFilter("jpg图片","*.jpg")
                );
                    File file = fileChoose.showSaveDialog(stage);
                    if(file!=null){
                        Exporter.export(file,getMainApp().getRightPane());
                    }
            }
        });

        newItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                newPane.createNewPane(getMainApp().getRightPane());
            }
        });
    }

    public MainApp getMainApp() {
        return mainApp;
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
}
