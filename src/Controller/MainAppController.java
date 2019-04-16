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


    /**
     * 初始化左右面板控制器
     */
    @Override
    public void init(){
        leftPaneController = LeftPaneController.getLeftPaneController();
        rightPaneController = rightPaneController.getRightPaneController();
        leftPaneController.setLeftPane(mainApp.getLeftPane());
        leftPaneController.setMainApp(mainApp); //设置mainApp，以便获取数据
        rightPaneController.setRightPane(mainApp.getRightPane());
        rightPaneController.setMainApp(mainApp);
        leftPaneController.init();
        rightPaneController.init();
    }
}
