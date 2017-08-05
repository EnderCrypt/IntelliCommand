package test.ing;

import java.awt.Point;
import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;

import net.ddns.endercrypt.intellicommand.IntelliCommandManager;
import net.ddns.endercrypt.intellicommand.bundle.Bundle;
import net.ddns.endercrypt.intellicommand.exception.IntelliCommandNotFound;

public class Main
{
	private static Scanner scanner = new Scanner(System.in);
	private static IntelliCommandManager manager = new IntelliCommandManager();

	public static void main(String[] args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, NoSuchFieldException
	{
		manager.registerCommands(new TestingCommands());

		while (true)
		{
			System.out.print("> ");
			String text = scanner.nextLine();
			Bundle bundle = new Bundle();
			bundle.add("POINT", new Point(5, 5));
			try
			{
				manager.trigger(bundle, text);
			}
			catch (IntelliCommandNotFound e)
			{
				System.err.println("Command not found");
			}
		}
	}
}