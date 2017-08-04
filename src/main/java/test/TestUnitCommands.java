package test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.ddns.endercrypt.intellicommand.CommandPath;
import net.ddns.endercrypt.intellicommand.command.CommandParam;
import net.ddns.endercrypt.intellicommand.command.Priority;

@CommandPath("test")
public class TestUnitCommands
{
	private Map<String, Object> testResults = new HashMap<>();

	public Map<String, Object> getResult(String... keys)
	{
		Set<String> testKeyList = testResults.keySet();
		List<String> keysList = Arrays.asList(keys);

		if (testResults.keySet().containsAll(keysList) && keysList.containsAll(testKeyList))
		{
			return Collections.unmodifiableMap(testResults);
		}
		else
		{
			throw new IllegalArgumentException("test results contained the wrong keys (contained: " + testKeyList + ")");
		}
	}

	public void clearResults()
	{
		testResults.clear();
	}

	// EMPTY //
	@CommandPath("empty1")
	public void commandEmpty1()
	{
		testResults.put("empty1", true);
	}

	// BASIC //

	@CommandPath("basic1 {var1}")
	public void commandBasic1(@CommandParam("var1") int number)
	{
		testResults.put("basic1", number);
	}

	@CommandPath("basic2 {var1}")
	public void commandBasic2(@CommandParam("var1") String text)
	{
		testResults.put("basic2", text);
	}

	@CommandPath("basic3 {var1} {var2}")
	public void commandBasic2(@CommandParam("var1") int num, @CommandParam("var2") String text)
	{
		testResults.put("basic3_int", num);
		testResults.put("basic3_string", text);
	}

	// OVERLOAD //

	@Priority(2)
	@CommandPath("overload1 {var1}")
	public void oveload1_int(@CommandParam("var1") int num)
	{
		testResults.put("overload1_int", num);
	}

	@Priority(1)
	@CommandPath("overload1 {var1}")
	public void overload1_boolean(@CommandParam("var1") boolean bool)
	{
		testResults.put("overload1_boolean", bool);
	}

	@CommandPath("overload1 {var1}")
	public void overload1_string(@CommandParam("var1") String text)
	{
		testResults.put("overload1_string", text);
	}

	// MISSING //

	@CommandPath("missing1 {var1}")
	public void missing_1(@CommandParam("var1") int number)
	{
		testResults.put("missing1", number);
	}

}
