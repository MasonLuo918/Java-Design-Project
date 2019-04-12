package Controller;

import Main.MainApp;
import Test.Main;

public class MainAppController extends Controller {
    private LeftPaneController leftPaneController;

    private RightPaneController rightPaneController;

    private MainAppController mainAppController;

    private MainApp mainApp;

    public MainAppController(MainApp mainApp){
        mainAppController = this;
        this.mainApp = mainApp;
    }

    @Override
    public void init(){
        leftPaneController = LeftPaneController.getLeftPaneController();
        rightPaneController = rightPaneController.getRightPaneController();
        leftPaneController.setLeftPane(mainApp.getLeftPane());
        leftPaneController.setMainApp(mainApp);
        rightPaneController.setRightPane(mainApp.getRightPane());
        leftPaneController.init();
        rightPaneController.init();
    }
}
