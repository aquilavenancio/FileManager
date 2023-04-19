package com.aquila.filemanager;

import com.aquila.filemanager.service.ManagerFilePropertie;
import java.io.File;
import java.util.List;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application implements EventHandler{

    private TextArea fileOriginal;
    private TextArea fileExposes;
    private FileChooser fileChooser;
    private Button selectedButtonFile;
    private Button exit;
    
    ManagerFilePropertie manager ;
    private void inicialize(Stage stage) {
        setFileChooser(new FileChooser());
        setFileOriginal(new TextArea());
        setFileExposes(new TextArea());
        setSelectedButtonFile(new Button("Select File"));
        setExit(new Button("Sair"));
        
        
        manager = new ManagerFilePropertie();
        
        getExit().setOnAction(e -> {
            System.exit(0);
        });
        getSelectedButtonFile().setOnAction(this);
        //Inicializando o chooser
        getFileChooser().setTitle("Selecionar arquivo properties");
        getFileChooser().getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Arquivos Properties", "*.properties"),
            new FileChooser.ExtensionFilter("Todos os arquivos", "*.*"));
        
        
        VBox root = new VBox();
        var hBoxButton = new HBox(this.getSelectedButtonFile(), this.getExit());
        hBoxButton.setPadding(new Insets(5, 5 ,5,5));
        root.setPadding(new Insets(10));
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(new Label("Selecione arquivos para retirar duplicados"),hBoxButton
                ,this.getFileOriginal(), new Label("Arquivo sem duplicacoes "), this.getFileExposes());
        var scene = new Scene(root, 1024, 720);

        stage.setTitle("| 1.0V | - File Manager");
        stage.setScene(scene);
        stage.show();

    }

    @Override
    public void start(Stage stage) {
        this.inicialize(stage);
        
    }

    public static void main(String[] args) {
        launch();
    }
    
    public FileChooser getFileChooser(){
        return this.fileChooser;
    }
    
    public void setFileChooser(FileChooser fileChooser){
        this.fileChooser = fileChooser;
    }
    
    public TextArea getFileOriginal(){
        return this.fileOriginal;
    }
    
    public void setFileOriginal(TextArea fileOriginal){
        this.fileOriginal = fileOriginal;
    }
    
    public void setSelectedButtonFile(Button selectedButton){
        this.selectedButtonFile = selectedButton;
    }
    
    public Button getSelectedButtonFile(){
        return this.selectedButtonFile;
    }
    
    public TextArea getFileExposes(){
        return this.fileExposes;
    }
    
    public void setFileExposes(TextArea fileExposes){
        this.fileExposes = fileExposes;
    }

    public Button getExit() {
        return exit;
    }

    public void setExit(Button exit) {
        this.exit = exit;
    }
    
    

    @Override
    public void handle(Event t) {
       Stage stage = new Stage();
       File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            List<String> lines = manager.lineListFile(file);
            lines.stream().forEach(line-> {
                Text text = new Text(line + "\n");
                System.out.print(text.getText());
                System.out.print(line);
                getFileOriginal().appendText(text.getText());
            
            });
            
            List<String> linedList = manager.linedListFile(file);
            linedList.stream().forEach(line -> {
                Text text = new Text(line + "\n");
                System.out.print(text.getText());
                System.out.print(line);
                getFileExposes().appendText(text.getText());
            });
        }
    }
}
