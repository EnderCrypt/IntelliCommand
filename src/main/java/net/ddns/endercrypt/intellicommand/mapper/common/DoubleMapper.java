package net.ddns.endercrypt.intellicommand.mapper.common;

import net.ddns.endercrypt.intellicommand.exception.MapperConversionFailed;
import net.ddns.endercrypt.intellicommand.mapper.ArgMapper;

public class DoubleMapper implements ArgMapper
{
	@Override
	public Object map(String text) throws MapperConversionFailed
	{
		try
		{
			return Double.valueOf(text);
		}
		catch (NumberFormatException e)
		{
			throw new MapperConversionFailed();
		}
	}
}
