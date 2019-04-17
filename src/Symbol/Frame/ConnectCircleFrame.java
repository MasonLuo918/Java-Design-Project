package Symbol.Frame;

import Symbol.GlobalConfig;
import Symbol.Symbol.AbstractSymbol;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Point2D;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.List;

public class ConnectCircleFrame {
    /*
     * 当连接线接近的时候,就会显示这几个circle，用以连接
     */
    private Circle[] connectCircle = new Circle[4];

    /*
     * 各个连接圆对应的Parent上的坐标,用来绑定线段
     */
    private List<SimpleDoubleProperty> connectCircleCoorOfParent = new ArrayList<>();

    /*
     * 连接框显示的面板
     */
    private AbstractSymbol drawPane;


    public ConnectCircleFrame(AbstractSymbol drawPane){
        this.drawPane = drawPane;
        for(int i = 0; i < 8; i++){
            getConnectCircleCoorOfParent().add(new SimpleDoubleProperty());
        }
        for (int i = 0; i < connectCircle.length; i++) {
            connectCircle[i] = new Circle(GlobalConfig.CIRCLE_RADIUS);   // 初始化连接框
            connectCircle[i].setFill(GlobalConfig.CONNECT_CIRCLE_COLOR); //设置连接框颜色
        }
    }

    /**
     * 显示连接框
     */
    public void showConnectCircle() {
        hideConnectCircle();
        drawConnectCircle();
        for (Circle circle : connectCircle) {
            drawPane.getChildren().add(circle);
        }
    }

    /**
     * 隐藏连接框
     */
    public void hideConnectCircle() {
        for (Circle circle : connectCircle) {
            drawPane.getChildren().remove(circle);
        }
    }

    /**
     * 画出连接框
     */
    public void drawConnectCircle() {

        connectCircle[0].setCenterX(drawPane.getPrefWidth() / 2);
        connectCircle[0].setCenterY(0);

        connectCircle[1].setCenterX(drawPane.getPrefWidth());
        connectCircle[1].setCenterY(drawPane.getPrefHeight() / 2);

        connectCircle[2].setCenterX(drawPane.getPrefWidth() / 2);
        connectCircle[2].setCenterY(drawPane.getPrefHeight());

        connectCircle[3].setCenterX(0);
        connectCircle[3].setCenterY(drawPane.getPrefHeight() / 2);
    }

    /**
     * 更新连接框在drawPane的父亲上面的坐标
     */
    public void updateConnectCircleCoorInParent() {
        for (int i = 0; i < 4; i++) {
            double x = connectCircle[i].getCenterX();
            double y = connectCircle[i].getCenterY();
            Point2D point = drawPane.localToParent(x, y);
            getConnectCircleCoorOfParent().get(2 * i).set(point.getX());
            getConnectCircleCoorOfParent().get(2 * i + 1).set(point.getY());
        }
    }


    // Getter and Setter

    public Circle[] getConnectCircle() {
        return connectCircle;
    }

    public void setConnectCircle(Circle[] connectCircle) {
        this.connectCircle = connectCircle;
    }

    public List<SimpleDoubleProperty> getConnectCircleCoorOfParent() {
        return connectCircleCoorOfParent;
    }

    public void setConnectCircleCoorOfParent(List<SimpleDoubleProperty> connectCircleCoorOfParent) {
        this.connectCircleCoorOfParent = connectCircleCoorOfParent;
    }
}
