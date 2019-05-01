package FileMenu;

import javafx.scene.layout.Pane;

import java.io.*;

public class Reader {
    public String read(File file) throws IOException {
        FileReader fileReader = new FileReader(file);
        BufferedReader reader = new BufferedReader(fileReader);
        StringBuffer json = new StringBuffer();
        String string;
        while((string = reader.readLine()) != null){
            json.append(string);
        }
        reader.close();
        return json.toString();
    }
}
