package net.ddns.endercrypt.intellicommand.mapper.common;

import net.ddns.endercrypt.intellicommand.mapper.ArgMapper;

public class StringMapper implements ArgMapper
{
	@Override
	public Object map(String text)
	{
		return text;
	}
}
