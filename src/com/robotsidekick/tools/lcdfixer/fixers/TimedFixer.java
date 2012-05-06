/**
 * 
 */
package com.robotsidekick.tools.lcdfixer.fixers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import com.robotsidekick.tools.lcdfixer.LCDFixer;
import com.robotsidekick.tools.lcdfixer.LCDFixerWindow;

/**
 * @author alex
 * 
 */
public abstract class TimedFixer implements LCDFixer
{
    protected static final int DEFAULT_DELAY = 1000;

    public TimedFixer()
    {
        this(DEFAULT_DELAY);
    }

    public TimedFixer(final int delay)
    {
        timer = new Timer(delay, new ActionListener()
        {
            @Override
            public void actionPerformed(final ActionEvent e)
            {
                run(window, count++);
            }
        });
    }

    private final Timer timer;
    private int count;
    private LCDFixerWindow window;

    protected abstract void run(final LCDFixerWindow window, final int count);

    /**
     * {@inheritDoc}
     */
    @Override
    public final void start(final LCDFixerWindow iWindow)
    {
        window = iWindow;
        count = 0;
        timer.start();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stop(final LCDFixerWindow iWindow)
    {
        window = null;
        count = -1;
        timer.stop();
    }

}
