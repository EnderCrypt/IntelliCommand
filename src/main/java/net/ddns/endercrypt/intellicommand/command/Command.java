package net.ddns.endercrypt.intellicommand.command;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Optional;

import net.ddns.endercrypt.intellicommand.bundle.Bundle;
import net.ddns.endercrypt.intellicommand.command.parse.CommandParser;
import net.ddns.endercrypt.intellicommand.exception.IntelliCommandException;
import net.ddns.endercrypt.intellicommand.exception.MapperConversionFailed;
import net.ddns.endercrypt.intellicommand.exception.UnderlyingIntelliException;
import net.ddns.endercrypt.intellicommand.mapper.Mappers;

/**
 * @author EnderCrypt
 * 
 * holder class for holding a command method and its related parameters and settings
 */
public class Command implements Comparable<Command>
{
	private String[] commandArgs;

	private Object object;
	private Method method;
	private Parameter[] parameters;

	private int priority;

	public Command(String[] commandArgs, Object object, Method method, int priority)
	{
		this.commandArgs = commandArgs;
		this.object = object;
		this.method = method;
		this.priority = priority;
		parameters = method.getParameters();
	}

	/**
	 * attempts to get the arguments used to call this command, using a list of string arguments
	 * WILL throw null if this cannot be done (aka not a valid command method to call with these arguments)
	 * @param mappers
	 * @param args
	 * @param bundle
	 * @return
	 */
	public Optional<Object[]> obtainArguments(Mappers mappers, String[] args, Bundle bundle)
	{
		CommandParser parser = new CommandParser(mappers, args, commandArgs, parameters, bundle);
		try
		{
			parser.parse();
		}
		catch (IllegalArgumentException | MapperConversionFailed e)
		{
			return Optional.empty();
		}
		return Optional.of(parser.result());
	}

	/**
	 * attempts to activate this command method with an array of object args, typically called with
	 * the return value from obtainArguments
	 * @param commandArguments
	 * @throws UnderlyingIntelliException 
	 */
	public void trigger(Object... commandArguments) throws UnderlyingIntelliException
	{
		//System.out.println("Invoking with args: " + Arrays.toString(commandArguments));
		try
		{
			method.invoke(object, commandArguments);
		}
		catch (InvocationTargetException e)
		{
			throw new UnderlyingIntelliException(e.getCause());
		}
		catch (ReflectiveOperationException e)
		{
			throw new IntelliCommandException(e);
		}
	}

	@Override
	public int compareTo(Command other)
	{
		return Integer.compare(other.priority, priority);
	}

	@Override
	public String toString()
	{
		return getClass().getName() + "[path=" + Arrays.toString(commandArgs) + ", method=" + method + "]";
	}
}
