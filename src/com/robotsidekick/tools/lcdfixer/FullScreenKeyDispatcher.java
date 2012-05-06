/**
 * 
 */
package com.robotsidekick.tools.lcdfixer;

import java.awt.KeyEventDispatcher;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import com.robotsidekick.util.AppleUtil;

/**
 * @author alex
 * 
 */
public final class FullScreenKeyDispatcher implements KeyEventDispatcher
{

    private static int MODIFIER_MASK = AppleUtil.isOsX() ? InputEvent.META_MASK : InputEvent.CTRL_MASK;

    public FullScreenKeyDispatcher(final LCDFixerWindow iWindow)
    {
        window = iWindow;
    }

    private final LCDFixerWindow window;

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean dispatchKeyEvent(final KeyEvent evt)
    {
        if (evt.getID() == KeyEvent.KEY_PRESSED)
        {
            if (evt.getKeyCode() == KeyEvent.VK_F && evt.getModifiers() == MODIFIER_MASK)
            {
                window.setFullScreen(!window.isUndecorated());
                return true;
            }
        }
        return false;
    }

}
