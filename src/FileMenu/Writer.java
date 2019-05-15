package FileMenu;

import Main.MainApp;
import Symbol.Line.AbstractLine;
import Symbol.MShape;
import Symbol.Symbol.AbstractSymbol;
import Test.Main;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.io.*;

public class Writer{
    public void save(File file,Pane rightPane) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode symbolArray = mapper.createArrayNode();
        ArrayNode lineArray = mapper.createArrayNode();
        ArrayNode connectArray = mapper.createArrayNode();
        for(Node node:rightPane.getChildren()){
            if(!(node instanceof MShape)){
                continue;
            }
            MShape mShape = (MShape)node;
            if(mShape.isLine()){
                AbstractLine line = (AbstractLine)mShape;
                lineArray.addPOJO(line.getLineBean());

                if(line.getStartConnect().isBind()){
                    connectArray.addPOJO(line.getStartBean());
                }
                if(line.getEndConnect().isBind()){
                    connectArray.addPOJO(line.getEndBean());
                }
            }else{
                AbstractSymbol symbol = (AbstractSymbol)mShape;
                symbolArray.addPOJO(symbol.getSymbolBean());
            }
        }
        ObjectNode root = mapper.createObjectNode();
        root.set("symbols",symbolArray);
        root.set("lines",lineArray);
        root.set("connects",connectArray);
        String string = mapper.writeValueAsString(root);
//        System.out.println(string);
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(string.getBytes());
        fileOutputStream.close();
    }
}
