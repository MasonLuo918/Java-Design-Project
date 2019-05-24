package Controller;

import Main.MainApp;
import Manager.SymbolManage;
import Symbol.*;
import Symbol.Line.*;
import Symbol.Symbol.*;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class LeftPaneController extends Controller {


    private static LeftPaneController leftPaneController = new LeftPaneController();

    private ScrollPane leftPane;

    private MainApp mainApp;

    private LeftPaneController() {
    }


    public static LeftPaneController getLeftPaneController() {
        return leftPaneController;
    }

    public ScrollPane getLeftPane() {

        return leftPane;
    }

    public void setLeftPane(ScrollPane leftPane) {
        this.leftPane = leftPane;
    }

    @Override
    public void init() {
        leftPane.setFitToWidth(true); //取消自动填充子节点
        VBox vbox = new VBox(40);
        vbox.setPadding(new Insets(30, 5, 30, 5));
        vbox.setFillWidth(false);
        vbox.setAlignment(Pos.CENTER);
        /**
         * 新建所有symbol
         */
        ArcRectangle arcRectangle = new ArcRectangle();
        ConnetSymbol connetSymbol = new ConnetSymbol();
        Diamond diamond = new Diamond();
        IOFrame ioFrame = new IOFrame();
        NoteSymbol noteSymbol = new NoteSymbol();
        Rectangle rectangle = new Rectangle();

        /**
         * 设置鼠标点击事件
         */
        arcRectangle.setOnMouseClicked(new leftPaneEvent());
        connetSymbol.setOnMouseClicked(new leftPaneEvent());
        diamond.setOnMouseClicked(new leftPaneEvent());
        ioFrame.setOnMouseClicked(new leftPaneEvent());
        noteSymbol.setOnMouseClicked(new leftPaneEvent());
        rectangle.setOnMouseClicked(new leftPaneEvent());
        vbox.getChildren().addAll(arcRectangle, rectangle, diamond, ioFrame, connetSymbol, noteSymbol);
        StraightLine straightLine = new StraightLine(vbox);
        BrokenLine brokenLine = new BrokenLine(vbox);
        DoubleBrokenLine doubleBrokenLine = new DoubleBrokenLine(vbox);
        doubleBrokenLine.setOnMouseClicked(new LineClickEvent());
        straightLine.setOnMouseClicked(new LineClickEvent());
        brokenLine.setOnMouseClicked(new LineClickEvent());
        leftPane.setContent(vbox);
    }


    /**
     * 添加symbol到rightPane
     *
     * @param mshape
     */
    public void addToRightPane(MShape mshape) {
        Pane rightPane = mainApp.getRightPane();
        rightPane.getChildren().add((Node) mshape);
        if (!mshape.isLine()) {
            AbstractSymbol symbol = (AbstractSymbol) mshape;
            symbol.setLayoutX(rightPane.getWidth() / 2 - symbol.getPrefWidth() / 2);
            symbol.setLayoutY(rightPane.getHeight() / 2 - symbol.getPrefHeight() / 2);
        }
    }

    public MainApp getMainApp() {
        return mainApp;
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * SymbolType 类的事件
     */
    class leftPaneEvent implements EventHandler<MouseEvent> {

        @Override
        public void handle(MouseEvent event) {

            MShape mShape;
            String className = event.getSource().getClass().getName();
            /**
             * 单击一次，根据反射机制新建symbol，添加为选中
             */
            try {
                Class<?> clz = Class.forName(className);
                mShape = (MShape) clz.newInstance();
                if (event.getClickCount() == 1) {
                    SymbolManage.getManage().cancelSelected();
                    SymbolManage.getManage().setLeftPaneSelected(mShape);
                }
                /**
                 * 单击两次，直接添加到rightPane中间
                 */
                if (event.getClickCount() == 2) {
                    addToRightPane(mShape);
                    SymbolManage.getManage().cancelSelected();
                }
            } catch (Exception e) {
            }
        }
    }

    class LineClickEvent implements EventHandler<MouseEvent> {

        @Override
        public void handle(MouseEvent event) {
            AbstractLine line = (AbstractLine) event.getSource();
            AbstractLine newLine = null;
            switch (line.getLineType()) {
                case LineType.STRAIGHT_LINE:
                    newLine = new StraightLine(LeftPaneController.this.getMainApp().getRightPane());
                    break;
                case LineType.BROKEN_LINE:
                    newLine = new BrokenLine(LeftPaneController.this.getMainApp().getRightPane());
                    break;
                case LineType.DOUBLE_BROKEN_LINE:
                    newLine = new DoubleBrokenLine(LeftPaneController.this.getMainApp().getRightPane());
                default:
                    break;
            }
            LeftPaneController.this.getMainApp().getRightPane().getChildren().remove(newLine);
            if (event.getClickCount() == 1) {
                SymbolManage.getManage().cancelSelected();
                getMainApp().getRightPane().getChildren().remove(newLine);
                SymbolManage.getManage().setLeftPaneSelected(newLine);
            }
            if (event.getClickCount() == 2) {
                SymbolManage.getManage().cancelSelected();
                LeftPaneController.this.getMainApp().getRightPane().getChildren().add(newLine);
            }
        }
    }
}
