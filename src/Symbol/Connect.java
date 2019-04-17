package Symbol;

import Symbol.Line.AbstractLine;
import Symbol.Symbol.AbstractSymbol;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.shape.Circle;

public class Connect {

    private int circleIndex;

    private AbstractSymbol symbol;

    private AbstractLine line;

    private SimpleDoubleProperty lineX;

    private SimpleDoubleProperty lineY;

    private boolean isBind = false;

    public Connect(AbstractLine line) {
        this.line = line;
    }

    public AbstractSymbol getSymbol() {
        return symbol;
    }

    public void connect() {
        lineX.bind(symbol.getConnectCircleCoorOfParent().get(2 * circleIndex));
        lineY.bind(symbol.getConnectCircleCoorOfParent().get(2 * circleIndex + 1));
        isBind = true;
    }

    public void unConnect() {
//        lineX.unbindBidirectional(symbol.getConnectCircleCoorOfParent().get(2 * circleIndex));
//        lineY.unbindBidirectional(symbol.getConnectCircleCoorOfParent().get(2 * circleIndex + 1));
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
}
