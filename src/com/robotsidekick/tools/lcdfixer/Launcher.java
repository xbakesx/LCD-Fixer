/**
 * 
 */
package com.robotsidekick.tools.lcdfixer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import com.robotsidekick.tools.lcdfixer.fixers.RainbowFixer;
import com.robotsidekick.tools.lcdfixer.fixers.WhiteFixer;
import com.robotsidekick.util.ReflectionUtil;

/**
 * @author alex
 * 
 */
public final class Launcher
{

    public static Class<?>[] getAllFixers()
    {
        return new Class<?>[] { WhiteFixer.class, RainbowFixer.class };
    }

    public static void main(final String[] args)
    {
        // TODO: use java preferences to set the last one selected
        final LCDFixerWindow window = new LCDFixerWindow(new WhiteFixer());

        initMenu(window);

        // show the window
        window.setVisible(true);
    }

    private static void initMenu(final LCDFixerWindow window)
    {
        final JMenuBar bar = new JMenuBar();
        final JMenu view = new JMenu("View");
        view.add(createFullScreenMenuItem(window));
        view.add(createFixerMenuItem(window));
        bar.add(view);
        window.setJMenuBar(bar);
    }

    private static JMenuItem createFixerMenuItem(final LCDFixerWindow window)
    {
        final JMenu fixers = new JMenu("Fixers");
        for (final Class<?> fixer : getAllFixers())
        {
            if (ReflectionUtil.doesImplement(fixer, LCDFixer.class))
            {
                try
                {
                    final LCDFixer lcdfixer = (LCDFixer) fixer.newInstance();
                    final JMenuItem item = new JMenuItem(lcdfixer.getName());
                    item.addActionListener(new ActionListener()
                    {
                        @Override
                        public void actionPerformed(final ActionEvent evt)
                        {
                            window.setFixer(lcdfixer);
                        }
                    });
                    fixers.add(item);
                }
                catch (final Throwable e)
                {
                    e.printStackTrace();
                }
            }
            else
            {
                System.err.println(fixer.getSimpleName() + " does not implement " + LCDFixer.class.getSimpleName());
            }
        }
        return fixers;
    }

    private static JMenuItem createFullScreenMenuItem(final LCDFixerWindow window)
    {
        final JMenuItem fullscreen = new JMenuItem("Full Screen", 'F');
        // fullscreen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F,
        // AppleUtils.isOsX() ? InputEvent.META_MASK : InputEvent.CTRL_MASK));
        fullscreen.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(final ActionEvent evt)
            {
                window.setFullScreen(!window.isUndecorated());
            }
        });
        return fullscreen;
    }
}
