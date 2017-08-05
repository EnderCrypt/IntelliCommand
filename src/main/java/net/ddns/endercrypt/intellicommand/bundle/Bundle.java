package net.ddns.endercrypt.intellicommand.bundle;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class Bundle
{
	private Map<String, Supplier<Object>> suppliers = new HashMap<>();

	public Bundle()
	{
		// TODO Auto-generated constructor stub
	}

	public void add(String key, Object item)
	{
		addSupplier(key, () -> item);
	}

	public void addSupplier(String key, Supplier<Object> supplier)
	{
		suppliers.put(key.toLowerCase(), supplier);
	}

	public Object get(String key)
	{
		Supplier<Object> supplier = suppliers.get(key.toLowerCase());
		if (supplier == null)
		{
			return null;
		}
		return supplier.get();
	}
}
