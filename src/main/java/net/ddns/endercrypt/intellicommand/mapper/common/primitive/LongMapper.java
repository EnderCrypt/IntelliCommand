package net.ddns.endercrypt.intellicommand.mapper.common.primitive;


import net.ddns.endercrypt.intellicommand.exception.MapperConversionFailed;
import net.ddns.endercrypt.intellicommand.mapper.ArgMapper;


public class LongMapper implements ArgMapper<Long>
{
	@Override
	public Long map(String text) throws MapperConversionFailed
	{
		try
		{
			return Long.valueOf(text);
		}
		catch (NumberFormatException e)
		{
			throw new MapperConversionFailed();
		}
	}
}
