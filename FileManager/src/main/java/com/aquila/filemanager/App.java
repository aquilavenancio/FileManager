package com.aquila.filemanager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


/**
 * JavaFX App
 */
public class App extends Application {

    private List<String> uniqueLines = new ArrayList<>();
    
    @Override
    public void start(Stage stage) {
        var javaVersion = SystemInfo.javaVersion();
        var javafxVersion = SystemInfo.javafxVersion();
        
        // Cria uma janela para selecionar o arquivo properties
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecionar arquivo properties");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Arquivos Properties", "*.properties"),
            new FileChooser.ExtensionFilter("Todos os arquivos", "*.*"));
        File file = fileChooser.showOpenDialog(stage);
        
        if (file != null) {
            // Lê as linhas do arquivo properties
            Properties properties = new Properties();
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                properties.load(reader);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            
            // Lê as linhas do arquivo properties e adiciona as linhas únicas ao objeto HashSet
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (!uniqueLines.contains(line)) {
                        uniqueLines.add(line);
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            // Cria um campo de texto para exibir as linhas do arquivo, pintando as linhas duplicadas de vermelho
            TextArea textArea = new TextArea();
            for (String line : uniqueLines) {
                Text text = new Text(line + "\n");
                if (lineHasDuplicates(line)) {
                    text.setFill(Color.RED);
                }
                textArea.appendText(text.getText());
            }
            textArea.setEditable(false);

            // Cria uma caixa de texto para exibir as linhas do arquivo
            TextArea textAreaOriginalFile = new TextArea();
            textAreaOriginalFile.setEditable(false);
            textAreaOriginalFile.setText(properties.toString());

            // Cria uma cena com a caixa de texto e a exibe na janela principal
            VBox root = new VBox();
            root.setPadding(new Insets(10));
            root.setAlignment(Pos.CENTER);
            root.getChildren().addAll(new Label("Conteúdo do arquivo: " + file.getName()), textAreaOriginalFile);
            root.getChildren().addAll(new Label("Texto duplicados"), textArea);
            var scene = new Scene(root, 640, 480);

            stage.setScene(scene);
            stage.show();

        }
        
    }
    
    private boolean lineHasDuplicates(String line) {
        int count = 0;
        for (String s : uniqueLines) {
            if (s.equals(line)) {
                count++;
            }
        }
        return count > 1;
    }

    public static void main(String[] args) {
        launch();
    }

}