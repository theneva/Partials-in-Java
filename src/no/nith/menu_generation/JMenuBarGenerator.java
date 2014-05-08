package no.nith.menu_generation;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.List;

/**
 * Utility for generating a JMenuBar from a JSON file.
 */
public class JMenuBarGenerator
{
    /**
     * Generates a complete JMenuBar from a JSON file.
     *
     * @param jsonMenuFileName the name of the JSON file.
     * @param actionListener   the action listener assigned to each JMenuItem.
     * @return the JMenuBar filled with items.
     * @throws FileNotFoundException if the JSON file cannot be found.
     */
    public static JMenuBar generateJMenuBarFromJson(String jsonMenuFileName, ActionListener actionListener) throws FileNotFoundException
    {
        List<MenuInformationContainer> menuInformationContainers = getCustomMenus(jsonMenuFileName);

        JMenuBar jMenuBar = new JMenuBar();
        fillMenuBar(jMenuBar, menuInformationContainers, actionListener);

        return jMenuBar;
    }

    /**
     * Generates a list of MenuInformationContainers from the specified JSON file.
     *
     * @param menuInformationFileName the name of the file.
     * @return the list of MenuInformationContainers.
     * @throws FileNotFoundException if the file cannot be found.
     */
    private static List<MenuInformationContainer> getCustomMenus(String menuInformationFileName) throws FileNotFoundException
    {
        Reader reader = new InputStreamReader(new FileInputStream(new File(menuInformationFileName)));
        Gson gson = new Gson();

        // This is the GSON way of generating a list from JSON.
        // The JSON key names MUST match the MenuInformationContainer field names,
        // as the <key> is translated to set<Key>(<value>)).
        return gson.fromJson(reader, new TypeToken<List<MenuInformationContainer>>()
        {
        }.getType());
    }

    /**
     * Fills the specified JMenuBar with the elements in menuInformationContainers.
     *
     * @param jMenuBar                  the JMenuBar to fill with elements.
     * @param menuInformationContainers the elements with which to fill the JMenuBar.
     * @param actionListener            the action listener assigned to each JMenuItem.
     */
    private static void fillMenuBar(JMenuBar jMenuBar, List<MenuInformationContainer> menuInformationContainers, ActionListener actionListener)
    {
        // Add each menu/menu item to the menu bar.
        for (MenuInformationContainer menuInformationContainer : menuInformationContainers)
        {
            JMenu menu = new JMenu(menuInformationContainer.getLabel());

            for (MenuInformationContainer child : menuInformationContainer.getChildren())
            {
                // Recursively fill the menu with its children.
                addMenuItemsToMenu(menu, child, actionListener);
            }

            jMenuBar.add(menu);
        }
    }

    /**
     * Recursively adds menu items to the menu in order to achieve nesting.
     *
     * @param menu                            the actual menu to which elements will be added.
     * @param currentMenuInformationContainer data about current menu item which will be added to the menu.
     * @param actionListener                  the action listener which is assigned to the current menu.
     */
    private static void addMenuItemsToMenu(JMenu menu, MenuInformationContainer currentMenuInformationContainer, ActionListener actionListener)
    {
        // No children => the current item is a List Item, not a submenu.
        if (currentMenuInformationContainer.getChildren() == null)
        {
            JMenuItem menuItem = new JMenuItem(currentMenuInformationContainer.getLabel());

            // The action command determines which method to use in the action listener.
            menuItem.setActionCommand(currentMenuInformationContainer.getActionCommand());
            menuItem.addActionListener(actionListener);

            menu.add(menuItem);

            return;
        }

        // The current item is a submenu...
        JMenu subMenu = new JMenu(currentMenuInformationContainer.getLabel());
        menu.add(subMenu);

        // ... so add each of its children to that menu.
        for (MenuInformationContainer child : currentMenuInformationContainer.getChildren())
        {
            addMenuItemsToMenu(subMenu, child, actionListener);
        }
    }
}
