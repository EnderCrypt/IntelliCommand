package net.ddns.endercrypt.intellicommand.mapper.common;

import net.ddns.endercrypt.intellicommand.mapper.ArgMapper;

public class BooleanMapper implements ArgMapper
{
	@Override
	public Object map(String text)
	{
		if (text.equalsIgnoreCase("true"))
			return true;
		if (text.equalsIgnoreCase("false"))
			return false;

		throw new IllegalArgumentException();
	}
}
