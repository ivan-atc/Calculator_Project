package com.gui_calculator;
import com.sun.jna.Native;
import com.sun.jna.platform.win32.BaseTSD;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser;

//Origin: https://stackoverflow.com/questions/28538234/sending-a-keyboard-input-with-java-jna-and-sendinput
//Modified by Atche Ivan on 27.06.2026.
public class windowsFocusManager {


    public static void interactWithWindows(String buttonText){
        
        // Map button text to Windows Virtual-Key codes
        int vkCode = getVirtualKeyCode(buttonText);
        
        if(vkCode == -1) {
            System.out.println("Unknown key: " + buttonText);
            return;
        }

        System.out.println("Sending key: " + buttonText + " (VK code: " + vkCode + ")");

        //Loop All Windows
        User32.INSTANCE.EnumWindows(( hWnd, data ) -> {
                
                //Variable Storing the Name of The Variable
                char[] name = new char[512];
                User32.INSTANCE.GetWindowText(hWnd, name, name.length);
        
                //Find Window with the C++ code CMD
                if(Native.toString(name).startsWith("C:"))
                {
                    //Bring this Window to the Front
                    User32.INSTANCE.SetForegroundWindow(hWnd);
            
                        //Prepare the Input Reference
                        WinUser.INPUT input = new WinUser.INPUT( );
                        input.type = new WinDef.DWORD(WinUser.INPUT.INPUT_KEYBOARD );
                        input.input.setType("ki");
                        input.input.ki.wScan = new WinDef.WORD(0);
                        input.input.ki.time = new WinDef.DWORD(0);
                        input.input.ki.dwExtraInfo = new BaseTSD.ULONG_PTR(0);

                        //Press Button
                        input.input.ki.wVk = new WinDef.WORD(vkCode);
                        input.input.ki.dwFlags = new WinDef.DWORD(0); // Key down

                        //Send Command
                        User32.INSTANCE.SendInput( new WinDef.DWORD( 1 ), ( WinUser.INPUT[] ) input.toArray( 1 ), input.size() );


                        //Release Button
                        input.input.ki.wVk = new WinDef.WORD(vkCode);
                        input.input.ki.dwFlags = new WinDef.DWORD(2); // Key up

                        //Send Command
                        User32.INSTANCE.SendInput( new WinDef.DWORD( 1 ), ( WinUser.INPUT[] ) input.toArray( 1 ), input.size() );
                    
                     return false; //Found

                }
                return true; //Search
        }, null);
    }

    
    //Note: Setting a case that does not correspond to is corresponding hexadecimal would not
    //make the digital word transmitted to the focussed window
    private static int getVirtualKeyCode(String buttonText) {
        switch(buttonText) {
            case "0": return 0x30;
            case "1": return 0x31;
            case "2": return 0x32;
            case "3": return 0x33;
            case "4": return 0x34;
            case "5": return 0x35;
            case "6": return 0x36;
            case "7": return 0x37;
            case "8": return 0x38;
            case "9": return 0x39;
            case "+": return 0x6B; // +
            case "-": return 0xBD; // -
            case "*": return 0x6A; // *
            case "/": return 0x6F; // Divided
            case "CLR": return 0x08; // Backspace
            case "ENTR": return 0x0D; //Enter
            default: return -1;
        }
    }

}


