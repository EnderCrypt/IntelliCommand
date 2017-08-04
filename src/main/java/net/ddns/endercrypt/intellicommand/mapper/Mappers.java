package net.ddns.endercrypt.intellicommand.mapper;

import java.util.HashMap;
import java.util.Map;

import net.ddns.endercrypt.intellicommand.exception.IntelliCommandException;
import net.ddns.endercrypt.intellicommand.mapper.common.DoubleMapper;
import net.ddns.endercrypt.intellicommand.mapper.common.IntegerMapper;
import net.ddns.endercrypt.intellicommand.mapper.common.StringMapper;

public class Mappers
{
	private Map<Class<?>, ArgMapper> mappers = new HashMap<>();
	{
		register(String.class, new StringMapper());
		register(Integer.class, new IntegerMapper());
		register(Double.class, new DoubleMapper());
	}

	public Mappers()
	{
		// TODO Auto-generated constructor stub
	}

	public void register(Class<?> mapperType, ArgMapper mapper)
	{
		mappers.put(mapperType, mapper);
	}

	public Object map(String value, Class<?> clazz)
	{
		ArgMapper mapper = mappers.get(clazz);
		if (mapper == null)
			throw new IntelliCommandException("Couldnt find mapper for class " + clazz.getSimpleName());

		return mapper.map(value);
	}
}
