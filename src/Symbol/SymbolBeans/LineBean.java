package Symbol.SymbolBeans;

public class LineBean {
    private int lineType;

    private String text;

    private String uuid;

    private double startX;

    private double startY;

    private double middleX;

    private double middleY;

    private double endX;

    private double endY;

    public int getLineType() {
        return lineType;
    }

    public void setLineType(int lineType) {
        this.lineType = lineType;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public double getStartX() {
        return startX;
    }

    public void setStartX(double startX) {
        this.startX = startX;
    }

    public double getStartY() {
        return startY;
    }

    public void setStartY(double startY) {
        this.startY = startY;
    }

    public double getEndX() {
        return endX;
    }

    public void setEndX(double endX) {
        this.endX = endX;
    }

    public double getEndY() {
        return endY;
    }

    public void setEndY(double endY) {
        this.endY = endY;
    }

    public double getMiddleX() {
        return middleX;
    }

    public void setMiddleX(double middleX) {
        this.middleX = middleX;
    }

    public double getMiddleY() {
        return middleY;
    }

    public void setMiddleY(double middleY) {
        this.middleY = middleY;
    }
}
