package net.ddns.endercrypt.intellicommand.mapper.common.primitive;

import net.ddns.endercrypt.intellicommand.exception.MapperConversionFailed;
import net.ddns.endercrypt.intellicommand.mapper.ArgMapper;

public class FloatMapper implements ArgMapper
{
	@Override
	public Object map(String text) throws MapperConversionFailed
	{
		try
		{
			return Float.valueOf(text);
		}
		catch (NumberFormatException e)
		{
			throw new MapperConversionFailed();
		}
	}
}