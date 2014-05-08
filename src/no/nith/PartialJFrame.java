package no.nith;

import no.nith.domain.JMenuBarGeneratorFromFile;
import no.nith.menu_method_holders.MenuMethodSwapper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.lang.reflect.Method;
import java.util.HashMap;

public class PartialJFrame<T> extends JFrame implements ActionListener
{
    private HashMap<String, ActionListener> availableMethods = new HashMap<>();

    public PartialJFrame(T methodHolder, String jsonMenuFileName) throws FileNotFoundException
    {
        super("Hello");

        JMenuBar jMenuBar = JMenuBarGeneratorFromFile.generateJMenuBarFromJson(jsonMenuFileName, this);
        setJMenuBar(jMenuBar);

        updateAvailableMethods(methodHolder);

        JButton swapSetsButton = new JButton("Swap methods");
        swapSetsButton.addActionListener(e -> MenuMethodSwapper.swapAvailableMethodSet(this));
        add(swapSetsButton, BorderLayout.SOUTH);
    }

    public void addFunction(String actionCommand, ActionListener listener)
    {
        availableMethods.put(actionCommand, listener);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (!availableMethods.containsKey(e.getActionCommand()))
        {
            System.err.println("Method " + e.getActionCommand() + " not found.");
            return;
        }

        availableMethods.get(e.getActionCommand()).actionPerformed(e);
    }

    public void updateAvailableMethods(T methodHolder)
    {
        System.err.println("updateAvailableMethods called with methodHolder = " + methodHolder.getClass().getName());
        Method[] methods = methodHolder.getClass().getDeclaredMethods();

        for (Method method : methods)
        {
            addFunction(method.getName(), e -> {
                try
                {
                    method.invoke(null);
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            });
        }

        System.err.println("availableMethods are now = " + availableMethods);
    }

    public void clearAvailableMethods()
    {
        availableMethods = new HashMap<>();
    }
}


