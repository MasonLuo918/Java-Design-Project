import Manager.SymbolManage;
import Symbol.Diamond;
import Symbol.MShape;
import Symbol.Rectangle;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Map;

public class MainApp extends Application {


    private Stage stage;

    private ScrollPane leftPane;

    private Pane rightPane;

    private BorderPane rootLayout;

    private MenuBar menuBar;


    @Override
    public void start(Stage primaryStage) throws Exception {

        rootLayout = new BorderPane();
        setLeftPane(new ScrollPane());
        setRightPane(new Pane());

        Rectangle rectangle = new Rectangle();
        rightPane.getChildren().add(rectangle);
        Diamond diamond = new Diamond();
        diamond.setLayoutX(400);
        diamond.setLayoutY(200);

        rightPane.getChildren().add(diamond);


        rightPane.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

            }
        });
        rectangle.setLayoutY(200);
        rectangle.setLayoutX(200);

        rootLayout.setLeft(leftPane);
        rootLayout.setCenter(rightPane);
        leftPane.setPrefWidth(200);

        primaryStage.setScene(new Scene(rootLayout,800,800));
        primaryStage.show();
        primaryStage.setTitle("");


        rightPane.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                boolean isContained = false;
                for(Node node:rightPane.getChildren()){
                    MShape mShape = (MShape) node;
                    if(mShape.containsPointInScene(event.getSceneX(),event.getSceneY())){
                        mShape.select(event);
                        isContained = true;
                    }
                }
                if(!isContained){
                    SymbolManage.getManage().removeAll();
                }
            }
        });
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
