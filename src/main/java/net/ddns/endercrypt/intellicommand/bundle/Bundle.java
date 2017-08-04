package net.ddns.endercrypt.intellicommand.bundle;

import java.util.HashMap;
import java.util.Map;

public class Bundle
{
	private Map<String, Object> items = new HashMap<>();

	public Bundle()
	{
		// TODO Auto-generated constructor stub
	}

	public void add(String key, Object item)
	{
		items.put(key, item);
	}

	public Object check(String key)
	{
		return items.get(key);
	}
}
