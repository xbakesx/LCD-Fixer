/**
 * 
 */
package com.robotsidekick.tools.lcdfixer;

/**
 * @author alex
 * 
 *         In order to implement this correctly, there MUST be a no-arg
 *         constructor
 */
public interface LCDFixer
{
    String getName();

    void start(final LCDFixerWindow window);

    void stop(final LCDFixerWindow window);
}
