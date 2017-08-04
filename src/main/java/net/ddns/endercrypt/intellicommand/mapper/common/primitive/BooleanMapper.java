package net.ddns.endercrypt.intellicommand.mapper.common.primitive;

import net.ddns.endercrypt.intellicommand.exception.MapperConversionFailed;
import net.ddns.endercrypt.intellicommand.mapper.ArgMapper;

public class BooleanMapper implements ArgMapper
{
	@Override
	public Object map(String text) throws MapperConversionFailed
	{
		if (text.equalsIgnoreCase("true"))
			return true;
		if (text.equalsIgnoreCase("false"))
			return false;

		throw new MapperConversionFailed();
	}
}
