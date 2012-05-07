/**
 * 
 */
package com.robotsidekick.util;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author alex
 * 
 */
public final class ReflectionUtil
{
    public static boolean doesImplement(final Class<?> clazz, final Class<?> interfase)
    {
        for (final Class<?> clazzInterfase : getAllInterfaces(clazz))
        {
            if (interfase.equals(clazzInterfase))
            {
                return true;
            }
        }
        return false;
    }

    private static Iterable<Class<?>> getAllInterfaces(final Class<?> clazz)
    {
        final List<Class<?>> interfaces = new ArrayList<Class<?>>();
        return getAllInterfaces(clazz, interfaces);
    }

    private static Iterable<Class<?>> getAllInterfaces(final Class<?> clazz, final List<Class<?>> all)
    {
        all.addAll(Arrays.asList(clazz.getInterfaces()));
        if (clazz.getSuperclass() != null)
        {
            return getAllInterfaces(clazz.getSuperclass(), all);
        }
        else
        {
            return all;
        }
    }

    public static boolean invoke(final String clazz, final String methodName, final Class<?>[] argTypes, final Object... args)
    {
        Class<?> util = null;
        try
        {
            util = Class.forName(clazz);
            final Method method = util.getMethod(methodName, argTypes);
            if (Modifier.isStatic(method.getModifiers()))
            {
                method.invoke(util, args);
            }
            else
            {
                method.invoke(util.newInstance(), args);
            }
            return true;
        }
        catch (final ClassNotFoundException e)
        {
            e.printStackTrace();
            return false;
        }
        catch (final Throwable e)
        {
            e.printStackTrace();
            return false;
        }
    }
}
