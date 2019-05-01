package Symbol.Line;

import Manager.SymbolManage;
import Util.MathUtil;
import Symbol.GlobalConfig;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * 使用前必须先setDrawPane
 */
public class StraightLine extends AbstractLine {

    public StraightLine(Pane drawPane) {
        super();
        setDrawPane(drawPane);
        setLineType(LineType.STRAIGHT_LINE);
        setStrokeWidth(2);
        setFill(null);
        setStartX((getDrawPane().getWidth() - getLineLength()) / 2);
        setStartY(getDrawPane().getHeight() / 2);
        setEndX((getDrawPane().getWidth() + getLineLength()) / 2);
        setEndY(getDrawPane().getHeight() / 2);
        updateLine();
        drawOperationFrame();
        initEvent();
        initOperationFrameEvent();
        drawPane.getChildren().add(this);
    }


    @Override
    public void select(MouseEvent event) {
        if (event.isControlDown()) {
            SymbolManage.getManage().getSelectedShape().addMore(this);
        } else {
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
        getCircles()[0].setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (getStartConnect().isBind()) {
                    getStartConnect().unConnect();
                }
            }
        });

        getCircles()[1].setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (getEndConnect().isBind()) {
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
                SymbolManage.getManage().detectLineEnter(startX.get(), startY.get());
            }
        });

        getCircles()[1].setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Point2D MouseInParent = sceneToParent(event.getSceneX(), event.getSceneY());
                setEndX(MouseInParent.getX());
                setEndY(MouseInParent.getY());
                SymbolManage.getManage().detectLineEnter(endX.get(), endY.get());
            }
        });
        getCircles()[0].setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Point2D point = localToParent(getCircles()[1].getCenterX(), getCircles()[1].getCenterY());
                SymbolManage.getManage().connect(StraightLine.this, getStartConnect());
                SymbolManage.getManage().getConnectManager().removeAllConnectSymbol();
            }
        });
        getCircles()[1].setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Point2D point = localToParent(getCircles()[1].getCenterX(), getCircles()[1].getCenterY());
                SymbolManage.getManage().connect(StraightLine.this, getEndConnect());
                SymbolManage.getManage().getConnectManager().removeAllConnectSymbol();
            }
        });

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
}
