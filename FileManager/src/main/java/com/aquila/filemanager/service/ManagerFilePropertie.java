/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.aquila.filemanager.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import javafx.scene.control.TextArea;

/**
 *
 * @author aquila
 */
public class ManagerFilePropertie {
    
    public List<String> lineListFile(File file){
        List<String> linesFound = new ArrayList<>();
        if(!Objects.isNull(file)){
            Properties properties = new Properties();
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                properties.load(reader);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            linesFound.add(properties.toString() + "\n");
        }
        
        return linesFound;
    }
    
    public List<String> linedListFile(File fileFound){
        List<String> linesFound = new ArrayList<>();
        if(!Objects.isNull(fileFound)){
            Properties properties = new Properties();
            try (BufferedReader reader = new BufferedReader(new FileReader(fileFound))) {
                properties.load(reader);
                for (String key : properties.stringPropertyNames()) {
                    String value = properties.getProperty(key);
                    System.out.println(key + " = " + value);
                    linesFound.add(key + " = " +value);
                 }
                
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }
        
        return linesFound;
    }
}
