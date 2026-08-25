package com.gui_calculator;


import java.io.IOException;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene; //Import Label Class
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField; //Import Text Field Class
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

//Call the JavaFX Application Class. It needs to be extended as it creates a secondary window
public class Main extends Application{

    public static void main (String args[]){ //Main Method
        launch(args); //Launch the application
    }

    @Override
    public void start(Stage primaryStage){ //At the start, the name of the stage is overitten, by primary stage

        int NO_TOUCHES = 16;
        String []characters = new String[NO_TOUCHES];
        windowsFocusManager focus = new windowsFocusManager();
        final String osComputation = null;
        File fileDirectory  = new File("tempFile/dataFileOriginal.txt");

        //Create Constructors
        primaryStage.setTitle("Prototype Calculator");
        TextField basicInput = new TextField(); //Create an Instance of the TextField Class
        Label basicTextInput = new Label("Input");
        Button[] allButtons = new Button[16];
        for(int button = 0 ; button < 16; button ++){
            allButtons[button] = new Button(Integer.toString(button));
        }

        VBox keypad = new VBox(10);
        HBox firstRow = new HBox(10);  //Create HBox Constructors that are automatically on the VBox.
        HBox secondRow = new HBox(10);
        HBox thirdRow = new HBox(10);
        HBox fourthRow = new HBox(10);

        ///Create Proprieties & Set Them
        VBox root = new VBox(15);
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(basicInput, basicTextInput, keypad);

        //Set Position of Rows
        firstRow.setAlignment(Pos.CENTER);
        secondRow.setAlignment(Pos.CENTER);
        thirdRow.setAlignment(Pos.CENTER);
        fourthRow.setAlignment(Pos.CENTER);

        Scene sc = new Scene(root, 300, 400);
        installWindow(primaryStage, sc);
        //Create Label On It Proprieties
        basicTextInput.setFont(new Font("Arial", 15));
        basicTextInput.setAlignment(Pos.TOP_RIGHT);
        basicTextInput.setTextFill(Color.GREY);
        basicInput.setPrefHeight(80);
        basicInput.setPrefColumnCount(5);

        //Add All Buttons in Each Rows
        firstRow.getChildren().addAll(allButtons[7], allButtons[8], allButtons[9], allButtons[10]);
        secondRow.getChildren().addAll(allButtons[4], allButtons[5], allButtons[6], allButtons[11]);
        thirdRow.getChildren().addAll(allButtons[1], allButtons[2], allButtons[3], allButtons[12]);
        fourthRow.getChildren().addAll(allButtons[0], allButtons[15], allButtons[14], allButtons[13]);

        //Add Each Rows
        keypad.getChildren().addAll(firstRow, secondRow, thirdRow, fourthRow);
        

        //Install the Keyboard of All Buttons and the Text Field
        installWindow(primaryStage, sc);
        installKeyboard(allButtons, basicInput, characters,NO_TOUCHES, focus);
        //Set the Scene
        
        primaryStage.show();
        primaryStage.setScene(sc);

        new Thread(){ 
        //Handle The Connection From Kernel to GUI tasks, including:
        //- The Computation Files Transfer to the GUI code
        //- The Reading and Display of the File Content into the TextField (I/O Field)
        //Note: The display capabilities is paused by the wait() method that does not execute the rest of the code until it is prompt to
        public void run(){handleOS(fileDirectory, osComputation, basicInput);}
        }.start();

        //Wait Command Function that aims to avoid that the notification for interruption of the file handling executes 
        //before the function itself
        new Thread(){
            public void run(){waitCommand();}
        }.start();

        new Thread(){
        //Function that interrupts the thread dedicated to search the file to execute the rest of the code aiming to read the file
            public void run(){reloadGUI();}
        }.start();
    
}

    public void installWindow(Stage primaryStage, Scene scene){ 
        primaryStage.setAlwaysOnTop(true);
    }

    public void installKeyboard(Button []keyboard, TextField field, String []listOfCharacters, int nb, windowsFocusManager focus)
    {
          
        //Create Button Properties
        for(int button = 0 ; button < nb; button ++){
            final int thisButton = button;
            keyboard[button].setText(Integer.toString(button));
            keyboard[button].setPrefSize(50,50);
            keyboard[button].setAlignment(Pos.BASELINE_CENTER);
            

            keyboard[button].setFocusTraversable(false);
            field.setFocusTraversable(false);
            
            if(button == 10){
                keyboard[button].setText("+");
            }
            if(button == 11){
                keyboard[button].setText("-");
            }
            if(button == 12){
                keyboard[button].setText("*");
            }
            if(button == 13){
                keyboard[button].setText("/");
            }
            if(button == 14){
                keyboard[button].setText("ENTR");
            }
            if(button == 15){
                keyboard[button].setText("CLR");
            }
            
            keyboard[button].setOnAction(new EventHandler<ActionEvent>() {
            //setOnAction method that handles the interface between the terminal and the caluculator
            //Handle the following functions:
            //Printing the Calculator Prompt in the Text Field & the CMD
            public void handle(ActionEvent keyboardInput) {

                    if(!"=".equals(keyboard[thisButton].getText())){

                        // Run on background thread to prevent UI freezing
                        new Thread(() -> {
                            focus.interactWithWindows(keyboard[thisButton].getText());
                        }).start();
                    }

                }
            });
        }
    }

    
    //Display CMD-Terminal Results on the Calculator Solution
    public void handleOS(File targetDirectory, String characters, TextField field){
        //Synchronise the block for avoiding bad synchronisation among the threads (handleOS + reloadGUI)
    for(int noBins = 1; noBins <= 3; noBins++){
        synchronized (this) {
            //Installation of a wait method to pause the current thread (i.e - the calculation is prompt in the terminal)
            //before the application starts being in a position of reading
            for (int attempt = 0; attempt < 500 && !targetDirectory.exists(); attempt++) {
                try {
                    this.wait(1000);
                    System.out.println("Directory does not exist");
                } catch (InterruptedException exception) {
                    Thread.currentThread().interrupt();
                    System.out.println(exception.toString());
                }
            }

        //Wait Command that waits that the kernel file to overwrite the previous existing file to avoid race conditions
        new Thread(){
            public void run(){waitCommand();}
        }.start();
            
            //Indicates that the thread is finished if 50 attemps were made OR the file was created
            System.out.println("\nThreat Finished "+ noBins+ " time(s)"); 
            try {
                if (targetDirectory.exists()) {
                    //Generic Array That Reads The OS's file contents 
                    BufferedReader reader =  new BufferedReader(new FileReader(targetDirectory));
                    characters = reader.readLine();
                    reader.close(); 
                }
            } catch (IOException error) {
                System.out.println(error.toString());
            }

            //Set the File String into the Text Field at the end of the user prompt
            field.setText(characters);
            targetDirectory.delete();
         }

       }
       System.out.println("Thread Completly Finished");
    }


    public void reloadGUI() {
        //Synchronise the block for avoiding bad synchronisation among the threads (handleOS + reloadGUI)
        synchronized (this) {
            //Notify the wait method on the handleOS block to continue the rest of the code (dedicated to reload the GUI).
            this.notifyAll(); 
        }
    }


public void waitCommand(){
    synchronized(this){
        try {
            this.wait(100);
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
        }
    }
}
}

