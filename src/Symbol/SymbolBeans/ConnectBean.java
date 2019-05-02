package Symbol.SymbolBeans;

public class ConnectBean {
    private int circleIndex;

    private String symbolUUID;

    private String lineUUID;

    private double lineX;

    private double lineY;

    private boolean isBind;

    private boolean isStartPoint;

    public int getCircleIndex() {
        return circleIndex;
    }

    public void setCircleIndex(int circleIndex) {
        this.circleIndex = circleIndex;
    }

    public String getSymbolUUID() {
        return symbolUUID;
    }

    public void setSymbolUUID(String symbolUUID) {
        this.symbolUUID = symbolUUID;
    }

    public String getLineUUID() {
        return lineUUID;
    }

    public void setLineUUID(String lineUUID) {
        this.lineUUID = lineUUID;
    }

    public double getLineX() {
        return lineX;
    }

    public void setLineX(double lineX) {
        this.lineX = lineX;
    }

    public double getLineY() {
        return lineY;
    }

    public void setLineY(double lineY) {
        this.lineY = lineY;
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
