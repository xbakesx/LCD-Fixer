/**
 * 
 */
package com.robotsidekick.tools.lcdfixer.fixers;

import java.awt.Color;

import com.robotsidekick.tools.lcdfixer.LCDFixer;
import com.robotsidekick.tools.lcdfixer.LCDFixerWindow;

/**
 * @author alex
 * 
 */
public final class WhiteFixer implements LCDFixer
{

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName()
    {
        return "White";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start(final LCDFixerWindow window)
    {
        window.setBackground(Color.white);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stop(final LCDFixerWindow window)
    {
        // ignore
    }

}
