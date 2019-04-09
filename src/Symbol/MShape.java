package Symbol;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;

public interface MShape {
    /**
     * 画出操作框
     */
    public void showOperationFrame();

    /**
     * 隐藏操作框
     */
    public void hideOperationFrame();

    /**
     * 选中该Symbol
     */
    public void select(MouseEvent event);

    /**
     * 初始化Symbol的事件
     */
    public void initEvent();

    /**
     * 初始化操作框的事件
     */
    public void initOperationFrameEvent();

    /**
     * 将Scene的坐标系转换成Symbol的父亲坐标系
     */
    public Point2D sceneToParent(double x, double y);

    /**
     * 判断鼠标是否进入这个pane,在scene
     */
    public boolean containsPointInScene(double x, double y);

    /**
     * 获取在scene的bounds
     */
    public Bounds getBoundsInScene();
}
