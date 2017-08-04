package net.ddns.endercrypt.intellicommand.command;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;
import net.ddns.endercrypt.intellicommand.command.parse.CommandParser;
import net.ddns.endercrypt.intellicommand.mapper.Mappers;

public class Command
{
	private String[] commandArgs;

	private Object object;
	private Method method;
	private Parameter[] parameters;

	public Command(String[] commandArgs, Object object, Method method)
	{
		this.commandArgs = commandArgs;
		this.object = object;
		this.method = method;
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
}
