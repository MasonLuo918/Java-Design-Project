package Controller;

import Manager.SymbolManage;
import Symbol.*;
import Symbol.Line.AbstractLine;
import Symbol.Line.LineType;
import Symbol.Symbol.AbstractSymbol;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polyline;

import java.awt.*;
import java.lang.reflect.Constructor;


public class RightPaneController extends Controller {
    private static RightPaneController rightPaneController = new RightPaneController();

    public Pane rightPane;

    private RightPaneController() {

    }

    public static RightPaneController getRightPaneController() {
        return rightPaneController;
    }

    public Pane getRightPane() {
        return rightPane;
    }

    public void setRightPane(Pane rightPane) {
        this.rightPane = rightPane;
    }

    @Override
    public void init() {
        rightPane.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                boolean inMyshape = false;
                try {
                    for (Node node : rightPane.getChildren()) {
                        /*
                         *  如果点击是在Symbol里的时候，选中
                         */
                        MShape mShape = (MShape) node;
                        if (mShape.containsPointInScene(event.getSceneX(), event.getSceneY())) {
                            mShape.select(event);
                            inMyshape = true;
                        }
                        /**
                         * 如果不是线条，记录下现在的鼠标的点等，以便拖拽使用
                         */
                        mShape.setInitX(node.getTranslateX());
                        mShape.setInitY(node.getTranslateY());
                        mShape.setCursorPoint(mShape.sceneToParent(event.getSceneX(), event.getSceneY()));
                    }
                } catch (Exception e) {
                }
                if (!inMyshape) {
                    SymbolManage.getManage().removeAll();
                    Point2D point2D = rightPane.sceneToLocal(event.getSceneX(), event.getSceneY());
                    try {
                        if (SymbolManage.getManage().getLeftPaneSelected() != null) {
                            MShape mShape = SymbolManage.getManage().getLeftPaneSelected();
                            rightPane.getChildren().add((Node) mShape);
                            if (!mShape.isLine()) {
                                AbstractSymbol symbol = (AbstractSymbol) mShape;
                                symbol.setLayoutX(point2D.getX() - symbol.getPrefWidth() / 2);
                                symbol.setLayoutY(point2D.getY() - symbol.getPrefHeight() / 2);
                            } else {
                                /**
                                 * 点击右边面板时，新建线条
                                 */
                                AbstractLine line = (AbstractLine) mShape;
                                int lineType = line.getLineType();
                                switch (lineType) {
                                    case LineType.STRAIGHT_LINE:
                                        line.setStartX(point2D.getX() - line.getInitLineLenght() / 2);
                                        line.setStartY(point2D.getY());
                                        line.setEndX(line.getStartX() + line.getInitLineLenght());
                                        line.setEndY(line.getStartY());
                                        break;
                                    case LineType.BROKEN_LINE:
                                        line.setStartX(point2D.getX() - line.getInitLineLenght() / 2);
                                        line.setStartY(point2D.getY());
                                        line.setMiddleX(line.getStartX() + line.getInitLineLenght());
                                        line.setMiddleY(line.getStartY());
                                        line.setEndX(line.getMiddleX());
                                        line.setEndY(line.getMiddleY() + line.getInitLineLenght());

                                }
                            }

                            SymbolManage.getManage().cancelSelected();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        });

        rightPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.isControlDown()) {
                    return;
                }
                if (SymbolManage.getManage().isOpertionDrag()) {
                    return;
                }
                Point2D eventPointInScene = rightPane.sceneToLocal(event.getSceneX(), event.getSceneY());
                for (MShape myshape : SymbolManage.getManage().getSelectedShape()) {

                    if (!myshape.isLine()) {
                        AbstractSymbol symbol = (AbstractSymbol) myshape;
                        double dragX = eventPointInScene.getX() - symbol.getCursorPoint().getX();
                        double dragY = eventPointInScene.getY() - symbol.getCursorPoint().getY();
                        double newPositionX = symbol.getInitX() + dragX;
                        double newPositionY = symbol.getInitY() + dragY;
                        if (!(symbol.getLayoutX() + newPositionX < 0 || symbol.getLayoutX() + newPositionX + symbol.getPrefWidth() > rightPane.getWidth())) {
                            symbol.setTranslateX(newPositionX);
                        }

                        if (!(symbol.getLayoutY() + newPositionY < 0 || symbol.getLayoutY() + newPositionY + symbol.getPrefHeight() > rightPane.getHeight())) {
                            symbol.setTranslateY(newPositionY);
                        }
                    }


                }
            }
        });
    }

}
