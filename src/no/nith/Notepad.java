package no.nith;

import no.nith.menu_generation.JMenuBarGenerator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

public class Notepad extends JFrame implements ActionListener
{
    public Notepad(String jsonMenuFileName) throws FileNotFoundException
    {
        super("Notepad with menu from JSON");

        JMenuBar jMenuBar = JMenuBarGenerator.generateJMenuBarFromJson(jsonMenuFileName, this);
        setJMenuBar(jMenuBar);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        switch (e.getActionCommand())
        {
            case "newFile":
                newFile();
                break;
            case "open":
                open();
                break;
            default:
                System.err.println("Method '" + e.getActionCommand() + "' not supported.");
                break;
        }
    }

    private void newFile()
    {
        System.out.println("newFile called");
    }

    private void open()
    {
        System.out.println("open called");
    }
}


