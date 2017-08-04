package net.ddns.endercrypt.intellicommand.command;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Map;

import net.ddns.endercrypt.intellicommand.command.parse.CommandParser;
import net.ddns.endercrypt.intellicommand.mapper.Mappers;

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

	public Object[] obtainArguments(Mappers mappers, String[] args, Map<String, Object> bundle)
	{
		CommandParser parser = new CommandParser(mappers, args, commandArgs, parameters, bundle);
		try
		{
			parser.parse();
		}
		catch (IllegalArgumentException e)
		{
			return null;
		}
		return parser.result();
	}

	public void trigger(Object... commandArguments) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		//System.out.println("Invoking with args: " + Arrays.toString(commandArguments));
		method.invoke(object, commandArguments);
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
