package net.ddns.endercrypt.intellicommand.mapper.common;

import net.ddns.endercrypt.intellicommand.mapper.ArgMapper;

public class IntegerMapper implements ArgMapper
{
	@Override
	public Object map(String text)
	{
		try
		{
			return Integer.valueOf(text);
		}
		catch (NumberFormatException e)
		{
			throw new IllegalArgumentException();
		}
	}
}
