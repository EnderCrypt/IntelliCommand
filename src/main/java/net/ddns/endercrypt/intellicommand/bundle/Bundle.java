package net.ddns.endercrypt.intellicommand.bundle;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @author EnderCrypt
 *
 * bundle for holding objects to be @Include in command methods
 */
public class Bundle
{
	private Map<String, Supplier<Object>> suppliers = new HashMap<>();

	public Bundle()
	{
		// TODO Auto-generated constructor stub
	}

	/**
	 * adds a new object to be available from @Include
	 * @param key
	 * @param item
	 */
	public void add(String key, Object item)
	{
		addSupplier(key, () -> item);
	}

	/**
	 * adds a supplier to be able to supply an object to @Include
	 * the advantages to this is that you might not need to build an object 
	 * unless its actually needed
	 * @param key
	 * @param supplier
	 */
	public void addSupplier(String key, Supplier<Object> supplier)
	{
		suppliers.put(key.toLowerCase(), supplier);
	}

	/**
	 * gets a bundled object
	 * @param key
	 * @return
	 */
	public Object get(String key)
	{
		Supplier<Object> supplier = suppliers.get(key.toLowerCase());
		if (supplier == null)
		{
			return null;
		}
		Object object = supplier.get();
		// cache
		suppliers.put(key, () -> object);
		// return
		return object;
	}
}
