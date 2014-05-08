package no.nith.domain;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.List;

/**
 * Created by theneva on 08/05/14.
 */
public class JMenuBarGeneratorFromFile
{
    public static JMenuBar generateJMenuBarFromJson(String jsonMenuFileName, ActionListener actionListener) throws FileNotFoundException
    {
        List<CustomMenu> customMenus = getCustomMenus(jsonMenuFileName);

        return fillMenuBar(customMenus, actionListener);
    }

    private static List<CustomMenu> getCustomMenus(String menuInformationFileName) throws FileNotFoundException
    {
        Reader reader = new InputStreamReader(new FileInputStream(new File(menuInformationFileName)));
        Gson gson = new Gson();

        return gson.fromJson(reader, new TypeToken<List<CustomMenu>>()
        {
        }.getType());
    }

    private static JMenuBar fillMenuBar(List<CustomMenu> customMenus, ActionListener actionListener)
    {
        JMenuBar menuBar = new JMenuBar();

        for (CustomMenu customMenu : customMenus)
        {
            JMenu menu = new JMenu(customMenu.getLabel());

            for (CustomMenu dust : customMenu.getItems())
            {
                addMenuItemsToMenu(menu, dust, actionListener);
            }

            menuBar.add(menu);
        }

        return menuBar;
    }

    private static void addMenuItemsToMenu(JMenu menu, CustomMenu currentMenu, ActionListener actionListener)
    {
        // TODO add action.
        if (currentMenu.getItems() == null)
        {
            JMenuItem menuItem = new JMenuItem(currentMenu.getLabel());

            menuItem.setActionCommand(currentMenu.getActionCommand());

            menuItem.addActionListener(actionListener);

            menu.add(menuItem);

            return;
        }

        JMenu subMenu = new JMenu(currentMenu.getLabel());
        menu.add(subMenu);

        for (CustomMenu child : currentMenu.getItems())
        {
            addMenuItemsToMenu(subMenu, child, actionListener);
        }
    }
}
