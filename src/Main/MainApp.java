package Main;

import Controller.MainAppController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainApp extends Application {


    private Stage stage;

    private ScrollPane leftPane;

    private Pane rightPane;

    private BorderPane rootLayout;

    private MenuBar menuBar;

    private MainApp mainApp;

    private MainAppController mainAppController;

    public MainApp(){
        mainApp = this;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        initLayout();
        initController();
        primaryStage.setScene(new Scene(rootLayout, 900, 600));
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
        Menu fileMenu = new Menu("File");
        Menu editMenu = new Menu("Edit");
        Menu helpMenu = new Menu("Help");
        menuBar.getMenus().addAll(fileMenu, editMenu, helpMenu);

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

    public static void main(String[] args){
        launch(args);
    }
}
