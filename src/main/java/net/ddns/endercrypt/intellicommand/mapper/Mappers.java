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

/**
 * @author EnderCrypt
 * 
 * class that holds all mappers and the class they produce
 * default mappers exists for all basic primitive datatypes +string
 * 
 * strings can be converted into the correct object using the map method
 */
public class Mappers
{
	private Map<Class<?>, ArgMapper<?>> mappers = new HashMap<>();
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
		// common
		register(String.class, new StringMapper());
	}

	/**
	 * allows adding additional mappers
	 * @param mapperType
	 * @param mapper
	 */
	public void register(Class<?> mapperType, ArgMapper mapper)
	{
		mappers.put(mapperType, mapper);
	}

	/**
	 * attempts to convert a string into its mapper object
	 * @param value
	 * @param clazz
	 * @return
	 * @throws MapperConversionFailed if an internal mapper failed to map the correct object
	 * @throws IntelliCommandException if no mapper was available to convert to this specific class
	 * @throws IllegalArgumentException if an internal mapper converted to the wrong object class
	 */
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
