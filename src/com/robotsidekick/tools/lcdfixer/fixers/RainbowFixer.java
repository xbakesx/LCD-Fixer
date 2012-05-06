/**
 * 
 */
package com.robotsidekick.tools.lcdfixer.fixers;

import java.awt.Color;

import com.robotsidekick.tools.lcdfixer.LCDFixerWindow;

/**
 * @author alex
 * 
 */
public final class RainbowFixer extends TimedFixer
{
    private static final Color[] rainbow = new Color[] { Color.red, Color.orange, Color.yellow, Color.green, Color.blue, new Color(160, 32, 240) };

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName()
    {
        return "Rainbow Fixer";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void run(final LCDFixerWindow window, final int count)
    {
        window.setBackground(rainbow[count % rainbow.length]);
    }

}
