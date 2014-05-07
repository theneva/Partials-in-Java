package no.nith.domain;

import java.util.List;

/**
 * Created by theneva on 07/05/14.
 */
public class CustomMenu
{
    private String label;
    private List<CustomMenu> items;
    private String actionCommand;

    public CustomMenu()
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

    public List<CustomMenu> getItems()
    {
        return items;
    }

    public void setItems(List<CustomMenu> items)
    {
        this.items = items;
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
        return "CustomMenu{" +
                "label='" + label + '\'' +
                ", items=" + items +
                ", actionCommand='" + actionCommand + '\'' +
                '}';
    }
}
