package Symbol.Line;

import Manager.SymbolManage;
import Util.MathUtil;
import Symbol.GlobalConfig;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class BrokenLine extends AbstractLine {
//
//    private SimpleIntegerProperty startPointArrowType = new SimpleIntegerProperty(GlobalConfig.ARROW_LEFT);
//
//    private SimpleIntegerProperty endPointArrowType = new SimpleIntegerProperty(GlobalConfig.ARROW_BOTTOM);

    public BrokenLine(Pane drawPane){
        super();
        setDrawPane(drawPane);
        setLineType(LineType.BROKEN_LINE);
        setStrokeWidth(2);
        setFill(null);
        for(int i = 0; i < getCircles().length; i++){
            getCircles()[i] = new Circle(GlobalConfig.CIRCLE_RADIUS);
            getCircles()[i].setFill(GlobalConfig.OPERATION_FRAME_CIRCLE_COLOR);
        }
        setStartX((getDrawPane().getWidth() - getLineLength()) / 2);
        setStartY(getDrawPane().getHeight() / 2);
        setEndX((getDrawPane().getWidth() + getLineLength()) / 2);
        setEndY(getDrawPane().getHeight() / 2 + getLineLength());
        middleXProperty().bind(endX);
        middleYProperty().bind(startY);
        updateLine();
        drawOperationFrame();
        initEvent();
        initOperationFrameEvent();
        drawPane.getChildren().add(this);
    }

    @Override
    public void select(MouseEvent event) {
        if(event.isControlDown()){
            SymbolManage.getManage().getSelectedShape().addMore(this);
        }else{
            SymbolManage.getManage().getSelectedShape().add(this);
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
    public void initOperationFrameEvent() {
        for(Circle circle:getCircles()){
            circle.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    circle.setCursor(Cursor.E_RESIZE);
                }
            });
        }
        getCircles()[0].setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(getStartConnect().isBind()){
                    getStartConnect().unConnect();
                }
            }
        });
        getCircles()[1].setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(getEndConnect().isBind()){
                    getEndConnect().unConnect();
                }
            }
        });

        getCircles()[0].setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                    Point2D MouseInParent = sceneToParent(event.getSceneX(), event.getSceneY());
                    setStartX(MouseInParent.getX());
                    setStartY(MouseInParent.getY());
                    SymbolManage.getManage().detectLineEnter(getStartX(), getStartY());
            }
        });
        getCircles()[1].setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Point2D MouseInParent = sceneToParent(event.getSceneX(), event.getSceneY());
                setEndX(MouseInParent.getX());
                setEndY(MouseInParent.getY());
                SymbolManage.getManage().detectLineEnter(getEndX(),getEndY());
            }
        });
        getCircles()[0].setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Point2D point = localToParent(getCircles()[1].getCenterX(), getCircles()[1].getCenterY());
                SymbolManage.getManage().connect(BrokenLine.this, getStartConnect());
                SymbolManage.getManage().getConnectManager().removeAllConnectSymbol();
            }
        });

        getCircles()[1].setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Point2D point = localToParent(getCircles()[1].getCenterX(), getCircles()[1].getCenterY());
                SymbolManage.getManage().connect(BrokenLine.this, getEndConnect());
                SymbolManage.getManage().getConnectManager().removeAllConnectSymbol();
            }
        });
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


//        Point2D[] points1 = Util.getArrowPoint(startPointArrowType.get(), getStartX(), getStartY());
//        Point2D[] points2 = Util.getArrowPoint(endPointArrowType.get(), getEndX(), getEndY());
//        double x1 = getStartX();
//        double y1 = getStartY();
//        double x2 = points1[0].getX();
//        double y2 = points1[0].getY();
//        double x3 = points1[1].getX();
//        double y3 = points1[1].getY();
//        double x4 = getStartX();
//        double y4 = getStartY();
//        double x5 = points1[2].getX();
//        double y5 = points1[2].getY();
//
//        double x6 = getMiddleX();
//        double y6 = getMiddleY();
//
//        double x7 = points2[2].getX();
//        double y7 = points2[2].getY();
//        double x8 = points2[1].getX();
//        double y8 = points2[1].getY();
//        double x9 = getEndX();
//        double y9 = getEndY();
//        double x10 = points2[0].getX();
//        double y10 = points2[0].getY();
//        double x11 = points2[2].getX();
//        double y11 = points2[2].getY();
//        getPoints().clear();
//        getPoints().addAll( x1, y1, x2, y2, x3, y3, x4, y4, x2, y2, x5, y5, x6, y6, x7, y7, x8, y8, x9, y9, x10, y10, x11, y11);
    }
//
//    public int getStartPointArrowType() {
//        return startPointArrowType.get();
//    }
//
//    public SimpleIntegerProperty startPointArrowTypeProperty() {
//        return startPointArrowType;
//    }
//
//    public void setStartPointArrowType(int startPointArrowType) {
//        this.startPointArrowType.set(startPointArrowType);
//    }
//
//    public int getEndPointArrowType() {
//        return endPointArrowType.get();
//    }
//
//    public SimpleIntegerProperty endPointArrowTypeProperty() {
//        return endPointArrowType;
//    }
//
//    public void setEndPointArrowType(int endPointArrowType) {
//        this.endPointArrowType.set(endPointArrowType);
//    }
}
