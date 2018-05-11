package ballGame;

import javax.swing.*;

public class Main {
    public static void main(String[] args){
        JFrame Window = new JFrame();
        Window.setBounds(100,100,800,500);
        GameContent level = new GameContent();
        Window.setTitle("Ball Game");
        Window.setResizable(false);
        Window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Window.setVisible(true);


        Window.add(level);


    }
}
