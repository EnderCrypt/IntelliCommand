package net.ddns.endercrypt.intellicommand.command.parse;

import java.lang.reflect.Parameter;
import net.ddns.endercrypt.intellicommand.bundle.Bundle;
import net.ddns.endercrypt.intellicommand.bundle.Include;
import net.ddns.endercrypt.intellicommand.command.CommandParam;
import net.ddns.endercrypt.intellicommand.exception.IntelliCommandException;
import net.ddns.endercrypt.intellicommand.exception.MapperConversionFailed;
import net.ddns.endercrypt.intellicommand.mapper.Mappers;

/**
 * @author EnderCrypt
 * 
 * class for parsing string command arguments into arguments for a command
 * once created, cannot be re-used
 */
public class CommandParser
{
	private boolean functional = true;
	private boolean done = false;

	private Mappers mappers;

	private String[] args;
	private String[] commandArgs;
	private Parameter[] parameters;
	private Bundle bundle;

	private Object[] returnArgs;

	public CommandParser(Mappers mappers, String[] args, String[] commandArgs, Parameter[] parameters, Bundle bundle)
	{
		this.mappers = mappers;

		this.args = args;
		this.commandArgs = commandArgs;
		this.parameters = parameters;
		this.bundle = bundle;

		this.returnArgs = new Object[parameters.length];
	}

	/**
	 * performs the parsing
	 * @throws MapperConversionFailed
	 */
	public void parse() throws MapperConversionFailed
	{
		// verify that CommandParser hasnt already been parsed
		if (functional == false)
			throw new IllegalStateException("CommandParser cannot be re-used");
		functional = false;

		// verify that arguments match required args
		if (args.length != commandArgs.length)
			throw new IllegalArgumentException("wrong number of arguments (was " + args.length + ", expected: " + commandArgs.length + ")");

		// set parameters from string args
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
		// set bundles
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
					if (clazz.isInstance(bundleClazz) == false)
						throw new IntelliCommandException("command method requested bundle named \"" + name + "\" type: " + clazz.getName() + " but got bundle class: " + bundleClazz.getName());

					returnArgs[i] = bundleValue;
				}
			}
		}
		// verify theres no null's
		for (int i = 0; i < returnArgs.length; i++)
		{
			Object returnArg = returnArgs[i];

			if (returnArg == null)
			{
				throw new IllegalArgumentException("Missing argument to fill position " + (i + 1));
			}
		}
		// done
		done = true;
	}

	/**
	 * internal method for setting all arguments of name to a specific value
	 * @param name
	 * @param value
	 * @throws MapperConversionFailed
	 */
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

	/**
	 * method for retriving Class's from primitives
	 * TODO: this method is.. should be replaced, its very hardcoded/low quality, im fully aware of its terribleness and would love to replace it with
	 * something a bit less bad-looking, but untill i find something better, this will do
	 * @param clazz
	 * @return
	 */
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
		if (done == false)
			throw new IllegalStateException("cannot get results whitout finishing parsing");
		return returnArgs;
	}

}
