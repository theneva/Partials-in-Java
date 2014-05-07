package no.nith;

import no.nith.menu_holders.MenuMethodHolder;

import javax.swing.*;
import java.io.FileNotFoundException;

/**
 * Created by theneva on 07/05/14.
 */
public class Main
{
    public static void main(String[] args) throws FileNotFoundException
    {
        MenuMethodHolder menuMethodHolder = new MenuMethodHolder();

        PartialJFrame<MenuMethodHolder> notepad = new PartialJFrame<>(menuMethodHolder, "menus.json");

        JTextArea area = new JTextArea();
        notepad.add(area);

        notepad.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        notepad.setSize(400, 300);
        notepad.setVisible(true);
    }
}
