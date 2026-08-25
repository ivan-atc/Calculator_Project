#include <iostream>
#include <fstream>
#include <filesystem>
#include <limits>

#define LIMIT_FILE_PATH 200

using namespace std;
namespace fs = std::filesystem;

///File Handler System class
class fileHandler
{
public:
    ofstream dataFile; //File Handling the I/O

    //Constructors & Destructor
    fileHandler();
    virtual ~fileHandler();
    fileHandler(const fileHandler &file);
    fileHandler &operator=(const fileHandler &file);
};


/// Calculator Prompt Handler Class
class calculatorPrompt
{
private:
    //Calculator GUI contents handled by the Kernel
    double firstNumber, secondNumber, result;
    char operand;
    int noBins;//Counter that counts the number of bins

public:
    //file Handler member class handled by the Kernel
    fileHandler cFile;


    //Constructors/Destructors/Assignment Operators
    calculatorPrompt(fileHandler &calculatorFile);
    ~calculatorPrompt();
    calculatorPrompt(const calculatorPrompt &prompt);
    calculatorPrompt &operator=(const calculatorPrompt &prompt);

    //Member Functions
    void resetCalculator();
    void getResults();

    void readMembers();
    void displaceFile();

    void setFirstNumber();
    void setSecondNumber();
    void setOperand();
    void processCalculation();

    void writeDouble(double value);
    void writeOperand(char operand);
    void writeEqual();

};

///Implementations
fileHandler::fileHandler()
{
    dataFile.open("dataFile.txt", ios::out);   //Constructor that opens the file temporarly
}

//Destructors, Copy Constructors, Assignment Operators
fileHandler::~fileHandler() {}
fileHandler::fileHandler(const fileHandler &file) {}
fileHandler &fileHandler::operator =(const fileHandler &file)
{
    if(this != &file)
    {}
    return (*this);
}

///IMPLEMENTATIONS
calculatorPrompt::calculatorPrompt(fileHandler &calculatorFile):cFile(calculatorFile), noBins(0)
//Constructor that automatically processes core functions when it is created
{

    //Prompt the user to enter the first number and write into the file
    resetCalculator();
    setFirstNumber();
    writeDouble(firstNumber);
    
    resetCalculator(); //Overwrite the file so that the calculator reads the first user prompt
    displaceFile();

    //Prompt the user to enter the operand and write into the file
    setOperand();
    writeOperand(operand);

    resetCalculator(); //Overwrite the file so that the calculator reads the seconf user prompt
    displaceFile();

    //Prompt the user to enter the second number and write into the file
    setSecondNumber();
    writeDouble(secondNumber);

    resetCalculator();//Overwrite the file so that the calculator reads the third user prompt
    displaceFile();

    processCalculation();
    getResults();

    writeEqual();
    
    resetCalculator(); //Overwrite the file so that the calculator reads the total calculation data
    displaceFile();

    cFile.dataFile.close();

}

//Destructors, Copy Constructors, Assignment Operators
calculatorPrompt::~calculatorPrompt() {}
calculatorPrompt::calculatorPrompt(const calculatorPrompt &prompt):cFile(prompt.cFile),
    firstNumber(prompt.firstNumber), secondNumber(prompt.secondNumber), operand(prompt.operand), result(prompt.result) {}

calculatorPrompt &calculatorPrompt::operator =(const calculatorPrompt &prompt)
{
    if(this != &prompt)
    {
        cFile = prompt.cFile,
        firstNumber = prompt.firstNumber, secondNumber = prompt.secondNumber, operand = prompt.operand, result = prompt.result;
    }

    return *this;
}

///Functions

void calculatorPrompt::getResults()
{
    cout<<"="<< result;
}

void calculatorPrompt::resetCalculator()
{
    std::filesystem::remove("gui_calculator_maven/tempFile/dataFileOriginal.txt");
}
//Set Functions
void calculatorPrompt::setFirstNumber()
{
    cout<<"Please Enter the First Number"<<endl;
    cin>>firstNumber;
    noBins++;
}

void calculatorPrompt::setOperand()
{
    while (true)
    {
        cout << "Please Enter the Operand (+, -, *, /)" << endl;
        if (cin >> operand &&
            (operand == '+' || operand == '-' || operand == '*' || operand == '/'))
        {
            break;
        }

        cout << "Invalid operand. Please try again." << endl;
        cin.clear();
        cin.ignore(numeric_limits<streamsize>::max(), '\n');
    }
    noBins++;
}
void calculatorPrompt::setSecondNumber()
{
    cout<<"Please Enter the Second Number"<<endl;
    cin>>secondNumber;
    noBins++;
}


//Function that handle the calculation processing depending on the user input
void calculatorPrompt::processCalculation()
{
    switch(operand)
    {
    case '+':
        result = firstNumber + secondNumber;
        break;
    case '-':
        result = firstNumber - secondNumber;
        break;
    case '*':
        result = firstNumber * secondNumber;
        break;
    case '/':
        result = firstNumber / secondNumber;
        break;
    }
}

//Function that Write on the Operating System File the Calculation Prompt of the User
//It Opens Temporarly the File for The Process
//The Package is To Be Send To The GUI code to display calculation in the calculator
void calculatorPrompt::writeEqual()
{
    cFile.dataFile.open("dataFile.txt", ios::out);
    if(cFile.dataFile.is_open())
    {
        cFile.dataFile<<firstNumber<<" "<<operand<<" "<<secondNumber<<"\t"<<"="<<" "<<result;
    }
    cFile.dataFile.close();
}


void calculatorPrompt::writeDouble(double value)
{
    cFile.dataFile.open("dataFile.txt", ios::out);
    if(cFile.dataFile.is_open())
    {
        if( noBins <= 2)
        {
            cFile.dataFile<<value;
        }
        else
        {
            cFile.dataFile<<firstNumber<<" "<<operand<<" "<<value;
        }
    }

    cFile.dataFile.close();

}

void calculatorPrompt::writeOperand(char operand)
{
    cFile.dataFile.open("dataFile.txt", ios::out);
    if(cFile.dataFile.is_open())
    {
        cFile.dataFile<<firstNumber<<" "<<operand;
    }
    cFile.dataFile.close();
}

void calculatorPrompt::displaceFile()
{
    try
    {
        std::filesystem::copy("dataFile.txt", "gui_calculator_maven/tempFile/dataFileOriginal.txt");
    }
    catch(std::filesystem::filesystem_error &e)
    {
        cout<<" Error in Moving File"<<endl;
    }
}

//Read in the Required Format the Operation in the CMD terminal
void calculatorPrompt::readMembers()
{
    cout<<"\n"<<firstNumber<<" "<<operand<<" "<<secondNumber<<" ="<<result;
}


int main(int argc, char *argv[])
{
    fileHandler cFile;
    calculatorPrompt kernel(cFile);
    return 0;
}

