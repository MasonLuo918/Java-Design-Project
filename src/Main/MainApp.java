package Main;

import Controller.MainAppController;
import FileMenu.Writer;
import Manager.SymbolManage;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class MainApp extends Application {


    private Stage stage;

    private ScrollPane leftPane;

    private Pane rightPane;

    private BorderPane rootLayout;

    private Scene scene;

    private MenuBar menuBar;

    private MainApp mainApp;

    private MainAppController mainAppController;

    public MainApp(){
        mainApp = this;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        initLayout();
        scene = new Scene(rootLayout,900,800);
        SymbolManage.getManage().setMainApp(this); //连接Manage和MainApp
        initController();
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setTitle("Flow Chart");
    }

    public void initLayout(){
        rootLayout = new BorderPane();
        leftPane = new ScrollPane();
        leftPane.setPrefWidth(220);
        rightPane = new Pane();
        /**
         * 初始化菜单栏，分别有File，Edit，Help三个菜单
         */
        menuBar = new MenuBar();
        rootLayout.setTop(menuBar);
        rootLayout.setCenter(rightPane);
        rootLayout.setLeft(leftPane);
    }

    public void initController(){
        mainAppController = new MainAppController(this);
        mainAppController.init();
    }

    public MainApp getMainApp() {
        return mainApp;
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public ScrollPane getLeftPane() {
        return leftPane;
    }

    public void setLeftPane(ScrollPane leftPane) {
        this.leftPane = leftPane;
    }

    public Pane getRightPane() {
        return rightPane;
    }

    public void setRightPane(Pane rightPane) {
        this.rightPane = rightPane;
    }

    public BorderPane getRootLayout() {
        return rootLayout;
    }

    public void setRootLayout(BorderPane rootLayout) {
        this.rootLayout = rootLayout;
    }

    public MenuBar getMenuBar() {
        return menuBar;
    }

    public void setMenuBar(MenuBar menuBar) {
        this.menuBar = menuBar;
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public static void main(String[] args){
        launch(args);
    }
}
