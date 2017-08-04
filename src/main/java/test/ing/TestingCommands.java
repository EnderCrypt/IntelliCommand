package test.ing;

import java.awt.Point;

import net.ddns.endercrypt.intellicommand.CommandPath;
import net.ddns.endercrypt.intellicommand.command.Include;
import net.ddns.endercrypt.intellicommand.command.Priority;
import net.ddns.endercrypt.intellicommand.command.CommandParam;

@CommandPath("send")
public class TestingCommands
{
	@Priority(1)
	@CommandPath("message {number}")
	public void message(@Include("key") Point key, @CommandParam("number") int text)
	{
		System.out.println("KEY: " + key);
		System.out.println("NUMBER: " + text);
	}

	@Priority(0)
	@CommandPath("message {text}")
	public void message(@Include("key") Point key, @CommandParam("text") String text)
	{
		System.out.println("KEY: " + key);
		System.out.println("TEXT: " + text);
	}
}
