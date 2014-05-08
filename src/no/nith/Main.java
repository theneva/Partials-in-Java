package no.nith;

import javax.swing.*;
import java.io.FileNotFoundException;

/**
 * Created by theneva on 07/05/14.
 */
public class Main
{
    public static void main(String[] args) throws FileNotFoundException
    {
        Notepad notepad = new Notepad("menus.json");

        JTextArea area = new JTextArea();
        notepad.add(area);

        notepad.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // or whatever
        notepad.setSize(400, 300);
        notepad.setVisible(true);
    }
}
