package net.ddns.endercrypt.intellicommand.mapper;

import net.ddns.endercrypt.intellicommand.exception.MapperConversionFailed;

public interface ArgMapper
{
	public Object map(String text) throws MapperConversionFailed;
}
