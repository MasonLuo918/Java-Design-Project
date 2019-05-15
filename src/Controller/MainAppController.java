package Controller;

import Main.MainApp;
import Test.Main;

public class MainAppController extends Controller {
    private LeftPaneController leftPaneController;

    private RightPaneController rightPaneController;

    private MainAppController mainAppController;

    private MenuBarController menuBarController;

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
        menuBarController = new MenuBarController();
        menuBarController.setMainApp(mainApp);
        menuBarController.init();
        leftPaneController.init();
        rightPaneController.init();
    }

    public LeftPaneController getLeftPaneController() {
        return leftPaneController;
    }

    public void setLeftPaneController(LeftPaneController leftPaneController) {
        this.leftPaneController = leftPaneController;
    }

    public RightPaneController getRightPaneController() {
        return rightPaneController;
    }

    public void setRightPaneController(RightPaneController rightPaneController) {
        this.rightPaneController = rightPaneController;
    }

    public MainAppController getMainAppController() {
        return mainAppController;
    }

    public void setMainAppController(MainAppController mainAppController) {
        this.mainAppController = mainAppController;
    }

    public MainApp getMainApp() {
        return mainApp;
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public MenuBarController getMenuBarController() {
        return menuBarController;
    }

    public void setMenuBarController(MenuBarController menuBarController) {
        this.menuBarController = menuBarController;
    }
}
