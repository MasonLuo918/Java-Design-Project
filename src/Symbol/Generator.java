package Symbol;

import Symbol.Line.AbstractLine;
import Symbol.Line.BrokenLine;
import Symbol.Line.LineType;
import Symbol.Line.StraightLine;
import Symbol.Symbol.*;
import Symbol.SymbolBeans.LineBean;
import Symbol.SymbolBeans.SymbolBean;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    private static AbstractSymbol getSymbol(SymbolBean bean) {
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
            line = getLine(lineBean,drawPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return line;
    }

    private static AbstractLine getLine(LineBean lineBean,Pane drawPane) {
        int type = lineBean.getLineType();
        AbstractLine line = null;
        switch (type) {
            case LineType.STRAIGHT_LINE:
                line = new StraightLine(drawPane);
                break;
            case LineType.BROKEN_LINE:
                line = new BrokenLine(drawPane);
                break;
            default:
                break;
        }
        line.setUserBean(lineBean);
        return line;
    }
}
