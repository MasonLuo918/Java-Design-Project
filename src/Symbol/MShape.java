package Symbol;

import com.fasterxml.jackson.core.JsonProcessingException;
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
     * 选中该Symbol,event检测是否按下ctrl
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

    public boolean isLine();

    public void drawOperationFrame();

    public void setInitX(double initX);

    public void setInitY(double initY);

    public double getInitX();

    public double getInitY();

    public void setCursorPoint(Point2D cursorPoint);

    public Point2D getCursorPoint();

    public void showTextArea() throws JsonProcessingException;

    public void hideTextArea();

    public void updateText();

    public void setTextListenerEvent();

    public String toJson() throws JsonProcessingException;
}
