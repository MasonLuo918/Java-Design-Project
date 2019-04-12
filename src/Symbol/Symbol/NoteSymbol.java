package Symbol.Symbol;

import Symbol.GlobalConfig;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.shape.*;

public class NoteSymbol extends AbstractSymbol{
    @Override
    public void initMyShape() {
        class Shape extends Path{
            private SimpleDoubleProperty width = new SimpleDoubleProperty();

            private SimpleDoubleProperty height = new SimpleDoubleProperty();

            private final double WID = 20;

            public Shape() {
                this.width.bind(NoteSymbol.this.prefWidthProperty());
                this.height.bind(NoteSymbol.this.prefHeightProperty());
                setFill(GlobalConfig.SYMBOL_FILL_COLOR);
                setStroke(GlobalConfig.SYMBOL_STROKE_COLOR);
                draw();
                setListener();
            }
            public void setListener(){
                widthProperty().addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                        draw();
                    }
                });

                heightProperty().addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                        draw();
                    }
                });
            }

            public void draw(){
                getElements().clear();
                MoveTo moveTo = new MoveTo();
                moveTo.setX(0.0);
                moveTo.setY(0);

                HLineTo hLineTo = new HLineTo();
                hLineTo.setX(getWidth());

                VLineTo vLineTo = new VLineTo();
                vLineTo.setY(getHeight());


                CubicCurveTo curveTo = new CubicCurveTo();
                curveTo.setX(0.0);
                curveTo.setY(getHeight() - 10);
                curveTo.setControlX1((3.0 / 4) * getWidth());
                curveTo.setControlY1(getHeight() - WID);
                curveTo.setControlX2((1.0 / 4) * getWidth());
                curveTo.setControlY2(getHeight() + WID);


                VLineTo vLineTo2 = new VLineTo();
                vLineTo2.setY(0);

                getElements().add(moveTo);
                getElements().add(hLineTo);
                getElements().add(vLineTo);
                getElements().add(curveTo);
                getElements().add(vLineTo2);

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
        setMyShape(new Shape());
    }
}
