package test.ing;

import java.awt.Point;

import net.ddns.endercrypt.intellicommand.CommandPath;
import net.ddns.endercrypt.intellicommand.bundle.Include;
import net.ddns.endercrypt.intellicommand.command.CommandParam;

@CommandPath("tell")
public class TestingCommands
{
	@CommandPath("hello {arg}")
	public void user1(@CommandParam("arg") String text, @Include("POINT") Point point)
	{
		System.out.println("POINT: " + point + " said: " + text);
	}
}
