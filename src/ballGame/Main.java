package ballGame;

import javax.swing.*;

//
public class Main {
    public static void main(String[] args){
        JFrame Window = new JFrame(); //  Window is defined
        Window.setBounds(100, 100, 800, 1000);//setting window parameters
        GameContent level = new GameContent();//create instance of gamecontent
        Window.setTitle("Ball Game");
        Window.setResizable(false);
        Window.setLocationRelativeTo(null);
        Window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //cease operations on window close
        Window.setVisible(true);


        Window.add(level); //add the gamecontent to the window


    }
}
