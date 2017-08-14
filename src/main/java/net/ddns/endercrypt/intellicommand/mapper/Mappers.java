package net.ddns.endercrypt.intellicommand.mapper;

import java.util.HashMap;
import java.util.Map;

import net.ddns.endercrypt.intellicommand.exception.IntelliCommandException;
import net.ddns.endercrypt.intellicommand.exception.MapperConversionFailed;
import net.ddns.endercrypt.intellicommand.mapper.common.StringMapper;
import net.ddns.endercrypt.intellicommand.mapper.common.primitive.BooleanMapper;
import net.ddns.endercrypt.intellicommand.mapper.common.primitive.ByteMapper;
import net.ddns.endercrypt.intellicommand.mapper.common.primitive.CharMapper;
import net.ddns.endercrypt.intellicommand.mapper.common.primitive.DoubleMapper;
import net.ddns.endercrypt.intellicommand.mapper.common.primitive.FloatMapper;
import net.ddns.endercrypt.intellicommand.mapper.common.primitive.IntegerMapper;
import net.ddns.endercrypt.intellicommand.mapper.common.primitive.LongMapper;
import net.ddns.endercrypt.intellicommand.mapper.common.primitive.ShortMapper;

public class Mappers
{
	private Map<Class<?>, ArgMapper> mappers = new HashMap<>();
	{
		// primitive
		register(Boolean.class, new BooleanMapper());
		register(Byte.class, new ByteMapper());
		register(Character.class, new CharMapper());
		register(Double.class, new DoubleMapper());
		register(Float.class, new FloatMapper());
		register(Integer.class, new IntegerMapper());
		register(Long.class, new LongMapper());
		register(Short.class, new ShortMapper());
		// objects
		// common
		register(String.class, new StringMapper());
	}

	public Mappers()
	{
		// TODO Auto-generated constructor stub
	}

	public void register(Class<?> mapperType, ArgMapper mapper)
	{
		mappers.put(mapperType, mapper);
	}

	public Object map(String value, Class<?> clazz) throws MapperConversionFailed
	{
		ArgMapper mapper = mappers.get(clazz);
		if (mapper == null)
			throw new IntelliCommandException("Couldnt find mapper for class " + clazz.getSimpleName());

		Object object = mapper.map(value);

		if (clazz.isInstance(object) == false)
			throw new IllegalArgumentException("Mapper for " + clazz + " returned an object of type " + object.getClass());

		return object;
	}
}
