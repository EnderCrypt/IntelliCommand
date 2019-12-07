package net.ddns.endercrypt.intellicommand.mapper.common;


import java.nio.file.Path;
import java.nio.file.Paths;

import net.ddns.endercrypt.intellicommand.exception.MapperConversionFailed;
import net.ddns.endercrypt.intellicommand.mapper.ArgMapper;


public class PathMapper implements ArgMapper<Path>
{
	@Override
	public Path map(String text) throws MapperConversionFailed
	{
		return Paths.get(text);
	}
}
