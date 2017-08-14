package net.ddns.endercrypt.intellicommand.bundle;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * @author EnderCrypt
 *
 * command method annotation used to request an object from a bundle
 */
@Retention(RUNTIME)
@Target(PARAMETER)
public @interface Include
{
	public String value();
}
