package net.ddns.endercrypt.intellicommand;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import net.ddns.endercrypt.intellicommand.bundle.Bundle;
import net.ddns.endercrypt.intellicommand.command.Command;
import net.ddns.endercrypt.intellicommand.command.Priority;
import net.ddns.endercrypt.intellicommand.exception.IntelliCommandException;
import net.ddns.endercrypt.intellicommand.exception.IntelliCommandNotFound;
import net.ddns.endercrypt.intellicommand.exception.MalformedArgumentException;
import net.ddns.endercrypt.intellicommand.mapper.ArgMapper;
import net.ddns.endercrypt.intellicommand.mapper.Mappers;

/**
 * @author EnderCrypt
 *
 * main class for handling commands, the registerMapper method should be used to register objects with commands first
 * and custom mappers if needed, afterwards commands can be inputed through the trigger method
 */
public class IntelliCommandManager
{
	private List<Command> commands = new ArrayList<>();
	private Mappers mappers = new Mappers();

	public IntelliCommandManager()
	{

	}

	/**
	 * registers a mapper class to allow command arguments to be converted into java objects
	 * 
	 * @param mapperType the mapper class
	 * @param mapper the object to be converted into
	 */
	public void registerMapper(Class<?> mapperType, ArgMapper mapper)
	{
		mappers.register(mapperType, mapper);
	}

	/**
	 * registers an object to recieve commands, for a class to be accepted it needs to have
	 * @CommandPath("value") as a class annotation and for each method that should be a command
	 * 
	 * @param object
	 */
	public void registerCommands(Object object)
	{
		// vars
		Class<?> clazz = object.getClass();
		String clazzName = clazz.getSimpleName();

		// get type annotation
		CommandPath classCommandPath = clazz.getAnnotation(CommandPath.class);
		if (classCommandPath == null)
			throw new IllegalArgumentException(clazzName + " missing annotation " + CommandPath.class.getSimpleName());
		String global = classCommandPath.value();

		// get methods
		for (Method method : clazz.getDeclaredMethods())
		{
			CommandPath methodCommandPath = method.getAnnotation(CommandPath.class);
			if (methodCommandPath != null)
			{
				// process path
				String local = methodCommandPath.value();
				String fullPath = global + " " + local;
				String[] args = Arrays.stream(fullPath.split(" ")).filter(s -> !s.equals("")).toArray(String[]::new);

				// priority
				int priority = 0;
				Priority priorityAnnotation = method.getAnnotation(Priority.class);
				if (priorityAnnotation != null)
				{
					priority = priorityAnnotation.value();
				}

				// add command
				commands.add(new Command(args, object, method, priority));
			}
		}

		// sort
		Collections.sort(commands);
	}

	/**
	 * internal method for splitting a string at each space OR quotes
	 * @param text
	 * @return an array of command + arguments
	 */
	private static String[] split(String text)
	{
		List<String> args = new ArrayList<>();

		StringBuilder sb = new StringBuilder();
		boolean inQuote = false;
		for (char c : text.toCharArray())
		{
			if (c == '\"')
			{
				if (inQuote)
				{
					args.add(sb.toString());
					sb.setLength(0);
					inQuote = false;
				}
				else
				{
					inQuote = true;
				}
				continue;
			}
			if (c == ' ')
			{
				if (inQuote)
				{
					sb.append(' ');
				}
				else
				{
					if (sb.length() > 0)
					{
						args.add(sb.toString());
						sb.setLength(0);
					}
				}
				continue;
			}
			sb.append(c);
		}
		if (inQuote == true)
		{
			throw new MalformedArgumentException("Malformed command, should have starting and ending quotes");
		}
		if (sb.length() > 0)
		{
			args.add(sb.toString());
		}
		return args.toArray(new String[args.size()]);
	}

	/**
	 * attempts to find and activate a command from a class thats been previously added through registerCommands
	 * a bundle of objects should be included, these will be passed on to command methods with the @Include annotation
	 * 
	 * @param bundle
	 * @param stringCommand
	 */
	public void trigger(Bundle bundle, String stringCommand)
	{
		String[] args = split(stringCommand);

		for (Command command : commands)
		{
			Optional<Object[]> optionalCommandArguments = command.obtainArguments(mappers, args, bundle);
			if (optionalCommandArguments.isPresent())
			{
				Object[] objArgs = optionalCommandArguments.get();
				try
				{
					command.trigger(objArgs);
				}
				catch (ReflectiveOperationException e)
				{
					throw new IntelliCommandException(e);
				}
				return;
			}
		}
		throw new IntelliCommandNotFound(stringCommand);
	}
}
