/**
 * 
 */
package com.robotsidekick.util;

import java.awt.Window;
import java.lang.reflect.Method;

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
    private static final String requestToggleFullScreen = "requestToggleFullScreen";

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
        if (setWindowCanFullScreen(window, true))
        {
            return ReflectionUtil.invoke(Application, requestToggleFullScreen, new Class<?>[] { Window.class }, window);
        }
        else
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
}
