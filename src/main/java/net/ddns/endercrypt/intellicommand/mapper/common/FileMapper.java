package net.ddns.endercrypt.intellicommand.mapper.common;


import java.io.File;

import net.ddns.endercrypt.intellicommand.exception.MapperConversionFailed;
import net.ddns.endercrypt.intellicommand.mapper.ArgMapper;


public class FileMapper implements ArgMapper<File>
{
	@Override
	public File map(String text) throws MapperConversionFailed
	{
		return new File(text);
	}
}
