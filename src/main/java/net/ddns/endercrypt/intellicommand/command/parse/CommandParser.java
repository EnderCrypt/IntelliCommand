package net.ddns.endercrypt.intellicommand.command.parse;

import java.lang.reflect.Parameter;
import net.ddns.endercrypt.intellicommand.bundle.Bundle;
import net.ddns.endercrypt.intellicommand.bundle.Include;
import net.ddns.endercrypt.intellicommand.command.CommandParam;
import net.ddns.endercrypt.intellicommand.exception.IntelliCommandException;
import net.ddns.endercrypt.intellicommand.exception.MapperConversionFailed;
import net.ddns.endercrypt.intellicommand.mapper.Mappers;

public class CommandParser
{
	private Mappers mappers;

	private String[] args;
	private String[] commandArgs;
	private Parameter[] parameters;

	private Object[] returnArgs;

	public CommandParser(Mappers mappers, String[] args, String[] commandArgs, Parameter[] parameters, Bundle bundle)
	{
		this.mappers = mappers;

		this.args = args;
		this.commandArgs = commandArgs;
		this.parameters = parameters;

		this.returnArgs = new Object[parameters.length];

		// set includes
		for (int i = 0; i < parameters.length; i++)
		{
			Parameter parameter = parameters[i];
			Include bundleAnnotation = parameter.getAnnotation(Include.class);
			if (bundleAnnotation != null)
			{
				String name = bundleAnnotation.value();
				Object bundleValue = bundle.get(name);

				if (bundleValue != null)
				{
					Class<?> clazz = filterPrimitiveType(parameter.getType());
					Class<?> bundleClazz = bundleValue.getClass();
					if (clazz.equals(bundleClazz) == false)
						throw new IntelliCommandException("command method requested bundle named \"" + name + "\" type: " + clazz.getName() + " but got bundle class: " + bundleClazz.getName());

					returnArgs[i] = bundleValue;
				}
			}
		}
	}

	public void parse() throws MapperConversionFailed
	{
		if (args.length != commandArgs.length)
			throw new IllegalArgumentException("wrong number of arguments (was " + args.length + ", expected: " + commandArgs.length + ")");

		for (int i = 0; i < commandArgs.length; i++)
		{
			String arg = args[i];
			String commandArg = commandArgs[i];

			if (commandArg.startsWith("{") && commandArg.endsWith("}"))
			{
				String name = commandArg.substring(1, commandArg.length() - 1);
				String value = args[i];
				setArg(name, value);
			}
			else
			{
				if (arg.equals(commandArg) == false)
					throw new IllegalArgumentException("expected " + commandArg + " got " + arg + " at position " + i);
			}
		}

		for (int i = 0; i < returnArgs.length; i++)
		{
			Object returnArg = returnArgs[i];

			if (returnArg == null)
			{
				throw new IllegalArgumentException("Missing argument to fill position " + (i + 1));
			}
		}
	}

	private void setArg(String name, String value) throws MapperConversionFailed
	{
		for (int i = 0; i < parameters.length; i++)
		{
			Parameter parameter = parameters[i];
			CommandParam commandParam = parameter.getAnnotation(CommandParam.class);
			if (commandParam != null)
			{
				if (commandParam.value().equals(name)) // find first occurance of name
				{
					Class<?> clazz = filterPrimitiveType(parameter.getType());
					returnArgs[i] = mappers.map(value, clazz);
					return;
				}
			}
		}
		throw new IllegalArgumentException("couldnt find " + CommandParam.class.getSimpleName() + "(\"" + name + "\")");
	}

	public static Class<?> filterPrimitiveType(Class<?> clazz)
	{
		/*
		java.lang.Boolean#TYPE
		java.lang.Character#TYPE
		java.lang.Byte#TYPE
		java.lang.Short#TYPE
		java.lang.Integer#TYPE
		java.lang.Long#TYPE
		java.lang.Float#TYPE
		java.lang.Double#TYPE
		 */
		if (clazz.isPrimitive())
		{
			switch (clazz.getSimpleName())
			{
			case "boolean":
				clazz = Boolean.class;
				break;
			case "char":
				clazz = Character.class;
				break;
			case "byte":
				clazz = Byte.class;
				break;
			case "short":
				clazz = Short.class;
				break;
			case "int":
				clazz = Integer.class;
				break;
			case "long":
				clazz = Long.class;
				break;
			case "float":
				clazz = Float.class;
				break;
			case "double":
				clazz = Double.class;
				break;
			default:
				throw new RuntimeException("Unknown primitive type:" + clazz.getSimpleName());
			}
		}
		return clazz;
	}

	public Object[] result()
	{
		return returnArgs;
	}

}
