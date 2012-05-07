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
public final class WhiteBlackFixer extends TimedFixer
{

    public WhiteBlackFixer()
    {
        super(250);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName()
    {
        return "Black & White";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void run(final LCDFixerWindow window, final int count)
    {
        if (count % 2 == 0)
        {
            window.setBackground(Color.white);
        }
        else
        {
            window.setBackground(Color.black);
        }
    }

}
