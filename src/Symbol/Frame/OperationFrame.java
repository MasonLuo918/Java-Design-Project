package Symbol.Frame;

import Manager.SymbolManage;
import MathUtil.MathUtil;
import Symbol.GlobalConfig;
import Symbol.Symbol.AbstractSymbol;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class OperationFrame {

    /*
     * 操作框显示的面板
     */
    AbstractSymbol drawPane;

    /*
     * 操作圆, circles[0]为旋转圆, 其他按顺序依次为左上角为第一个，顺时针数
     */
    Circle[] circles = new Circle[9];

    /**
     * 操作线, lines[0]为连接旋转圆的线,其他依次为
     */
    Line[] lines = new Line[5];


    public OperationFrame(AbstractSymbol drawPane){

        this.drawPane = drawPane;

        /*
         * 给操作框对象分配内存空间
         */
        for (int i = 0; i < circles.length; i++) {
            circles[i] = new Circle(GlobalConfig.CIRCLE_RADIUS);
            circles[i].setFill(GlobalConfig.OPERATION_FRAME_CIRCLE_COLOR);
            circles[i].setCursor(Cursor.E_RESIZE);
        }
        for (int i = 0; i < lines.length; i++) {
            lines[i] = new Line();
            lines[i].setStroke(GlobalConfig.OPERATION_FRAME_LINE_COLOR);
            lines[i].getStrokeDashArray().add(3.0);  // 设置为虚线
        }
    }

    public void showOperationFrame() {
        hideOperationFrame();
        drawOperationFrame();
        for (Circle circle : circles) {
            drawPane.getChildren().add(circle);
        }

        for (Line line : lines) {
            drawPane.getChildren().add(line);
        }
    }

    public void hideOperationFrame() {
        for (Circle circle : circles) {
            drawPane.getChildren().remove(circle);
        }

        for (Line line : lines) {
            drawPane.getChildren().remove(line);
        }
    }


    /**
     * 画出操作框，更新操作框的位置
     */
    public void drawOperationFrame() {
        circles[0].setCenterX(drawPane.getPrefWidth() / 2);
        circles[0].setCenterY(-GlobalConfig.OFFSET);

        circles[1].setCenterX(0);
        circles[1].setCenterY(0);

        circles[2].setCenterX(drawPane.getPrefWidth() / 2);
        circles[2].setCenterY(0);

        circles[3].setCenterX(drawPane.getPrefWidth());
        circles[3].setCenterY(0);

        circles[4].setCenterX(drawPane.getPrefWidth());
        circles[4].setCenterY(drawPane.getPrefHeight() / 2);

        circles[5].setCenterX(drawPane.getPrefWidth());
        circles[5].setCenterY(drawPane.getPrefHeight());

        circles[6].setCenterX(drawPane.getPrefWidth() / 2);
        circles[6].setCenterY(drawPane.getPrefHeight());

        circles[7].setCenterX(0);
        circles[7].setCenterY(drawPane.getPrefHeight());

        circles[8].setCenterX(0);
        circles[8].setCenterY(drawPane.getPrefHeight() / 2);


        lines[0].setStartX(drawPane.getPrefWidth() / 2);
        lines[0].setStartY(GlobalConfig.CIRCLE_RADIUS - GlobalConfig.OFFSET);
        lines[0].setEndX(drawPane.getPrefWidth() / 2);
        lines[0].setEndY(-GlobalConfig.CIRCLE_RADIUS);

        lines[1].setStartX(GlobalConfig.CIRCLE_RADIUS);
        lines[1].setStartY(0);
        lines[1].setEndX(drawPane.getPrefWidth() - GlobalConfig.CIRCLE_RADIUS);
        lines[1].setEndY(0);

        lines[2].setStartX(drawPane.getPrefWidth());
        lines[2].setStartY(0);
        lines[2].setEndX(drawPane.getPrefWidth());
        lines[2].setEndY(drawPane.getPrefHeight() - GlobalConfig.CIRCLE_RADIUS);

        lines[3].setStartX(drawPane.getPrefWidth() - GlobalConfig.CIRCLE_RADIUS);
        lines[3].setStartY(drawPane.getPrefHeight());
        lines[3].setEndX(GlobalConfig.CIRCLE_RADIUS);
        lines[3].setEndY(drawPane.getPrefHeight());


        lines[4].setStartX(0);
        lines[4].setStartY(drawPane.getPrefHeight() - GlobalConfig.CIRCLE_RADIUS);
        lines[4].setEndX(0);
        lines[4].setEndY(0);


    }

    /**
     * 初始化操作框的事件
     */
    public void initOperationFrameEvent() {

        for (Circle circle : circles) {

            /**
             * 当监测到按下操作圆的时候，将OperationDrag设置为true，以便禁止拖拽
             */
            circle.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    SymbolManage symbolManage = SymbolManage.getManage();
                    symbolManage.setOperationDrag(true);
                    drawPane.setInitX(drawPane.getTranslateX());
                    drawPane.setInitY( drawPane.getTranslateY());
                    drawPane.setCursorPoint( drawPane.sceneToParent(event.getSceneX(), event.getSceneY()));
                }
            });

            circle.setOnMouseReleased(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    SymbolManage symbolManage = SymbolManage.getManage();
                    symbolManage.setOperationDrag(false);
                }
            });
            circle.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    circle.setFill(Color.RED);
                }
            });
            circle.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    circle.setFill(GlobalConfig.OPERATION_FRAME_CIRCLE_COLOR);
                }
            });
        }

        circles[0].setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Point2D eventCoorRelativeToParent =  drawPane.sceneToParent(event.getSceneX(), event.getSceneY());
                double shapeCenterX =  drawPane.getPrefWidth() / 2 +  drawPane.getLayoutX() +  drawPane.getTranslateX();
                double shapeCenterY =  drawPane.getPrefHeight() / 2 +  drawPane.getLayoutY() +  drawPane.getTranslateY();
                double ax = 0;
                double ay = shapeCenterY -  drawPane.getLayoutY();
                double bx = eventCoorRelativeToParent.getX() - shapeCenterX;
                double by = shapeCenterY - eventCoorRelativeToParent.getY();
                double a = shapeCenterY -  drawPane.getLayoutY();
                double b = MathUtil.distance(shapeCenterX, shapeCenterY, eventCoorRelativeToParent.getX(),
                        eventCoorRelativeToParent.getY());
                double arc = Math.acos((ax * bx + ay * by) / (a * b));
                double angle = MathUtil.arcChangeAngle(arc);
                if (bx < 0) {
                    angle *= -1;
                }
                drawPane.setRotate(angle);

            }
        });

        circles[1].setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Point2D mousePointInParent =  drawPane.sceneToParent(event.getSceneX(), event.getSceneY());
                double dragX = mousePointInParent.getX() -  drawPane.getCursorPoint().getX();
                double dragY = mousePointInParent.getY() -  drawPane.getCursorPoint().getY();
                double newPositionX = ( drawPane.getInitX() + dragX);
                double newPositionY = ( drawPane.getInitY() + dragY);
                double oldWidth =  drawPane.getPrefWidth();
                double oldHeight =  drawPane.getPrefHeight();
                double addWidth = ( drawPane.getTranslateX() - newPositionX);
                double addHeight = ( drawPane.getTranslateY() - newPositionY);
                drawPane.setPrefWidth( drawPane.getPrefWidth() + addWidth);
                if ( drawPane.getPrefWidth() != oldWidth) {
                    drawPane.setTranslateX(newPositionX);
                }
                drawPane.setPrefHeight( drawPane.getPrefHeight() + addHeight);
                if ( drawPane.getPrefHeight() != oldHeight) {
                    drawPane.setTranslateY(newPositionY);
                }
            }
        });

        circles[2].setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Point2D mousePointInParent =  drawPane.sceneToParent(event.getSceneX(), event.getSceneY());
                double dragY = mousePointInParent.getY() -  drawPane.getCursorPoint().getY();
                double newPositionY = ( drawPane.getInitY() + dragY);
                double oldHeight =  drawPane.getPrefHeight();
                double addHeight = ( drawPane.getTranslateY() - newPositionY);
                drawPane.setPrefHeight( drawPane.getPrefHeight() + addHeight);
                if (oldHeight !=  drawPane.getPrefHeight()) {
                    drawPane.setTranslateY(newPositionY);
                }
            }
        });

        circles[3].setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Point2D mousePointInParent =  drawPane.sceneToParent(event.getSceneX(), event.getSceneY());
                double dragY = mousePointInParent.getY() -  drawPane.getCursorPoint().getY();
                double newPositionY =  drawPane.getInitY() + dragY;
                double addHeight = ( drawPane.getTranslateY() - newPositionY);
                double oldHeight =  drawPane.getPrefHeight();
                drawPane.setPrefWidth(mousePointInParent.getX() -  drawPane.getInitX() -  drawPane.getLayoutX());
                drawPane.setPrefHeight( drawPane.getPrefHeight() + addHeight);
                if (oldHeight !=  drawPane.getPrefHeight()) {
                    drawPane.setTranslateY(newPositionY);
                }
            }
        });
        circles[4].setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Point2D mousePointInParent = drawPane.sceneToParent(event.getSceneX(), event.getSceneY());
                double dragX = mousePointInParent.getX() - drawPane.getCursorPoint().getX();
                double newPositionX = drawPane.getInitX() + dragX;
                double addWeight = newPositionX - drawPane.getTranslateX();
                drawPane.setPrefWidth(mousePointInParent.getX() - drawPane.getInitX() - drawPane.getLayoutX());
            }
        });
        circles[5].setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Point2D mousePointInParent = drawPane.sceneToParent(event.getSceneX(), event.getSceneY());
                drawPane.setPrefWidth(mousePointInParent.getX() - drawPane.getInitX() - drawPane.getLayoutX());
                drawPane.setPrefHeight(mousePointInParent.getY() - drawPane.getInitY() - drawPane.getLayoutY());
            }
        });
        circles[6].setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Point2D mousePointInParent = drawPane.sceneToParent(event.getSceneX(), event.getSceneY());
                drawPane.setPrefHeight(mousePointInParent.getY() - drawPane.getInitY() - drawPane.getLayoutY());
            }
        });
        circles[7].setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Point2D mousePointInParent = drawPane.sceneToParent(event.getSceneX(), event.getSceneY());
                double dragX = mousePointInParent.getX() - drawPane.getCursorPoint().getX();
                double newPositionX = drawPane.getInitX() + dragX;
                double oldWidth = drawPane.getPrefWidth();
                double addWidth = drawPane.getTranslateX() - newPositionX;
                drawPane.setPrefWidth(drawPane.getPrefWidth() + addWidth);
                if (drawPane.getPrefWidth() != oldWidth) {
                    drawPane.setTranslateX(newPositionX);
                }
                drawPane.setPrefHeight(mousePointInParent.getY() - drawPane.getInitY() - drawPane.getLayoutY());
            }
        });
        circles[8].setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Point2D mousePointInParent = drawPane.sceneToParent(event.getSceneX(), event.getSceneY());
                double dragX = mousePointInParent.getX() - drawPane.getCursorPoint().getX();
                double newPositionX = drawPane.getInitX() + dragX;
                double oldWidth = drawPane.getPrefWidth();
                double addWidth = drawPane.getTranslateX() - newPositionX;
                drawPane.setPrefWidth(drawPane.getPrefWidth() + addWidth);
                if (drawPane.getPrefWidth() != oldWidth) {
                    drawPane.setTranslateX(newPositionX);
                }
            }
        });
    }

    public AbstractSymbol getDrawPane() {
        return drawPane;
    }

    public void setDrawPane(AbstractSymbol drawPane) {
        this.drawPane = drawPane;
    }

    public Circle[] getCircles() {
        return circles;
    }

    public void setCircles(Circle[] circles) {
        this.circles = circles;
    }

    public Line[] getLines() {
        return lines;
    }

    public void setLines(Line[] lines) {
        this.lines = lines;
    }
}
