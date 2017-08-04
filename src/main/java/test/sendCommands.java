package test;

import java.awt.Point;

import net.ddns.endercrypt.intellicommand.CommandPath;
import net.ddns.endercrypt.intellicommand.command.Include;
import net.ddns.endercrypt.intellicommand.command.CommandParam;

@CommandPath("send")
public class sendCommands
{
	@CommandPath("message {number}")
	public void message(@Include("key") Point key, @CommandParam("number") int text)
	{
		System.out.println("KEY: " + key);
		System.out.println("NUMBER: " + text);
	}

	@CommandPath("message {text}")
	public void message(@Include("key") Point key, @CommandParam("text") String text)
	{
		System.out.println("KEY: " + key);
		System.out.println("TEXT: " + text);
	}
}
