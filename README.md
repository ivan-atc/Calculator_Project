# Calculator_Project
## Contents
This folder repository contains a software project, being a virtual calculator, that is aiming to perform simple arithmetic tasks (i.e addition, substraction, multiplication, division).

The project contains two main parts:
- A basic input output system (coded in C++), generating a file (representing an operating system)
- A GUI programm (coded in Java), responsible for the display of the application
  
## Project Operation
During the execution of the entire project, the C++ should display a ON-screen terminal, with visible requests for the user to enter some data. This follows the execution of an ON-screen GUI that represents the calculator. Both part are intended to work nearly simultaneously.

The user should to press enter (the ENTR touch), at each user prompt. The following routine is intended to be followed by the user:
- User enters a first number and press enters for validation
- User enters a operand and press enters for validation
- User enters a second number and press enters for validation

Following these steps must display the user calculation prompt on the calculator text field.

## Installtion Required
The only installation requires for this project are a modified version of the GCC compiler (originally from CodeBlocks), the official JDK interpreter for Java alongside with JavaFX modules.

### Note:
1) The MinGW compiler must be displaced within the workspace folder (i.e in the "\.vscode" folder).
<img width="796" height="446" alt="image" src="https://github.com/user-attachments/assets/123f9c87-bf17-4853-be4e-3bf771e9f1cd" />

Below is the repository of the modified version of the GCC compiler attached in a ZIP:
https://www.icloud.com/iclouddrive/031MlcVB423hHol48JCXcvvqw#MinGW

    For Usage, there is some modifications that must be made in the launch.json & the task.json files:
    #### In launch.json:
    In the "programm", "cwd", "value", "miDebuggerPath" sections, remplace the current paths with the current directory in which all the dowloaded similar files are located 

    #### In task.json:
    In both "command", "cwd" and "PATH" sections, remplace also the current paths with the current directory in which all the dowloaded similar files are located 
    

2) The official Java interpreter does not come natively with the required JavaFx libraries.
In case, below is the link of the JavaFx libraries, to be included in the ("lib") folder of the openJDK folder:
https://gluonhq.com/products/javafx/
<img width="706" height="314" alt="image" src="https://github.com/user-attachments/assets/d2b0be50-f39c-4989-aa49-d53c65b97754" />

## How to Use It
For best compability with the "\.vscode" folder, the Visual Code Studio text editor from Microsoft should be used.
For best usage during execution, the CMD must be utilised as a split window with the GUI to understand the result of the calculator.
<img width="2874" height="1696" alt="image" src="https://github.com/user-attachments/assets/a94a52ec-d2d4-40ef-b113-349555ce35bf" />

