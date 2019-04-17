package Manager;

import Symbol.MShape;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Selected {

    /*
     *  shape设置为选中状态时，添加入这里，selectedShape里面所有的shape都处于选中状态,
     *  操作框显示
     */
    private ObservableList<MShape> selectedShape = FXCollections.observableArrayList();

    /**
     * 添加一个shape进去selectedShape,使用这个方法添加的时候,
     * selectedShape里面只能有一个shape,不能有多个
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
     * 添加一个shape进入selectedShape里面,与add()方法不同的是,
     * 添加进去一个shape并不会删除其他shape,按下ctrl的时候使用这
     * 个方法添加
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
     * 取消单个选中
     * @param mShape
     */
    public void remove(MShape mShape) {
        mShape.hideOperationFrame();
        mShape.hideTextArea();  //同时如果有显示textArea的时候，取消显示
        getSelectedShape().remove(mShape);
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

    // Getter and Setter
    public ObservableList<MShape> getSelectedShape() {
        return selectedShape;
    }

    public void setSelectedShape(ObservableList<MShape> selectedShape) {
        this.selectedShape = selectedShape;
    }
}
