package Symbol;

import Symbol.Line.*;
import Symbol.Symbol.*;
import Symbol.SymbolBeans.ConnectBean;
import Symbol.SymbolBeans.LineBean;
import Symbol.SymbolBeans.SymbolBean;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

import javax.sound.sampled.Line;
import java.io.IOException;

public class Generator {
    public static AbstractSymbol generateSymbol(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        AbstractSymbol symbol = null;
        try {
            SymbolBean symbolBean = objectMapper.readValue(json, SymbolBean.class);
            symbol = getSymbol(symbolBean);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return symbol;
    }

    public static AbstractSymbol getSymbol(SymbolBean bean) {
        int type = bean.getSymbolType();
        AbstractSymbol symbol = null;
        switch (type) {
            case SymbolType.RECTANGLE:
                symbol = new Rectangle();
                break;
            case SymbolType.ARC_RECTANGLE:
                symbol = new ArcRectangle();
                break;
            case SymbolType.CONNECT_SYMBOL:
                symbol = new ConnetSymbol();
                break;
            case SymbolType.DIAMOND:
                symbol = new Diamond();
                break;
            case SymbolType.IO_FRAME:
                symbol = new IOFrame();
                break;
            case SymbolType.NOTE_SYMBOL:
                symbol = new NoteSymbol();
                break;
            default:
                break;
        }
        symbol.setUserBean(bean);
        return symbol;
    }

    public static AbstractLine generateLine(String json, Pane drawPane) {
        ObjectMapper mapper = new ObjectMapper();
        AbstractLine line = null;
        try {
            LineBean lineBean = mapper.readValue(json, LineBean.class);
            line = getLine(lineBean, drawPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return line;
    }

    public static AbstractLine getLine(LineBean lineBean, Pane drawPane) {
        int type = lineBean.getLineType();
        AbstractLine line = null;
        switch (type) {
            case LineType.STRAIGHT_LINE:
                line = new StraightLine(drawPane);
                break;
            case LineType.BROKEN_LINE:
                line = new BrokenLine(drawPane);
                break;
            case LineType.DOUBLE_BROKEN_LINE:
                line = new DoubleBrokenLine(drawPane);
            default:
                break;
        }
        line.setUserBean(lineBean);
        return line;
    }

    public static void generateConncet(String json, Pane drawPane) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            ConnectBean connectBean = objectMapper.readValue(json, ConnectBean.class);
            AbstractLine line = null;
            AbstractSymbol symbol = null;
            for (Node node : drawPane.getChildren()) {
                if(!(node instanceof MShape)){
                    continue;
                }
                MShape mShape = (MShape) node;
                if (mShape.getUuid().equals(connectBean.getLineUUID())) {
                    line = (AbstractLine) mShape;
                }
                if (mShape.getUuid().equals(connectBean.getSymbolUUID())) {
                    symbol = (AbstractSymbol) mShape;
                }
            }
            if (line != null && symbol != null) {
                if (connectBean.isStartPoint()) {
                    line.getStartConnect().setCircleIndex(connectBean.getCircleIndex());
                    line.getStartConnect().setSymbol(symbol);
                    line.getStartConnect().connect();
                } else {
                    line.getEndConnect().setCircleIndex(connectBean.getCircleIndex());
                    line.getEndConnect().setSymbol(symbol);
                    line.getEndConnect().connect();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
