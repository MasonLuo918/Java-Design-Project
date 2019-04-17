package Manager;


import Main.MainApp;
import MathUtil.MathUtil;
import Symbol.Line.Connect;
import Symbol.GlobalConfig;
import Symbol.Line.AbstractLine;
import Symbol.MShape;
import Symbol.Symbol.AbstractSymbol;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;


/**
 * 如果被选中，就添加入这个类中
 * <p>
 * 私有化构造函数，不允许新建类
 */
public class SymbolManage {

    /*
     *  只能获取这个对象，不能新建对象
     */
    private static SymbolManage manage = new SymbolManage();

    /**
     * 设置标志位，判断是否在操作框拖动
     * 如果是在操作框拖动，就不进行symbol的
     * 拖拽事件
     */
    private boolean isOperationDrag = false;

    private Selected selectedShape = new Selected();

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
        for (MShape mShape : selectedShape.getSelectedShape()) {
            mainApp.getRightPane().getChildren().remove(mShape);
        }
        selectedShape.removeAll();
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
        AbstractSymbol nowSymbol = null;
        int circleIndex = 0;
        double distance = GlobalConfig.MAX_NUMBER;
        Point2D pointInParent;
        for(AbstractSymbol symbol: connectSymbol){
            for(int i = 0; i < symbol.getConnectCircleFrame().getConnectCircle().length; i++){
                pointInParent = symbol.localToParent(symbol.getConnectCircleFrame().getConnectCircle()[i].getCenterX(), symbol.getConnectCircleFrame().getConnectCircle()[i].getCenterY());
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

    public Selected getSelectedShape() {
        return selectedShape;
    }

    public void setSelectedShape(Selected selectedShape) {
        this.selectedShape = selectedShape;
    }
}
