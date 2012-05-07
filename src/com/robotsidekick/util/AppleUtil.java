/**
 * 
 */
package com.robotsidekick.util;

import java.awt.Window;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author alex
 * 
 */
public final class AppleUtil
{
    private AppleUtil()
    {

    }

    private static final String OS_X = "Mac OS X";
    private static final String OS_X_LION = "10.7";

    private static final String FullScreenUtilities = "com.apple.eawt.FullScreenUtilities";
    private static final String setWindowCanFullScreen = "setWindowCanFullScreen";

    private static final String Application = "com.apple.eawt.Application";
    private static final String FullScreenListener = "com.apple.eawt.FullScreenListener";
    private static final String requestToggleFullScreen = "requestToggleFullScreen";
    private static final String addFullScreenListenerTo = "addFullScreenListenerTo";

    public static boolean isOsX()
    {
        return OS_X.equals(System.getProperty("os.name"));
    }

    public static boolean isLion()
    {
        return System.getProperty("os.version").startsWith(OS_X_LION);
    }

    public static boolean setWindowCanFullScreen(final Window window, final boolean enable)
    {
        boolean ret = false;

        ret = ReflectionUtil.invoke(FullScreenUtilities, setWindowCanFullScreen, new Class<?>[] { Window.class, Boolean.TYPE }, window, enable);
        return ret;
    }

    public static boolean requestToggleFullScreen(final Window window)
    {
        return ReflectionUtil.invoke(Application, requestToggleFullScreen, new Class<?>[] { Window.class }, window);
    }

    public static boolean addFullScreenListenerTo(final Window window, final MyFullScreenListener myListener)
    {
        try
        {
            final Class<?> fullScreenListenerInterface = Class.forName(FullScreenListener);
            final InvocationHandler handler = new MyFullScreenListenerProxy(myListener);
            final Class<?> proxyClass = Proxy.getProxyClass(fullScreenListenerInterface.getClassLoader(), new Class[] { fullScreenListenerInterface });
            return ReflectionUtil.invoke(FullScreenUtilities,
                                         addFullScreenListenerTo,
                                         new Class<?>[] { Window.class, Class.forName(FullScreenListener) },
                                         window,
                                         proxyClass.getConstructor(new Class[] { InvocationHandler.class }).newInstance(new Object[] { handler }));
        }
        catch (final Throwable e)
        {
            return false;
        }
    }

    public static void printMethodsForClass(final String clazz)
    {
        try
        {
            final Class<?> util = Class.forName(clazz);
            System.out.println("Methods for " + clazz);
            final Method[] methods = util.getMethods();
            for (final Method method : methods)
            {
                String sep = "";
                final StringBuilder buf = new StringBuilder();
                final Class<?>[] types = method.getParameterTypes();
                for (int i = 0, n = types.length; i < n; ++i)
                {
                    buf.append(sep);
                    buf.append(types[i].getName());
                    sep = ", ";
                }
                System.out.println("\t" + method.getName() + ":" + buf.toString());
            }
        }
        catch (final Throwable e)
        {
            e.printStackTrace();
        }
    }

    public static interface MyFullScreenListener
    {
        void windowEnteringFullScreen();

        void windowExitingFullScreen();
    }

    public static class MyFullScreenListenerProxy implements InvocationHandler
    {
        private static final String windowEnteringFullScreen = "windowEnteringFullScreen";
        private static final String windowExitingFullScreen = "windowExitingFullScreen";
        private final MyFullScreenListener listener;

        public MyFullScreenListenerProxy(final MyFullScreenListener myListener)
        {
            listener = myListener;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable
        {
            if (windowEnteringFullScreen.equals(method.getName()))
            {
                listener.windowEnteringFullScreen();
            }
            else if (windowExitingFullScreen.equals(method.getName()))
            {
                listener.windowExitingFullScreen();
            }
            return null;
        }

        // 42 public void windowEnteringFullScreen(final FullScreenEvent e);
        // 43
        // 44 /**
        // 45 * Invoked when a window has fully entered full screen.
        // 46 * @param event containing the specific window which has entered
        // full screen.
        // 47 */
        // 48 public void windowEnteredFullScreen(final FullScreenEvent e);
        // 49
        // 50 /**
        // 51 * Invoked when a window has started to exit full screen.
        // 52 * @param event containing the specific window exiting full screen.
        // 53 */
        // 54 public void windowExitingFullScreen(final FullScreenEvent e);
        // 55
        // 56 /**
        // 57 * Invoked when a window has fully exited full screen.
        // 58 * @param event containing the specific window which has exited
        // full screen.
        // 59 */
        // 60 public void windowExitedFullScreen(final FullScreenEvent e);

    }
}
