package net.ddns.endercrypt.intellicommand;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import net.ddns.endercrypt.intellicommand.bundle.Bundle;
import net.ddns.endercrypt.intellicommand.command.Command;
import net.ddns.endercrypt.intellicommand.command.Priority;
import net.ddns.endercrypt.intellicommand.exception.IntelliCommandException;
import net.ddns.endercrypt.intellicommand.exception.IntelliCommandNotFound;
import net.ddns.endercrypt.intellicommand.exception.MalformedArgumentException;
import net.ddns.endercrypt.intellicommand.mapper.ArgMapper;
import net.ddns.endercrypt.intellicommand.mapper.Mappers;

public class IntelliCommandManager
{
	private List<Command> commands = new ArrayList<>();
	private Mappers mappers = new Mappers();

	public IntelliCommandManager()
	{

	}

	public void registerMapper(Class<?> mapperType, ArgMapper mapper)
	{
		mappers.register(mapperType, mapper);
	}

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

	public void trigger(Bundle bundle, String stringCommand)
	{
		String[] args = split(stringCommand);

		for (Command command : commands)
		{
			Object[] commandArguments = command.obtainArguments(mappers, args, bundle);
			if (commandArguments != null)
			{
				try
				{
					command.trigger(commandArguments);
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
