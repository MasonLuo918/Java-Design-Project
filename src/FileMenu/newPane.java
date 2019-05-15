package FileMenu;

import Manager.SymbolManage;
import javafx.scene.layout.Pane;

public class newPane {
    public static void createNewPane(Pane drawPane){
        drawPane.getChildren().clear();
        SymbolManage.getManage().getSelectedShape().removeAll();
    }
}
