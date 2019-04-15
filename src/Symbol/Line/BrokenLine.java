package Symbol.Line;

import Manager.SymbolManage;
import MathUtil.MathUtil;
import Symbol.GlobalConfig;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class BrokenLine extends AbstractLine {

    private Circle[] circles = new Circle[3];

    public BrokenLine(Pane drawPane){
        setDrawPane(drawPane);
        setLineType(LineType.BROKEN_LINE);
        setStrokeWidth(2);
        setFill(null);
        for(int i = 0; i < circles.length; i++){
            circles[i] = new Circle(GlobalConfig.CIRCLE_RADIUS);
            circles[i].setFill(GlobalConfig.OPERATION_FRAME_CIRCLE_COLOR);
        }
        startX.set((getDrawPane().getWidth() - getLineLength()) / 2);
        startY.set(getDrawPane().getHeight() / 2);
        middleX.set((getDrawPane().getWidth() + getLineLength()) / 2);
        middleY.set(getDrawPane().getHeight() / 2);
        endX.set((getDrawPane().getWidth() + getLineLength()) / 2);
        endY.set(getDrawPane().getHeight() / 2 + getLineLength());
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

        middleX.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                updateLine();
                drawOperationFrame();
            }
        });

        middleY.addListener(new ChangeListener<Number>() {
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
    public boolean containsPointInScene(double x, double y) {
        boolean inOperationCircle = false;
        for(Circle circle: circles){
            Bounds local = circle.getBoundsInLocal();
            Bounds scene = circle.localToScene(local);
            if(scene.contains(x, y)){
                inOperationCircle = true;
                break;
            }
        }
        if(!inOperationCircle){
            return getBoundsInScene().contains(x,y);
        }else{
            return false;
        }
    }

    @Override
    public void initOperationFrameEvent() {
        for(Circle circle:circles){
            circle.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    circle.setCursor(Cursor.E_RESIZE);
                }
            });
        }
        circles[0].setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Point2D MouseInParent = sceneToParent(event.getSceneX(), event.getSceneY());
                setStartX(MouseInParent.getX());
                setStartY(MouseInParent.getY());
                setMiddleY(MouseInParent.getY());
            }
        });
        circles[1].setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Point2D MouseInParent = sceneToParent(event.getSceneX(), event.getSceneY());
                setMiddleX(MouseInParent.getX());
                setMiddleY(MouseInParent.getY());
                setStartY(MouseInParent.getY());
                setEndX(MouseInParent.getX());
            }
        });
        circles[2].setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Point2D MouseInParent = sceneToParent(event.getSceneX(), event.getSceneY());
                setMiddleX(MouseInParent.getX());
                setEndX(MouseInParent.getX());
                setEndY(MouseInParent.getY());
            }
        });
    }

    @Override
    public void drawOperationFrame() {
        circles[0].setCenterX(getStartX());
        circles[0].setCenterY(getStartY());
        circles[1].setCenterX(getMiddleX());
        circles[1].setCenterY(getMiddleY());
        circles[2].setCenterX(getEndX());
        circles[2].setCenterY(getEndY());
    }

    @Override
    public void updateLine() {
        double x1 = getStartX();
        double y1 = getStartY();
        double arc1 = MathUtil.getArcOfX(getStartX(), getStartY(), getMiddleX(), getMiddleY());
        double arc2 = MathUtil.getArcOfX(getMiddleX(), getMiddleY(), getEndX(), getEndY());
        arc2 *= -1;
        double height = GlobalConfig.ARROW_LENGTH * (Math.sqrt(3) / 2);
        double x2 = getStartX() + height * Math.cos(arc1);
        double y2 = getStartY() - height * Math.sin(arc1);
        double x3 = x2 - GlobalConfig.ARROW_LENGTH / 2 * Math.sin(arc1);
        double y3 = y2 - GlobalConfig.ARROW_LENGTH / 2 * Math.cos(arc1);
        double x4 = x2 + GlobalConfig.ARROW_LENGTH / 2 * Math.sin(arc1);
        double y4 = y2 + GlobalConfig.ARROW_LENGTH / 2 * Math.cos(arc1);

        double middleX = getMiddleX();
        double middleY = getMiddleY();

        double x5 = getEndX() - height * Math.cos(arc2);
        double y5 = getEndY() - height * Math.sin(arc2);

        double x6 = x5 - GlobalConfig.ARROW_LENGTH / 2 * Math.sin(arc2);
        double y6 = y5 - GlobalConfig.ARROW_LENGTH / 2 * Math.cos(arc2);

        double x7 = getEndX();
        double y7 = getEndY();

        double x8 = x5 + GlobalConfig.ARROW_LENGTH / 2 * Math.sin(arc2);
        double y8 = y5 + GlobalConfig.ARROW_LENGTH / 2 * Math.cos(arc2);

        getPoints().clear();
        getPoints().addAll(x1, y1, x3, y3, x4, y4, x1, y1, x3 ,y3, x2, y2,middleX, middleY, x5, y5, x6 , y6, x7, y7, x8, y8, x5, y5);
//
    }
}
