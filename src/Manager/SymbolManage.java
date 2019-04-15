package Manager;


import Main.MainApp;
import Symbol.MShape;
import Symbol.Symbol.AbstractSymbol;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.Pane;


/**
 *   如果被选中，就添加入这个类中
 *
 *   私有化构造函数，不允许新建类
 */
public class SymbolManage {

    private static SymbolManage manage = new SymbolManage();

    private boolean isOpertionDrag = false;
    
    private ObservableList<MShape> selectedShape = FXCollections.observableArrayList();

    private ObservableList<MShape> connectShape = FXCollections.observableArrayList();

    private MainApp mainApp;

    private MShape leftPaneSelectd;

    /**
     *  私有化该类，不能新建新的类，只能通过SymbolManage.getManage()获取对象
     */
    private SymbolManage(){
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
     * @param shape
     */
    public void add(MShape shape){
        if(getSelectedShape().contains(shape)){
            return;
        }
        for(MShape mShape:getSelectedShape()){
            mShape.hideOperationFrame();
        }
        getSelectedShape().clear();

        getSelectedShape().add(shape);
        shape.showOperationFrame();
    }

    /**
     * 当ctrl按下的时候
     * 调用这个函数，将选中的shape加入selecredShape
     * @param shape
     */
    public void addMore(MShape shape){
        if(getSelectedShape().contains(shape)){
            return;
        }
        getSelectedShape().add(shape);
        shape.showOperationFrame();
    }

    public void removeAll(){
        for(MShape mShape:getSelectedShape()){
            mShape.hideOperationFrame();
        }
        getSelectedShape().clear();
    }

    public boolean isOpertionDrag() {
        return isOpertionDrag;
    }

    public void setOpertionDrag(boolean opertionDrag) {
        isOpertionDrag = opertionDrag;
    }

    public void setLeftPaneSelected(MShape mShape){
        leftPaneSelectd = mShape;
    }

    /**
     * 取消左边面板选中，当添加完毕
     * 直接设置为null
     */
    public void cancelSelected(){
        leftPaneSelectd = null;
    }

    public MShape getLeftPaneSelected(){
        return leftPaneSelectd;
    }

    public MainApp getMainApp() {
        return mainApp;
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public void detect(double x, double y){
        Pane rightPane = mainApp.getRightPane();
        for(Node node:rightPane.getChildren()){
            MShape m = (MShape) node;
            if(m.isLine()){
                continue;
            }
            AbstractSymbol symbol = (AbstractSymbol) node;
            if(symbol.isLineInner(x,y)){
                symbol.showConnectCircle();
            }else{
                symbol.hideConnectCircle();
            }
        }
    }
}
