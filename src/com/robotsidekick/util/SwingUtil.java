/**
 * 
 */
package com.robotsidekick.util;

import java.awt.Component;
import java.awt.Container;
import java.awt.Window;

import javax.swing.JPopupMenu;

/**
 * @author alex
 * 
 */
public final class SwingUtil
{
    public static Window getParentWindow(final Component item)
    {
        final Component parent = item.getParent();
        if (parent == null)
        {
            return null;
        }
        if (parent instanceof Window)
        {
            return (Window) parent;
        }
        if (parent instanceof JPopupMenu)
        {
            return getParentWindow(((JPopupMenu) parent).getInvoker());
        }
        if (parent instanceof Container)
        {
            return getParentWindow(parent);
        }
        return null;
    }
}
