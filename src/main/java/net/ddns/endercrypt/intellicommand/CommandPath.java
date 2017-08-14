package net.ddns.endercrypt.intellicommand;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * @author EnderCrypt
 * 
 * annotation for representing a command, each variable should be surrounded by { and }
 */
@Retention(RUNTIME)
@Target({ TYPE, METHOD })
public @interface CommandPath
{
	public String value();
}
