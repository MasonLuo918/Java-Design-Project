package Test;

import MathUtil.MathUtil;
import Symbol.Line.StraightLine;
import Symbol.Symbol.Rectangle;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

    double initx, inity;
    Point2D cursorPoint;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane root = new Pane();


//        Rectangle rectangle = new Rectangle();
//        rectangle.showOperationFrame();
//        rectangle.setLayoutY(400);
//        rectangle.setLayoutX(300);
//        Button button = new Button("translate");
//        button.setOnAction(event -> {
//            rectangle.setTranslateX(rectangle.getTranslateX() + 20);
//            System.out.println(rectangle.getLayoutX());
//        });
//        Button button2 = new Button("rorate");
//        button2.setOnAction(event -> {
//            rectangle.setRotate(rectangle.getRotate() + 20);
//        });
//        Button button3 = new Button("LeftTop");
//        button3.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                double a = MathUtil.angleChangeArc(rectangle.getRotate());
//                double b = Math.cos(a) * 20;
//                System.out.println(rectangle.getTranslateX() + "*");
//                rectangle.setTranslateX((rectangle.getTranslateX() - b));
//                System.out.println(rectangle.getTranslateX() + "**");
//                rectangle.setTranslateY(rectangle.getTranslateY() - b);
//
//                rectangle.setPrefWidth(rectangle.getPrefWidth() + b);
//                rectangle.setPrefHeight(rectangle.getPrefHeight() + b);
//            }
//        });
//
//
//        rectangle.setOnMousePressed(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                rectangle.select(event);
//                initx = rectangle.getTranslateX();
//                inity = rectangle.getTranslateY();
//                cursorPoint = rectangle.sceneToParent(event.getSceneX(), event.getSceneY());
//                System.out.println("initx = " + initx + ", inity = " + inity );
//                System.out.println("cursorPointx = " + cursorPoint.getX() +", cursorPointy = " + cursorPoint.getY());
//            }
//        });
//
//        rectangle.setOnMouseDragged(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                Point2D mousePointInParent = rectangle.sceneToParent(event.getSceneX(), event.getSceneY());
//                double dragX = mousePointInParent.getX() - cursorPoint.getX();
//                double dragY = mousePointInParent.getY() - cursorPoint.getY();
//                double newPositionX = initx + dragX;
//                double newPositionY = inity + dragY;
////                System.out.println(dragX);
////                System.out.println(dragY);
//                rectangle.setTranslateX(newPositionX);
//                rectangle.setTranslateY(newPositionY);
//            }
//        });
//        button3.setLayoutX(200);
//        button2.setLayoutX(100);
        primaryStage.setScene(new Scene(root, 800, 800));
        primaryStage.setTitle("Javafx test");
        primaryStage.show();
        StraightLine line = new StraightLine(root);
        line.showOperationFrame();
    }

    public static void main(String[] args){
        launch(args);
    }
}
