package Symbol.Line;

import Symbol.GlobalConfig;
import Symbol.Symbol.AbstractSymbol;
import javafx.beans.property.SimpleDoubleProperty;


/**
 *  该类对应着线条的一个点，symbol的一个连接圆
 *  调用connect()方法绑定连接圆和点
 *  调用unConnect()方法删除连接圆和点
 */
public class Connect {

    private int circleIndex;

    private AbstractSymbol symbol;

    private AbstractLine line;

    private SimpleDoubleProperty lineX;

    private boolean isStartPoint = true;private SimpleDoubleProperty lineY;

    /**
     * 标志位,判断是否已经用connect进行绑定
     */
    private boolean isBind = false;

    public Connect(AbstractLine line) {
        this.line = line;
    }

    public AbstractSymbol getSymbol() {
        return symbol;
    }


    /**
     * 使用前确保symbol,lineX,lineY,circleIndex已经设置
     */
    public void connect() {
        lineX.bind(symbol.getConnectCircleFrame().getConnectCircleCoorOfParent().get(2 * circleIndex));
        lineY.bind(symbol.getConnectCircleFrame().getConnectCircleCoorOfParent().get(2 * circleIndex + 1));
        isBind = true;
    }

    public void unConnect() {
        lineX.unbind();
        lineY.unbind();
        isBind = false;
    }

    public void setSymbol(AbstractSymbol symbol) {
        this.symbol = symbol;
    }

    public AbstractLine getLine() {
        return line;
    }

    public void setLine(AbstractLine line) {
        this.line = line;
    }


    public double getLineX() {
        return lineX.get();
    }

    public SimpleDoubleProperty lineXProperty() {
        return lineX;
    }

    public void setLineX(double lineX) {
        this.lineX.set(lineX);
    }

    public void setLineXProperty(SimpleDoubleProperty lineX) {
        this.lineX = lineX;
    }

    public double getLineY() {
        return lineY.get();
    }

    public SimpleDoubleProperty lineYProperty() {
        return lineY;
    }

    public void setLineY(double lineY) {
        this.lineY.set(lineY);
    }

    public void setLineYProperty(SimpleDoubleProperty lineY) {
        this.lineY = lineY;
    }

    public int getCircleIndex() {
        return circleIndex;
    }

    public void setCircleIndex(int circleIndex) {
        this.circleIndex = circleIndex;
    }

    public boolean isBind() {
        return isBind;
    }

    public void setBind(boolean bind) {
        isBind = bind;
    }

    public boolean isStartPoint() {
        return isStartPoint;
    }

    public void setStartPoint(boolean startPoint) {
        isStartPoint = startPoint;
    }
}
