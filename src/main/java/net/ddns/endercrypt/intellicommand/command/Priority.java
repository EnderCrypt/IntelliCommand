package net.ddns.endercrypt.intellicommand.command;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * @author EnderCrypt
 * 
 * optional annotation for specifying priority of command methods for overloading arguments
 * 
 * an example is when a number is passed to a method thats overloaded, where one method accepts a string
 * and the other accepts a number, if this happends whitout priority, a random method is choosen
 * 
 * higher number = priority
 */
@Retention(RUNTIME)
@Target(METHOD)
public @interface Priority
{
	public int value();
}
