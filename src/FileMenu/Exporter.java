package FileMenu;

import Manager.SymbolManage;
import com.sun.javafx.tk.TKStageListener;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.DialogPane;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Exporter {

    public static void export(File file, Pane drawPane){
        DialogPane dialogPane = new DialogPane();
        Stage stage = new Stage();
        stage.setScene(new Scene(dialogPane,90,50));
        SymbolManage symbolManage = SymbolManage.getManage();
        symbolManage.getSelectedShape().removeAll();
        WritableImage image = drawPane.snapshot(new SnapshotParameters(),null);
        try{
            System.out.println();
            String imageType = file.getName().substring(file.getName().lastIndexOf(".") + 1);
            ImageIO.write(SwingFXUtils.fromFXImage(image,null),imageType,file);
            dialogPane.setContentText("保存成功");
        }catch (IOException e){
            dialogPane.setContentText("保存失败");
            e.printStackTrace();
        }finally {
            stage.show();
        }
    }
}
