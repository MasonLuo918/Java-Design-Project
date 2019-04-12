package Controller;

import Main.MainApp;
import Manager.SymbolManage;
import Symbol.*;
import Symbol.Line.StraightLine;
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
        leftPane.setFitToWidth(true);
        VBox vbox = new VBox(40);
        vbox.setPadding(new Insets(30, 5, 30, 5));
        vbox.setFillWidth(false);
        vbox.setAlignment(Pos.CENTER);
        ArcRectangle arcRectangle = new ArcRectangle();
        ConnetSymbol connetSymbol = new ConnetSymbol();
        Diamond diamond = new Diamond();
        IOFrame ioFrame = new IOFrame();
        NoteSymbol noteSymbol = new NoteSymbol();
        Rectangle rectangle = new Rectangle();

        /**
         * 形状
         */
        arcRectangle.setOnMouseClicked(new leftPaneEvent());
        connetSymbol.setOnMouseClicked(new leftPaneEvent());
        diamond.setOnMouseClicked(new leftPaneEvent());
        ioFrame.setOnMouseClicked(new leftPaneEvent());
        noteSymbol.setOnMouseClicked(new leftPaneEvent());
        rectangle.setOnMouseClicked(new leftPaneEvent());
        vbox.getChildren().addAll(arcRectangle, rectangle, diamond, ioFrame, connetSymbol, noteSymbol);

        StraightLine straightLine = new StraightLine(vbox);

        /**
         * 添加线条的事件
         */
        straightLine.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getClickCount() == 1){
                    SymbolManage.getManage().cancelSelected();
                    StraightLine newLine = new StraightLine(getMainApp().getRightPane());
                    getMainApp().getRightPane().getChildren().remove(newLine);
                    SymbolManage.getManage().setLeftPaneSelected(newLine);
                }

                if(event.getClickCount() == 2){
                    StraightLine line = new StraightLine(getMainApp().getRightPane());
                    SymbolManage.getManage().cancelSelected();
                }
            }
        });
        leftPane.setContent(vbox);
    }

    /**
     * Symbol 类的事件
     */
    class leftPaneEvent implements EventHandler<MouseEvent> {

        @Override
        public void handle(MouseEvent event) {

            MShape mShape;
            String className = event.getSource().getClass().getName();
            try{
                Class<?> clz = Class.forName(className);
                mShape = (MShape)clz.newInstance();
                if(event.getClickCount() == 1){
                    SymbolManage.getManage().cancelSelected();
                    SymbolManage.getManage().setLeftPaneSelected(mShape);
                }
                if(event.getClickCount() == 2){
                    addToRightPane(mShape);
                    SymbolManage.getManage().cancelSelected();
                }
            }catch (Exception e){}
        }
    }

    public void addToRightPane(MShape mshape){
        Pane rightPane = mainApp.getRightPane();
        rightPane.getChildren().add((Node)mshape);
        if(!mshape.isLine()){
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
}