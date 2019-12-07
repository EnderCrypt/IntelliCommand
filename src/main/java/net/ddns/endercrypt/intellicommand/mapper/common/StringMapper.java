package net.ddns.endercrypt.intellicommand.mapper.common;


import net.ddns.endercrypt.intellicommand.exception.MapperConversionFailed;
import net.ddns.endercrypt.intellicommand.mapper.ArgMapper;


public class StringMapper implements ArgMapper<String>
{
	@Override
	public String map(String text) throws MapperConversionFailed
	{
		return text;
	}
}
