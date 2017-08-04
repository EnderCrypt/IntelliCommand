package net.ddns.endercrypt.intellicommand.mapper.common;

import net.ddns.endercrypt.intellicommand.exception.MapperConversionFailed;
import net.ddns.endercrypt.intellicommand.mapper.ArgMapper;

public class CharMapper implements ArgMapper
{
	@Override
	public Object map(String text) throws MapperConversionFailed
	{
		if (text.length() == 1)
		{
			return text.charAt(0);
		}
		throw new MapperConversionFailed();
	}
}
