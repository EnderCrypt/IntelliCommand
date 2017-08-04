package test;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import net.ddns.endercrypt.intellicommand.IntelliCommandManager;

public class Main
{
	public static void main(String[] args)
	{
		IntelliCommandManager commandManager = new IntelliCommandManager();

		commandManager.registerCommands(new sendCommands());

		Map<String, Object> bundle = new HashMap<>();
		bundle.put("key", new Point(25, 0));
		commandManager.trigger(bundle, "send message 67");
	}
}
