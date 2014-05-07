package no.nith.menu_holders;

import no.nith.PartialJFrame;

/**
 * Created by theneva on 07/05/14.
 */
public class MenuMethodSwapper
{
    public static Class currentAvailableMethodSet = MenuMethodHolder.class;

    public static void swapAvailableMethodSet(PartialJFrame frame)
    {
        System.err.println("swapAvailableMethodSet called");
        if (frame == null)
        {
            System.err.println("Frame == null, returning");
            return;
        }

        MenuMethodSwapper swapper;

        if (currentAvailableMethodSet == MenuMethodHolder.class)
        {
            System.out.println("Setting available method set to MenuMethodHolder2");
            currentAvailableMethodSet = MenuMethodHolder2.class;
            swapper = new MenuMethodHolder2();
        }
        else
        {
            System.out.println("Setting available method set to MenuMethodHolder");
            currentAvailableMethodSet = MenuMethodHolder.class;
            swapper = new MenuMethodHolder();
        }

        frame.clearAvailableMethods();
        frame.updateAvailableMethods(swapper);
    }
}
