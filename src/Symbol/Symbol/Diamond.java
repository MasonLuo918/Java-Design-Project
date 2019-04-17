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

public class Diamond extends AbstractSymbol{
    @Override
    public void initMyShape() {
        class DiamondShape extends Polygon {

            private SimpleDoubleProperty width = new SimpleDoubleProperty();

            private SimpleDoubleProperty height = new SimpleDoubleProperty();

            public DiamondShape(){
                width.bind(Diamond.this.widthProperty());
                height.bind(Diamond.this.heightProperty());
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

            public void draw(){
                double x1 = getWidth() / 2;
                double y1 = 0;

                double x2 = getWidth();
                double y2 = getHeight() / 2;

                double x3 = getWidth() / 2;
                double y3 = getHeight();

                double x4 = 0;
                double y4 = getHeight() / 2;

                getPoints().addAll(x1, y1, x2, y2, x3, y3, x4, y4);
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

        DiamondShape diamond = new DiamondShape();
        setMyShape(diamond);
    }

    @Override
    public void showTextArea(){
        TextArea textArea = getTextArea();
        textArea.setWrapText(true);
        textArea.setLayoutX(getPrefWidth() / 4);
        textArea.setLayoutY(getPrefHeight() / 4);
        textArea.setPrefWidth(getPrefWidth() / 2);
        textArea.setPrefHeight(getPrefHeight() / 2);
        if(!getChildren().contains(textArea)){
            getChildren().addAll(textArea);
        }
    }

    @Override
    public void setTextListenerEvent(){
        getText().textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(getText().getBoundsInLocal().getHeight() > getTextArea().getHeight() - getTextHeight()){
                    getText().setText(oldValue);
                }
                updateText();
            }
        });
    }

    @Override
    public void updateText(){
        Text text = getText();
        text.setWrappingWidth(getPrefWidth() / 2);
        text.setTextAlignment(TextAlignment.CENTER);
        text.setLayoutX(getPrefWidth() / 4);
        text.setLayoutY(getPrefHeight() / 4);
        Bounds bounds = text.getBoundsInParent();
        double y = getPrefHeight() / 2 - bounds.getHeight();
        text.setY(y / 2 + getTextHeight());
    }
}
