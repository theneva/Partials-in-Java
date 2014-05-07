package no.nith;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import no.nith.domain.CustomMenu;
import no.nith.menu_holders.MenuMethodSwapper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

public class PartialJFrame<T> extends JFrame implements ActionListener
{
    private HashMap<String, ActionListener> availableMethods;

    public PartialJFrame(T methodHolder, String menuInformationFileName) throws FileNotFoundException
    {
        super("Hello");

        List<CustomMenu> customMenus = getCustomMenus(menuInformationFileName);
        JMenuBar menuBar = fillMenuBar(customMenus);
        setJMenuBar(menuBar);

        setJMenuBar(menuBar);

        clearAvailableMethods();
        updateAvailableMethods(methodHolder);

        JButton swapSetsButton = new JButton("Swap methods");
        swapSetsButton.addActionListener(e -> MenuMethodSwapper.swapAvailableMethodSet(this));
        add(swapSetsButton, BorderLayout.SOUTH);
    }

    private List<CustomMenu> getCustomMenus(String menuInformationFileName) throws FileNotFoundException
    {
        Reader reader = new InputStreamReader(new FileInputStream(new File(menuInformationFileName)));
        Gson gson = new Gson();

        return gson.fromJson(reader, new TypeToken<List<CustomMenu>>()
        {
        }.getType());
    }

    private JMenuBar fillMenuBar(List<CustomMenu> customMenus)
    {
        JMenuBar menuBar = new JMenuBar();

        for (CustomMenu customMenu : customMenus)
        {
            JMenu menu = new JMenu(customMenu.getLabel());

            for (CustomMenu dust : customMenu.getItems())
            {
                addMenuItemsToMenu(menu, dust);
            }

            menuBar.add(menu);
        }

        return menuBar;
    }

    private void addMenuItemsToMenu(JMenu menu, CustomMenu currentMenu)
    {
        // TODO add action.
        if (currentMenu.getItems() == null)
        {
            JMenuItem menuItem = new JMenuItem(currentMenu.getLabel());

            menuItem.setActionCommand(currentMenu.getActionCommand());

            menuItem.addActionListener(this);

            menu.add(menuItem);
            return;
        }

        JMenu subMenu = new JMenu(currentMenu.getLabel());
        menu.add(subMenu);

        for (CustomMenu child : currentMenu.getItems())
        {
            addMenuItemsToMenu(subMenu, child);
        }
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


