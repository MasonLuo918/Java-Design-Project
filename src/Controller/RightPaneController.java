package Controller;

import Main.MainApp;
import Manager.SymbolManage;
import Symbol.*;
import Symbol.Line.AbstractLine;
import Symbol.Line.DoubleBrokenLine;
import Symbol.Line.LineType;
import Symbol.Symbol.AbstractSymbol;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.IOException;


public class RightPaneController extends Controller {
    private static RightPaneController rightPaneController = new RightPaneController();

    private Pane rightPane;

    private MainApp mainApp;

    private AbstractSymbol[] copySymbol;

    private Point2D point;

    private RightPaneController() {

    }

    public static RightPaneController getRightPaneController() {
        return rightPaneController;
    }

    @Override
    public void init() {
        /**
         * 初始化右边绘图pane的点击事件，当鼠标按下是，判断是否是在图形里面按下，
         * 如果是，就选中该图形，如果不是，取消所有选中
         */
        rightPane.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Point2D point = new Point2D(event.getSceneX(), event.getSceneY());
                RightPaneController.this.point = point;
                boolean inMyShape = false; //标志位，判断是否在shape里面点击
                try {
                    for (Node node : rightPane.getChildren()) {
                        /*
                         *  如果点击是在Symbol里的时候，选中
                         */
                        if (!(node instanceof MShape)) {
                            continue;
                        }
                        MShape mShape = (MShape) node;
                        if (mShape.containsPointInScene(event.getSceneX(), event.getSceneY())) {
                            mShape.select(event);
                            if (event.getClickCount() == 2) {
                                mShape.showTextArea();
                            }
                            inMyShape = true;
                        }
                        /*
                         * 将鼠标的坐标系转换成在rightPane的坐标系，并记录下当前鼠标的位置，以便拖拽使用
                         */
                        mShape.setCursorPoint(mShape.sceneToParent(event.getSceneX(), event.getSceneY()));
                        /*
                         * 如果不是一条线，记录下初始symbol左上角的坐标
                         */
                        if (!mShape.isLine()) {
                            mShape.setInitX(node.getTranslateX());
                            mShape.setInitY(node.getTranslateY());
                            /**
                             * 如果是一条线的话，记录线的各个点的坐标
                             */
                        } else {
                            AbstractLine line = (AbstractLine) mShape;
                            line.setLastStartX(line.getStartX());
                            line.setLastStartY(line.getStartY());
                            line.setLastEndX(line.getEndX());
                            line.setLastEndY(line.getEndY());
                            line.setLastMiddleX(line.getMiddleX());
                            line.setLastMiddleY(line.getMiddleY());
                        }
                    }
                } catch (Exception e) {
                }
                /**
                 * 如果不是在shape里面点击，则判断两个事情
                 * 1、是否在左边面板里面选择了一个图形,如果是，则添加选中的图形到左边的面板
                 * 2、如果1不成立，取消所有选中
                 */
                if (!inMyShape) {
                    SymbolManage.getManage().getSelectedShape().removeAll(); //取消所有的选中
                    Point2D point2D = rightPane.sceneToLocal(event.getSceneX(), event.getSceneY()); //转换坐标系
                    try {
                        /**
                         * 如果1成立，则执行
                         */
                        if (SymbolManage.getManage().getLeftPaneSelected() != null) {
                            MShape mShape = SymbolManage.getManage().getLeftPaneSelected();
                            rightPane.getChildren().add((Node) mShape);
                            if (!mShape.isLine()) {
                                /**
                                 * 新建一个symbol，并根据鼠标点击的位置设置位置
                                 */
                                AbstractSymbol symbol = (AbstractSymbol) mShape;
                                symbol.setLayoutX(point2D.getX() - symbol.getPrefWidth() / 2);
                                symbol.setLayoutY(point2D.getY() - symbol.getPrefHeight() / 2);
                            } else {
                                /**
                                 * 点击右边面板时，新建线条,并且根据鼠标点击的位置设置位置
                                 */
                                AbstractLine line = (AbstractLine) mShape;
                                int lineType = line.getLineType();
                                switch (lineType) {
                                    case LineType.STRAIGHT_LINE:
                                        line.setStartX(point2D.getX() - line.getLineLength() / 2);
                                        line.setStartY(point2D.getY());
                                        line.setEndX(line.getStartX() + line.getLineLength());
                                        line.setEndY(line.getStartY());
                                        break;
                                    case LineType.BROKEN_LINE:
                                        line.setStartX(point2D.getX() - line.getLineLength() / 2);
                                        line.setStartY(point2D.getY());
                                        line.setEndX(line.getStartX() + line.getLineLength());
                                        line.setEndY(line.getStartY() + line.getLineLength());
                                        break;
                                    case LineType.DOUBLE_BROKEN_LINE:
                                        line.setStartX(point2D.getX() - line.getLineLength() / 2);
                                        line.setStartY(point2D.getY());
                                        line.setEndX(point2D.getX() - line.getLineLength() / 2);
                                        line.setEndY(point2D.getY() + line.getLineLength());
                                        line.setMiddleX(line.getStartX() + line.getLineLength());
                                        line.setMiddleY((line.getStartY() + line.getEndY()) / 2);
                                        break;
                                }
                            }

                            /**
                             * 添加完毕，取消左边面板的选中
                             */
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
                if (SymbolManage.getManage().isOperationDrag()) {
                    return;
                }
                Point2D eventPointInScene = rightPane.sceneToLocal(event.getSceneX(), event.getSceneY());
                for (MShape myShape : SymbolManage.getManage().getSelectedShape().getSelectedShape()) {
                    double dragX = eventPointInScene.getX() - myShape.getCursorPoint().getX();
                    double dragY = eventPointInScene.getY() - myShape.getCursorPoint().getY();
                    if (!myShape.isLine()) {
                        AbstractSymbol symbol = (AbstractSymbol) myShape;
                        double newPositionX = symbol.getInitX() + dragX;
                        double newPositionY = symbol.getInitY() + dragY;
                        if (!(symbol.getLayoutX() + newPositionX < 0 || symbol.getLayoutX() + newPositionX + symbol.getPrefWidth() > rightPane.getWidth())) {
                            symbol.setTranslateX(newPositionX);
                        }

                        if (!(symbol.getLayoutY() + newPositionY < 0 || symbol.getLayoutY() + newPositionY + symbol.getPrefHeight() > rightPane.getHeight())) {
                            symbol.setTranslateY(newPositionY);
                        }
                    } else {
                        AbstractLine line = (AbstractLine) myShape;
                        double newStartX = line.getLastStartX() + dragX;
                        double newStartY = line.getLastStartY() + dragY;
                        double newEndX = line.getLastEndX() + dragX;
                        double newEndY = line.getLastEndY() + dragY;
                        double newMiddleX = line.getLastMiddleX() + dragX;
                        double newMiddleY = line.getLastMiddleY() + dragY;
                        /**
                         * 如果没有绑定，则可以设置新位置
                         */
                        if (!line.getStartConnect().isBind() && !line.getEndConnect().isBind()) {
                            line.setStartX(newStartX);
                            line.setStartY(newStartY);
                            line.setEndX(newEndX);
                            line.setEndY(newEndY);
                            if (line.getLineType() == LineType.DOUBLE_BROKEN_LINE || line.getLineType() == LineType.DOUBLE_BROKEN_LINE) {
                                line.setMiddleX(newMiddleX);
                                line.setMiddleY(newMiddleY);
                            }
                        }else{
                            if(line instanceof DoubleBrokenLine){
                                line.setMiddleX(newMiddleX);
                                line.setMiddleY(newMiddleY);
                            }
                        }
                    }
                }
            }
        });

        mainApp.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.isControlDown()) {
                    System.out.println("**");
                    if (event.getCode().getName().equals(KeyCode.C.getName())) {
                        RightPaneController.this.copySymbol = SymbolManage.getManage().copySelectedShapeFromRightPane();//返回复制的符号数组
                    }
                    if (event.getCode().getName().equals(KeyCode.V.getName())) {
                        double y = RightPaneController.this.point.getY();
                        double x = RightPaneController.this.point.getX();
                        for (AbstractSymbol each : RightPaneController.this.copySymbol) {

                            RightPaneController.this.rightPane.getChildren().add(each);
                            RightPaneController.this.point = RightPaneController.this.copySymbol[0].sceneToParent(x, y);

                            each.setLayoutX(RightPaneController.this.point.getX());
                            each.setLayoutY(RightPaneController.this.point.getY());
                            each.setTranslateX(0);
                            each.setTranslateY(0);
                        }
                        //更新数组
                        AbstractSymbol[] copySymbol = new AbstractSymbol[RightPaneController.this.copySymbol.length];
                        int i = 0;
                        for (AbstractSymbol each : RightPaneController.this.copySymbol) {

                            copySymbol[i++] = Generator.getSymbol(each.getSymbolBean());
                        }
                        RightPaneController.this.copySymbol = copySymbol;
                    }
                } else if (event.getCode().getName().equals(KeyCode.BACK_SPACE.getName())) {
                    SymbolManage.getManage().deleteSelectedShapeFromRightPane();
                }
            }
        });
    }


    public void generate(String json) throws IOException {
        rightPane.getChildren().clear();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(json);
        JsonNode symbols = jsonNode.path("symbols");
        for (JsonNode node : symbols) {
            AbstractSymbol symbol = Generator.generateSymbol(node.toString());
            rightPane.getChildren().add(symbol);
        }
        JsonNode lines = jsonNode.path("lines");
        for (JsonNode node : lines) {
            AbstractLine line = Generator.generateLine(node.toString(), rightPane);
        }
        JsonNode connects = jsonNode.path("connects");
        for (JsonNode node : connects) {
            Generator.generateConncet(node.toString(), rightPane);
        }

    }

    public Pane getRightPane() {
        return rightPane;
    }

    public void setRightPane(Pane rightPane) {
        this.rightPane = rightPane;
    }

    public MainApp getMainApp() {
        return mainApp;
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
}
