/**
 * 
 */
package com.robotsidekick.tools.lcdfixer;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.KeyboardFocusManager;
import java.awt.Window;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

import com.robotsidekick.util.AppleUtil;
import com.robotsidekick.util.AppleUtil.MyFullScreenListener;

/**
 * @author alex
 * 
 */
public final class LCDFixerWindow extends JPanel
{
    private static final long serialVersionUID = -400555424474689043L;

    public static boolean isLCDFixerWindow(final Window w)
    {
        return (w.getComponent(0) instanceof LCDFixerWindow);
    }

    public static void setFixer(final Window w, final LCDFixer lcdfixer)
    {
        if (w.getComponent(0) instanceof LCDFixerWindow)
        {
            ((LCDFixerWindow) w.getComponent(0)).setFixer(lcdfixer);
        }
    }

    public LCDFixerWindow(final LCDFixer iFixer)
    {
        setFixer(iFixer);

        setFullScreen(false);

        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new FullScreenKeyDispatcher(this));
    }

    private JFrame window;
    private LCDFixer fixer;

    public void setFullScreen(final boolean isFullScreen)
    {
        JMenuBar iMenuBar = null;
        if (window != null)
        {
            iMenuBar = window.getJMenuBar();
            window.dispose();
        }

        window = new JFrame();
        window.setUndecorated(isFullScreen);
        window.setJMenuBar(iMenuBar);
        if (window.getJMenuBar() != null)
        {
            window.getJMenuBar().setVisible(!isFullScreen);
        }
        window.setExtendedState(JFrame.MAXIMIZED_BOTH);
        window.getContentPane().add(this);

        if (AppleUtil.isLion())
        {
            AppleUtil.setWindowCanFullScreen(window, true);
            AppleUtil.addFullScreenListenerTo(window, new MyFullScreenListener()
            {

                @Override
                public void windowExitingFullScreen()
                {
                    System.out.println("Exiting Full Screen");
                }

                @Override
                public void windowEnteringFullScreen()
                {
                    System.out.println("Entering Full Screen");
                }
            });
            AppleUtil.requestToggleFullScreen(getWindow());
        }
        else if (AppleUtil.isOsX())
        {
            // OS X doesn't handle full screen very well...
            window.setSize(java.awt.Toolkit.getDefaultToolkit().getScreenSize());
            window.setResizable(!isFullScreen);
        }
        else
        {
            final GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice device = null;

            if (ge != null && ge.getScreenDevices() != null && ge.getScreenDevices().length > 0 && ge.getScreenDevices()[0].isFullScreenSupported())
            {
                device = ge.getScreenDevices()[0];
                device.setFullScreenWindow(isFullScreen ? window : null);
            }
            else
            {
                window.setSize(java.awt.Toolkit.getDefaultToolkit().getScreenSize());
                window.setResizable(!isFullScreen);
            }
        }
        window.setVisible(true);
    }

    public void setFixer(final LCDFixer lcdfixer)
    {
        if (fixer != null)
        {
            fixer.stop(this);
        }
        fixer = lcdfixer;
        fixer.start(this);
    }

    public void setJMenuBar(final JMenuBar bar)
    {
        window.setJMenuBar(bar);
    }

    public boolean isUndecorated()
    {
        return window.isUndecorated();
    }

    @Override
    public void setVisible(final boolean iVisible)
    {
        super.setVisible(iVisible);
        window.setVisible(iVisible);
    }

    public Window getWindow()
    {
        return window;
    }

}
