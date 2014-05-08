package no.nith.menu_generation;

import java.util.List;

/**
 * Created by theneva on 07/05/14.
 */
public class MenuInformationContainer
{
    private String label;
    private List<MenuInformationContainer> children;
    private String actionCommand;

    public MenuInformationContainer()
    {
    }

    public String getLabel()
    {
        return label;
    }

    public void setLabel(String label)
    {
        this.label = label;
    }

    public List<MenuInformationContainer> getChildren()
    {
        return children;
    }

    public void setChildren(List<MenuInformationContainer> children)
    {
        this.children = children;
    }

    public String getActionCommand()
    {
        return actionCommand;
    }

    public void setActionCommand(String actionCommand)
    {
        this.actionCommand = actionCommand;
    }

    @Override
    public String toString()
    {
        return "MenuInformationContainer{" +
                "label='" + label + '\'' +
                ", children=" + children +
                ", actionCommand='" + actionCommand + '\'' +
                '}';
    }
}
