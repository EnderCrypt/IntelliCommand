package net.ddns.endercrypt.intellicommand.mapper;

import net.ddns.endercrypt.intellicommand.exception.MapperConversionFailed;

/**
 * @author EnderCrypt
 * 
 * interface for creating your own argmapper
 * the map method should attempt to convert to the correct
 * java class (the class type passed into registerMapper)
 * 
 * if the class is unable to convert due to invalid text, a MapperConversionFailed should be thrown
 */
public interface ArgMapper
{
	/**
	 * mapping method for converting a text string into a java object
	 * @param variable text to be converted
	 * @return
	 * @throws MapperConversionFailed if string cant be converted
	 */
	public Object map(String text) throws MapperConversionFailed;
}
