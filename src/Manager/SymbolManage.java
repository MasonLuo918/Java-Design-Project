package Manager;


import Symbol.MShape;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/**
 *   如果被选中，就添加入这个类中
 *
 *   私有化构造函数，不允许新建类
 */
public class SymbolManage {

    private static SymbolManage manage = new SymbolManage();

    private ObservableList<MShape> selectedShape = FXCollections.observableArrayList();


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
}
