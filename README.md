# Calculator_Project
## Contents
This folder repository contains a software project, being a virtual calculator, that is aiming to perform simple arithmetic tasks (i.e addition, subtraction, multiplication, division).

The project contains three main parts:
- A basic input output system (coded in C++)
The virtual BIOS was coded with a set of function calls that mimic real calculation functionalities. In particular, it uses the integrated CLI of the compiler to perform any calculation.
For interacting with the GUI, it generates a temporary file holding the user calculation.

- A GUI program (coded in Java), responsible for the display of the application
The GUI role was extended to not only display the calculator GUI, but also read the file generating by the virtual BIOS. The reading operation was set to allow the calculator to display the instantaneous calculation in the input field of the virtual GUI.

- A Java program intended to make the program acting as an On-Screen Keyboard to the C++ generated application
This was required as the keyboard of the GUI should be used to interact with the BIOS CLI to mimic the operation of a calculator. Hence, it was developed as a complete separate class, with an instance created in the GUI code. It allowed the cursor to be directed to the CLI whilst the GUI keyboards were pressed.
  
## Project Operation
During the execution of the entire project, the C++ should display a ON-screen terminal, with visible requests for the user to enter some data. This follows the execution of an ON-screen GUI that represents the calculator. Both part are intended to work nearly simultaneously.

The user should to press enter (the ENTR touch), at each user prompt. The following routine is intended to be followed by the user:
- User enters a first number and press enters for validation
- User enters a operand and press enters for validation
- User enters a second number and press enters for validation

Following these steps must display the user calculation prompt on the calculator text field.

## Installtion Required
The only installation requires for this project are a modified version of the GCC compiler (originally from CodeBlocks), the official JDK interpreter for Java alongside with JavaFX modules.

### Please Note:
1) The MinGW compiler must be displaced within the workspace folder (i.e in the "\.vscode" folder).
<img width="2862" height="1682" alt="image" src="https://github.com/user-attachments/assets/d3d8e21f-927d-4fcb-bfb9-5a1169c8ca1b" />

Below is the repository of the modified version of the GCC compiler attached in a ZIP:
https://www.icloud.com/iclouddrive/031MlcVB423hHol48JCXcvvqw#MinGW

For Usage, there is some modifications that must be made in the launch.json & the task.json files:
  ### In launch.json:
  In the "programm", "cwd", "value", "miDebuggerPath" sections, remplace the current paths with the current directory in which all the downloaded similar files are located 
  <img width="2140" height="1238" alt="image" src="https://github.com/user-attachments/assets/15ffdfb6-39c4-4f83-a9b0-35d62731e262" />


  ### In task.json:
  In both "command", "cwd" and "PATH" sections, remplace also the current paths with the current directory in which all the dowloaded similar files are located 
  <img width="2132" height="1156" alt="image" src="https://github.com/user-attachments/assets/240e07e6-7251-42b3-adc9-e1b0aada016d" />
  <img width="2146" height="1230" alt="image" src="https://github.com/user-attachments/assets/fdc38f95-54c0-4c08-9eed-c81dab9a3fa9" />


    

2) The official Java interpreter does not come natively with the required JavaFx libraries.
In case, below is the link of the JavaFx libraries, to be included in the ("lib") folder of your openJDK folder configured in VSCode:
https://gluonhq.com/products/javafx/

## How to Use It
For best compability with the "\.vscode" folder, the Visual Code Studio text editor from Microsoft should be used.
For best usage during execution, the CMD must be utilised as a split window with the GUI to understand the result of the calculator.
<img width="2880" height="1698" alt="image" src="https://github.com/user-attachments/assets/fca34e56-5072-43ab-b24d-880e176746d7" />


