package Symbol.Line;

import Manager.SymbolManage;
import Symbol.GlobalConfig;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 *  使用前必须先setDrawPane
 */
public class StraightLine extends AbstractLine {

    private Circle[] circles = new Circle[2];

    public StraightLine(Pane drawPane){
        setDrawPane(drawPane);
        setLineType(LineType.STRAIGHT_LINE);
        setStrokeWidth(2);
        for(int i = 0; i < circles.length; i++){
            circles[i] = new Circle(GlobalConfig.CIRCLE_RADIUS);
            circles[i].setFill(GlobalConfig.OPERATION_FRAME_CIRCLE_COLOR);
        }
        startX.set((getDrawPane().getWidth() - getInitLineLenght()) / 2);
        startY.set(getDrawPane().getHeight() / 2);
        endX.set((getDrawPane().getWidth() + getInitLineLenght()) / 2);
        endY.set(getDrawPane().getHeight() / 2);
//        circles[0].centerXProperty().bind(startX);
//        circles[0].centerYProperty().bind(startY);
//        circles[1].centerYProperty().bind(endY);
//        circles[1].centerXProperty().bind(endX);
        updateLine();
        drawOperationFrame();
        initEvent();
        initOperationFrameEvent();
        drawPane.getChildren().add(this);
    }

    @Override
    public void showOperationFrame() {
        for(Circle circle:circles){
            getDrawPane().getChildren().add(circle);
        }
    }

    @Override
    public void hideOperationFrame() {
        for(Circle circle:circles){
            getDrawPane().getChildren().remove(circle);
        }
    }

    @Override
    public void select(MouseEvent event) {
        if(event.isControlDown()){
            SymbolManage.getManage().addMore(this);
        }else{
            SymbolManage.getManage().add(this);
        }
    }

    @Override
    public void initEvent() {
        startX.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                updateLine();
                drawOperationFrame();
            }
        });
        startY.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                updateLine();
                drawOperationFrame();
            }
        });
        endX.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                updateLine();
                drawOperationFrame();
            }
        });
        endY.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                updateLine();
                drawOperationFrame();
            }
        });

        setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setStroke(Color.RED);
            }
        });

        setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setStroke(Color.BLACK);
            }
        });

    }

    @Override
    public void initOperationFrameEvent() {
        circles[0].setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Point2D MouseInParent = sceneToParent(event.getSceneX(), event.getSceneY());
                startX.set(MouseInParent.getX());
                startY.set(MouseInParent.getY());
            }
        });

        circles[1].setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Point2D MouseInParent = sceneToParent(event.getSceneX(), event.getSceneY());
                endX.set(MouseInParent.getX());
                endY.set(MouseInParent.getY());
            }
        });
    }

    @Override
    public Point2D sceneToParent(double x, double y) {
        Point2D toLocal = sceneToLocal(x,y);
        return toLocal;
    }

    @Override
    public boolean containsPointInScene(double x, double y) {
        return getBoundsInScene().contains(x,y);
    }

    @Override
    public Bounds getBoundsInScene() {
        return localToScene(getBoundsInLocal());
    }

    @Override
    public void drawOperationFrame() {
        circles[0].setCenterX(startX.get());
        circles[0].setCenterY(startY.get());
        circles[1].setCenterX(endX.get());
        circles[1].setCenterY(endY.get());
    }

    @Override
    public void updateLine() {
        double x1 = startX.get();
        double y1 = startY.get();
        double x2 = endX.get();
        double y2 = endY.get();
        getPoints().clear();
        getPoints().addAll(x1, y1, x2, y2);
    }

    @Override
    public double getMiddleX() {
        return 0;
    }

    /**
     * 直线取消middle属性
     */
    @Override
    public SimpleDoubleProperty middleXProperty() {
        return null;
    }

    @Override
    public void setMiddleX(double middleX) {
        return;
    }

    @Override
    public double getMiddleY() {
        return 0;
    }

    @Override
    public SimpleDoubleProperty middleYProperty() {
        return null;
    }

    @Override
    public void setMiddleY(double middleY) {
        return;
    }

}
