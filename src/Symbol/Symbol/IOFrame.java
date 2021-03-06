package Symbol.Symbol;


import Symbol.GlobalConfig;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Bounds;
import javafx.scene.control.TextArea;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class IOFrame extends AbstractSymbol {

    @Override
    public void initMyShape() {
        class Shape extends Polygon {

            private double offset = GlobalConfig.IOFRAME_OFFSET;
            private SimpleDoubleProperty width = new SimpleDoubleProperty();

            private SimpleDoubleProperty height = new SimpleDoubleProperty();

            public Shape(){
                width.bind(IOFrame.this.widthProperty());
                height.bind(IOFrame.this.heightProperty());
                setFill(GlobalConfig.SYMBOL_FILL_COLOR);
                setStroke(GlobalConfig.SYMBOL_STROKE_COLOR);
                draw();
                setListener();
            }

            public void setListener(){
                widthProperty().addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                        getPoints().clear();
                        draw();
                    }
                });

                heightProperty().addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                        getPoints().clear();
                        draw();
                    }
                });
            }

            public void draw() {
                double x1 = offset / 2;
                double y1 = 0;
                double x2 = offset / 2 + getWidth();
                double y2 = 0;
                double x3 = getWidth() - offset / 2;
                double y3 = getHeight();
                double x4 = -offset / 2;
                double y4 = getHeight();
                getPoints().addAll(x1,y1,x2,y2,x3,y3,x4,y4);
            }
            public double getWidth() {
                return width.get();
            }

            public SimpleDoubleProperty widthProperty() {
                return width;
            }

            public void setWidth(double width) {
                this.width.set(width);
            }

            public double getHeight() {
                return height.get();
            }

            public SimpleDoubleProperty heightProperty() {
                return height;
            }

            public void setHeight(double height) {
                this.height.set(height);
            }
        }
        Shape shape = new Shape();
        setMyShape(shape);
    }

    @Override
    public void showTextArea(){
        TextArea textArea = getTextArea();
        textArea.setWrapText(true);
        textArea.setLayoutX(GlobalConfig.IOFRAME_OFFSET / 2);
        textArea.setLayoutY(0);
        textArea.setPrefWidth(getPrefWidth() - GlobalConfig.IOFRAME_OFFSET);
        textArea.setPrefHeight(getPrefHeight());
        if(!getChildren().contains(textArea)){
            getChildren().addAll(textArea);
        }
    }

    @Override
    public void updateText(){
        Text text = getText();
        text.setLayoutX(GlobalConfig.IOFRAME_OFFSET / 2);
        text.setLayoutY(0);
        text.setWrappingWidth(getPrefWidth() - GlobalConfig.IOFRAME_OFFSET);
        text.setTextAlignment(TextAlignment.CENTER);
        Bounds bounds = text.getBoundsInParent();
        double y = getPrefHeight() - bounds.getHeight();
        text.setY(y / 2 + getTextHeight());

    }


    @Override
    public void initSymbolType(){
        setSymbolType(SymbolType.IO_FRAME);
    }
}
