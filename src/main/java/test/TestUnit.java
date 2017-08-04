package test;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import net.ddns.endercrypt.intellicommand.IntelliCommandManager;

import static org.junit.Assert.*;

@RunWith(BlockJUnit4ClassRunner.class)
public class TestUnit
{
	private static Random random;
	private static IntelliCommandManager commandManager;
	private static TestUnitCommands TestUnitCommands;

	private Map<String, Object> bundle;

	@BeforeClass
	public static void beforeClass()
	{
		random = new Random();
		commandManager = new IntelliCommandManager();
		TestUnitCommands = new TestUnitCommands();

		commandManager.registerCommands(TestUnitCommands);
	}

	@Before
	public void before()
	{
		bundle = new HashMap<>();
	}

	@After
	public void after()
	{
		bundle.clear();
		TestUnitCommands.clearResults();
	}

	@Test
	public void canExecuteBasicCommand1()
	{
		commandManager.trigger(bundle, "test empty1");

		Map<String, Object> results = TestUnitCommands.getResult("empty1");
		assertEquals(results.get("empty1"), true);
	}

	@Test
	public void empty1()
	{
		commandManager.trigger(bundle, "test empty1");

		Map<String, Object> results = TestUnitCommands.getResult("empty1");
		assertEquals(results.get("empty1"), true);
	}

	@Test
	public void basic1()
	{
		int number = random.nextInt(1_000_000);

		commandManager.trigger(bundle, "test basic1 " + number);

		Map<String, Object> results = TestUnitCommands.getResult("basic1");
		assertEquals(results.get("basic1"), number);
	}

	@Test
	public void basic2()
	{
		String text = String.valueOf(random.ints(10, 97, 122).mapToObj(i -> (char) i).toArray(Character[]::new));

		commandManager.trigger(bundle, "test basic2 " + text);

		Map<String, Object> results = TestUnitCommands.getResult("basic2");
		assertEquals(results.get("basic2"), text);
	}

	@Test
	public void basic3()
	{
		int number = random.nextInt(1_000_000);
		String text = String.valueOf(random.ints(10, 97, 122).mapToObj(i -> (char) i).toArray(Character[]::new));

		commandManager.trigger(bundle, "test basic3 " + number + " " + text);

		Map<String, Object> results = TestUnitCommands.getResult("basic3_int", "basic3_string");
		assertEquals(results.get("basic3_int"), number);
		assertEquals(results.get("basic3_string"), text);
	}

	@Test
	public void overload1_int()
	{
		int number = random.nextInt(1_000_000);

		commandManager.trigger(bundle, "test overload1 " + number);

		assertEquals(TestUnitCommands.getResult("overload1_int").get("overload1_int"), number);
	}

	@Test
	public void overload1_string()
	{
		String string = String.valueOf(random.ints(10, 97, 122).mapToObj(i -> (char) i).toArray(Character[]::new));

		commandManager.trigger(bundle, "test overload1 " + string);

		assertEquals(TestUnitCommands.getResult("overload1_string").get("overload1_string"), string);
	}

	@Test
	public void overload1_boolean()
	{
		boolean bool = random.nextBoolean();

		commandManager.trigger(bundle, "test overload1 " + bool);

		assertEquals(TestUnitCommands.getResult("overload1_boolean").get("overload1_boolean"), bool);
	}

}
