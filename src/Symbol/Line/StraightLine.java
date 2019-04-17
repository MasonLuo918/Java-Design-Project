package Symbol.Line;

import Manager.SymbolManage;
import MathUtil.MathUtil;
import Symbol.GlobalConfig;
import javafx.beans.property.SimpleDoubleProperty;
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
import sun.security.action.GetLongAction;

/**
 *  使用前必须先setDrawPane
 */
public class StraightLine extends AbstractLine {

    private Circle[] circles = new Circle[2];

    public StraightLine(Pane drawPane){
        super();
        setDrawPane(drawPane);
        setLineType(LineType.STRAIGHT_LINE);
        setStrokeWidth(2);
        setFill(null);
        for(int i = 0; i < circles.length; i++){
            circles[i] = new Circle(GlobalConfig.CIRCLE_RADIUS);
            circles[i].setFill(GlobalConfig.OPERATION_FRAME_CIRCLE_COLOR);
        }
        startX.set((getDrawPane().getWidth() - getLineLength()) / 2);
        startY.set(getDrawPane().getHeight() / 2);
        endX.set((getDrawPane().getWidth() + getLineLength()) / 2);
        endY.set(getDrawPane().getHeight() / 2);
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
        circles[0].setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(getStartConnect().isBind()){
                    getEndConnect().unConnect();
                }
            }
        });

        circles[1].setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(getEndConnect().isBind()){
                    getEndConnect().unConnect();
                }
            }
        });

        circles[0].setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Point2D MouseInParent = sceneToParent(event.getSceneX(), event.getSceneY());
                startX.set(MouseInParent.getX());
                startY.set(MouseInParent.getY());
                SymbolManage.getManage().detectLineEnter(startX.get(), startY.get());
            }
        });

        circles[1].setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Point2D MouseInParent = sceneToParent(event.getSceneX(), event.getSceneY());
                endX.set(MouseInParent.getX());
                endY.set(MouseInParent.getY());
                SymbolManage.getManage().detectLineEnter(endX.get(), endY.get());
            }
        });
        circles[0].setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Point2D point = localToParent(circles[1].getCenterX(), circles[1].getCenterY());
                SymbolManage.getManage().connect(StraightLine.this, getStartConnect());
                SymbolManage.getManage().removeAllConnectSymbol();
            }
        });
        circles[1].setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Point2D point = localToParent(circles[1].getCenterX(), circles[1].getCenterY());
                SymbolManage.getManage().connect(StraightLine.this, getEndConnect());
                SymbolManage.getManage().removeAllConnectSymbol();
            }
        });

        for(Circle circle:circles){
            circle.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    circle.setCursor(Cursor.E_RESIZE);
                }
            });
        }
    }



    @Override
    public boolean containsPointInScene(double x, double y) {
        boolean inOperationCircle = false;
        for(Circle circle: circles){
            Bounds toLocal = circle.getBoundsInLocal();
            Bounds toScene = circle.localToScene(toLocal);
            if(toScene.contains(x, y)){
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
    public void drawOperationFrame() {
        circles[0].setCenterX(startX.get());
        circles[0].setCenterY(startY.get());
        circles[1].setCenterX(endX.get());
        circles[1].setCenterY(endY.get());
    }

    @Override
    public void updateLine() {

        /**
         * 更新线段，带箭头
         */
        double x1 = getStartX();
        double y1 = getStartY();
        double arc = MathUtil.getArcOfX(getStartX(), getStartY(), getEndX(), getEndY());
        double height = GlobalConfig.ARROW_LENGTH * (Math.sqrt(3) / 2);
        double x2 = getEndX() - height * Math.cos(arc);
        double y2 = getEndY() + height * Math.sin(arc);
        double x3 = x2 - GlobalConfig.ARROW_LENGTH / 2 * Math.sin(arc);
        double y3 = y2 - GlobalConfig.ARROW_LENGTH / 2 * Math.cos(arc);
        double x4 = getEndX();
        double y4 = getEndY();
        double x5 = x2 + GlobalConfig.ARROW_LENGTH / 2 * Math.sin(arc);
        double y5 = y2 + GlobalConfig.ARROW_LENGTH / 2 * Math.cos(arc);
        getPoints().clear();
        getPoints().addAll(x1, y1, x2, y2, x3, y3, x4, y4, x5, y5, x2, y2);

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
