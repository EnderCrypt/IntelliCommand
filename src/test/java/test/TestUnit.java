package test;

import java.awt.Point;
import java.util.Map;
import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import net.ddns.endercrypt.intellicommand.IntelliCommandManager;
import net.ddns.endercrypt.intellicommand.bundle.Bundle;
import net.ddns.endercrypt.intellicommand.exception.IntelliCommandNotFound;
import net.ddns.endercrypt.intellicommand.exception.MalformedArgumentException;

import static org.junit.Assert.*;

@RunWith(BlockJUnit4ClassRunner.class)
public class TestUnit
{
	private static Random random;
	private static IntelliCommandManager commandManager;
	private static TestUnitCommands testUnitCommands;

	private Bundle bundle;

	@BeforeClass
	public static void beforeClass()
	{
		random = new Random();
		commandManager = new IntelliCommandManager();
		testUnitCommands = new TestUnitCommands();

		commandManager.registerCommands(testUnitCommands);
	}

	@Before
	public void before()
	{
		bundle = new Bundle();
	}

	@After
	public void after()
	{
		testUnitCommands.clearResults();
	}

	@Test
	public void canExecuteBasicCommand1()
	{
		commandManager.trigger(bundle, "test empty1");

		Map<String, Object> results = testUnitCommands.getResult("empty1");
		assertEquals(true, results.get("empty1"));
	}

	// EMPTY //

	@Test
	public void empty1()
	{
		commandManager.trigger(bundle, "test empty1");

		Map<String, Object> results = testUnitCommands.getResult("empty1");
		assertEquals(true, results.get("empty1"));
	}

	// BASIC //

	@Test
	public void basic1()
	{
		int number = random.nextInt(1_000_000);

		commandManager.trigger(bundle, "test basic1 " + number);

		Map<String, Object> results = testUnitCommands.getResult("basic1");
		assertEquals(number, results.get("basic1"));
	}

	@Test
	public void basic2()
	{
		String text = new String(random.ints(10, 97, 122).toArray(), 0, 10);

		commandManager.trigger(bundle, "test basic2 " + text);

		Map<String, Object> results = testUnitCommands.getResult("basic2");
		assertEquals(text, results.get("basic2"));
	}

	@Test
	public void basic3()
	{
		int number = random.nextInt(1_000_000);
		String text = new String(random.ints(10, 97, 122).toArray(), 0, 10);

		commandManager.trigger(bundle, "test basic3 " + number + " " + text);

		Map<String, Object> results = testUnitCommands.getResult("basic3_int", "basic3_string");
		assertEquals(number, results.get("basic3_int"));
		assertEquals(text, results.get("basic3_string"));
	}

	// OVERLOAD //

	@Test
	public void overload1_int()
	{
		int number = random.nextInt(1_000_000);

		commandManager.trigger(bundle, "test overload1 " + number);

		assertEquals(number, testUnitCommands.getResult("overload1_int").get("overload1_int"));
	}

	@Test
	public void overload1_string()
	{
		String text = new String(random.ints(10, 97, 122).toArray(), 0, 10);

		commandManager.trigger(bundle, "test overload1 " + text);

		assertEquals(text, testUnitCommands.getResult("overload1_string").get("overload1_string"));
	}

	@Test
	public void overload1_boolean()
	{
		boolean bool = random.nextBoolean();

		commandManager.trigger(bundle, "test overload1 " + bool);

		assertEquals(bool, testUnitCommands.getResult("overload1_boolean").get("overload1_boolean"));
	}

	// MISSING //

	@Test(expected = IntelliCommandNotFound.class)
	public void missing_command()
	{
		commandManager.trigger(bundle, "test missing1 yukgvdcdthsfaftj");
	}

	@Test(expected = IntelliCommandNotFound.class)
	public void missing_1()
	{
		commandManager.trigger(bundle, "test missing1 test");
	}

	// QUOTES //

	@Test(expected = MalformedArgumentException.class)
	public void quote_1()
	{
		commandManager.trigger(bundle, "test \"quote1");
	}

	@Test
	public void quote_2()
	{
		String text = "hello world";
		commandManager.trigger(bundle, "test quote2 \"" + text + "\"");

		assertEquals(text, testUnitCommands.getResult("quote2").get("quote2"));
	}

	// BUNDLE //

	@Test
	public void bundle_1()
	{
		Point point1 = new Point(1, 2);
		Point point2 = new Point(3, 4);

		bundle.add("item1", point1);
		bundle.add("item2", point2);

		commandManager.trigger(bundle, "test bundle1");

		Map<String, Object> result = testUnitCommands.getResult("point1", "point2");

		assertSame(point1, result.get("point1"));
		assertSame(point2, result.get("point2"));
	}
}
