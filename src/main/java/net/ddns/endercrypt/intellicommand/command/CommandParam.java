package net.ddns.endercrypt.intellicommand.command;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * @author EnderCrypt
 * 
 * command method annotation for each parameter that should take a variable from a command
 */
@Retention(RUNTIME)
@Target(PARAMETER)
public @interface CommandParam
{
	public String value();
}
