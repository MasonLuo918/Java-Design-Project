package Manager;


import Main.MainApp;
import MathUtil.MathUtil;
import Symbol.MShape;
import Symbol.Symbol.AbstractSymbol;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

}
