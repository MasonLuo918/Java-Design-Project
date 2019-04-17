package Manager;


import Main.MainApp;
import MathUtil.MathUtil;
import Symbol.Connect;
import Symbol.GlobalConfig;
import Symbol.Line.AbstractLine;
import Symbol.MShape;
import Symbol.Symbol.AbstractSymbol;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import sun.applet.resources.MsgAppletViewer;

import java.util.ArrayList;
import java.util.List;


/**
 * 如果被选中，就添加入这个类中
 * <p>
 * 私有化构造函数，不允许新建类
 */
public class SymbolManage {

    private static SymbolManage manage = new SymbolManage();

    private boolean isOperationDrag = false;

    private ObservableList<MShape> selectedShape = FXCollections.observableArrayList();

    private ObservableList<AbstractSymbol> connectSymbol = FXCollections.observableArrayList();

    private MainApp mainApp;

    /**
     * 当在左边面板单击的时候，新建一个对应的symbol，
     * 添加至leftPaneSelected，当在rightPane单击
     * 时，添加至rightPane
     */
    private MShape leftPaneSelected;

    /**
     * 私有化该类，不能新建新的类，只能通过SymbolManage.getManage()获取对象
     */
    private SymbolManage() {
    }

    public static SymbolManage getManage() {
        return manage;
    }

    public ObservableList<MShape> getSelectedShape() {
        return selectedShape;
    }

    /**
     * 当ctrl没有按下的时候
     * 就调用这个函数将选中的shape加入selectedShape
     *
     * @param shape
     */
    public void add(MShape shape) {
        if (getSelectedShape().contains(shape)) {
            return;
        }
        for (MShape mShape : getSelectedShape()) {
            mShape.hideOperationFrame();
            mShape.hideTextArea();
        }
        getSelectedShape().clear();
        getSelectedShape().add(shape);
        shape.showOperationFrame();
    }

    /**
     * 当ctrl按下的时候
     * 调用这个函数，将选中的shape加入selecredShape
     *
     * @param shape
     */
    public void addMore(MShape shape) {
        if (getSelectedShape().contains(shape)) {
            return;
        }
        getSelectedShape().add(shape);
        shape.showOperationFrame();
    }

    /**
     * 取消全部选中
     */
    public void removeAll() {
        for (MShape mShape : getSelectedShape()) {
            mShape.hideOperationFrame();
            mShape.hideTextArea();
        }
        getSelectedShape().clear();
    }

    /**
     * 取消单个选中
     *
     * @param mShape
     */
    public void remove(MShape mShape) {
        mShape.hideOperationFrame();
        mShape.hideTextArea();
        getSelectedShape().remove(mShape);
    }

    /**
     * 设置标志位，判断是否在操作框拖动
     * 如果是在操作框拖动，就不进行symbol的
     * 拖拽事件
     *
     * @return
     */
    public boolean isOperationDrag() {
        return isOperationDrag;
    }

    public void setOperationDrag(boolean operationDrag) {
        isOperationDrag = operationDrag;
    }

    public void setLeftPaneSelected(MShape mShape) {
        leftPaneSelected = mShape;
    }

    /**
     * 取消左边面板选中，当添加完毕
     * 直接设置为null
     */
    public void cancelSelected() {
        leftPaneSelected = null;
    }

    public MShape getLeftPaneSelected() {
        return leftPaneSelected;
    }

    public MainApp getMainApp() {
        return mainApp;
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * 删除事件，删除右边面板选中的图形
     */
    public void deleteSelectedShapeFromRightPane() {
        for (MShape mShape : getSelectedShape()) {
            mainApp.getRightPane().getChildren().remove(mShape);
        }
        removeAll();
    }

    public void addConnectSymbol(AbstractSymbol symbol){
        if(!connectSymbol.contains(symbol)){
            connectSymbol.add(symbol);
            symbol.showConnectCircle();
        }
    }

    public void removeConnectSymbol(AbstractSymbol symbol){
        if(connectSymbol.contains(symbol)){
            connectSymbol.remove(symbol);
            symbol.hideConnectCircle();
        }
    }

    public void removeAllConnectSymbol(){
        for(AbstractSymbol symbol:connectSymbol){
            symbol.hideConnectCircle();
        }
        connectSymbol.removeAll();
    }

    /**
     *
     * @param x - 需要在rightPane上的坐标
     * @param y - 需要在rightPane上的坐标
     */
    public void detectLineEnter(double x, double y){
        for(Node node:mainApp.getRightPane().getChildren()){
            if(node instanceof AbstractSymbol){
                AbstractSymbol symbol = (AbstractSymbol) node;
                Bounds bounds = symbol.getBoundsInParent();
                if(bounds.getMinX() - GlobalConfig.ENTER_WIDTH < x && bounds.getMinY() - GlobalConfig.ENTER_WIDTH < y
                        && bounds.getMaxX() + GlobalConfig.ENTER_WIDTH > x && bounds.getMaxY() + GlobalConfig.ENTER_WIDTH > y){
                    addConnectSymbol(symbol);
                }else{
                    removeConnectSymbol(symbol);
                }
            }
        }
    }

    public void connect(AbstractLine line, Connect connect){
//        AbstractSymbol nowSymbol = connectSymbol.get(0);
//        int circleIndex = 0;
//        Circle nowCircle;
//        Point2D pointInParent = nowSymbol.localToParent(nowSymbol.getConnectCircle()[circleIndex].getCenterX(),
//                nowSymbol.getConnectCircle()[circleIndex].getCenterY());
//        double distance = MathUtil.distance(connect.getLineX(), connect.getLineY(), pointInParent.getX(), pointInParent.getY());
//        for(int i = 0; i < nowSymbol.getConnectCircle().length; i++){
//            pointInParent = nowSymbol.localToParent(nowSymbol.getConnectCircle()[i].getCenterX(),
//                    nowSymbol.getConnectCircle()[i].getCenterY());
//
//            double length = MathUtil.distance(connect.getLineX(), connect.getLineY(), pointInParent.getX(), pointInParent.getY());
//            if(length < distance){
//                circleIndex = i;
//            }
//        }

        AbstractSymbol nowSymbol = null;
        int circleIndex = 0;
        double distance = GlobalConfig.MAX_NUMBER;
        Point2D pointInParent;
        for(AbstractSymbol symbol: connectSymbol){
            for(int i = 0; i < symbol.getConnectCircle().length; i++){
                pointInParent = symbol.localToParent(symbol.getConnectCircle()[i].getCenterX(), symbol.getConnectCircle()[i].getCenterY());
                double length = MathUtil.distance(connect.getLineX(), connect.getLineY(), pointInParent.getX(), pointInParent.getY());
                if(length < distance){
                    nowSymbol = symbol;
                    distance = length;
                    circleIndex = i;
                }
            }
        }
        if(!connectSymbol.isEmpty()){
            connect.setSymbol(nowSymbol);
            connect.setCircleIndex(circleIndex);
            connect.connect();
        }
    }
}
