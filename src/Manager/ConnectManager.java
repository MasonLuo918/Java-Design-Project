package Manager;

import Symbol.MShape;
import Symbol.Symbol.AbstractSymbol;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ConnectManager {

    private ObservableList<AbstractSymbol> connectSymbol = FXCollections.observableArrayList();

    public void addConnectSymbol(AbstractSymbol symbol){
        if(!connectSymbol.contains(symbol)){
            connectSymbol.add(symbol);
            symbol.showConnectCircle();
        }
    }

    public void removeConnectSymbol(AbstractSymbol symbol){
        if(connectSymbol.contains(symbol)){
            connectSymbol.remove(symbol);
            symbol.hideConnectCircle();
        }
    }

    public void removeAllConnectSymbol(){
        for(AbstractSymbol symbol:connectSymbol){
            symbol.hideConnectCircle();
        }
        connectSymbol.removeAll();
    }

    public ObservableList<AbstractSymbol> getConnectSymbol() {
        return connectSymbol;
    }

    public void setConnectSymbol(ObservableList<AbstractSymbol> connectSymbol) {
        this.connectSymbol = connectSymbol;
    }
}
