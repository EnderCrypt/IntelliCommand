package net.ddns.endercrypt.intellicommand.mapper.common.primitive;


import net.ddns.endercrypt.intellicommand.exception.MapperConversionFailed;
import net.ddns.endercrypt.intellicommand.mapper.ArgMapper;


public class DoubleMapper implements ArgMapper<Double>
{
	@Override
	public Double map(String text) throws MapperConversionFailed
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
