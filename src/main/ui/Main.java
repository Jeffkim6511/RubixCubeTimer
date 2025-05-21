package ui;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;

public class Main {
    //for console
//    public static void main(String[] args) {
//        try {
//            new RubixCubeApp();
//        } catch (FileNotFoundException e) {
//            System.out.println("An error has occurred");
//        }
//    }

    public static void main(String[] args) {
        try {
            SwingUtilities.invokeLater(new RubixCubeGUI());
        } catch (FileNotFoundException e) {
            System.out.println("An error has occurred");
        }
    }
}
