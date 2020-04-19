package cli;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Optional;

import static org.testng.Assert.*;

public class OptionsTest {

	@DataProvider
	public Object[][] options() {
		return new Object[][] {
				{ "--out" },
				{ "--out=" },
				{ "--out= " },
				{ "out=newick:output.txt" },
		};
	}

	@Test(dataProvider = "options")
	public void put_Invalid_Ignored(String option) {
		Options options = new Options();

		options.put(option);

		assertFalse(options.remove(Option.OUT).isPresent());
	}

	@Test
	public void put_RepeatedOption_Ignored() {
		Options options = new Options();
		options.put("--out=newick:output.txt");
		options.put("--lvs=3");

		options.put("--out=nexus:out.txt");


		assertEquals(options.keys().size(), 2);
		assertTrue(options.keys().contains("--out"));
		assertTrue(options.keys().contains("--lvs"));
		assertEquals(options.remove(Option.OUT).get(), "newick:output.txt");
	}

	@Test
	public void put_ValidOptions_Success() {
		Options options = new Options();

		options.put("-l=7");
		options.put("--matrix=csv:matrix.csv");

		assertEquals(options.keys().size(), 2);
		assertEquals(options.remove(Option.LVS).get(), "7");
		assertEquals(options.remove(Option.MATRIX).get(), "csv:matrix.csv");
	}

	@Test
	public void remove_InvalidNaturalFormat_Default() {
		Options options = new Options();
		options.put("-l=-4");

		String lvs = options.remove(Option.LVS, "2");

		assertEquals(lvs, "2");
	}

	@Test
	public void remove_NoMatch_DefaultValue() {
		Options options = new Options();
		options.put("-t=1");
		options.put("--lts=2");

		String value = options.remove(Option.LVS, "3");

		assertEquals(value, "3");
		assertEquals(options.keys().size(), 2);
		assertTrue(options.keys().contains("-t"));
		assertTrue(options.keys().contains("--lts"));
	}

	@Test
	public void remove_KeyWithDefault_PreviousValue() {
		Options options = new Options();
		options.put("--lvs=5");

		String value = options.remove(Option.LVS, "3");

		assertEquals(value, "5");
		assertTrue(options.keys().isEmpty());
	}

	@Test
	public void remove_AliasWithDefault_PreviousValue() {
		Options options = new Options();
		options.put("-l=7");

		String value = options.remove(Option.LVS, "3");

		assertEquals(value, "7");
		assertTrue(options.keys().isEmpty());
	}

	@Test
	public void remove_InvalidFileFormat_Ignored() {
		Options options = new Options();
		options.put("--matrix=csv-matrix.csv");

		Optional<String> matrix = options.remove(Option.MATRIX);

		assertTrue(matrix.isEmpty());
	}

	@Test
	public void remove_NoMatch_Empty() {
		Options options = new Options();
		options.put("--tee=nexus:tree.txt");

		Optional<String> tree = options.remove(Option.TREE);

		assertTrue(tree.isEmpty());
		assertEquals(options.keys().size(), 1);
		assertTrue(options.keys().contains("--tee"));
	}

	@Test
	public void remove_Key_PreviousValue() {
		Options options = new Options();
		options.put("--lvs=3");

		Optional<String> value = options.remove(Option.LVS);

		assertTrue(value.isPresent());
		assertEquals(value.get(), "3");
		assertTrue(options.keys().isEmpty());
	}

	@Test
	public void remove_Alias_PreviousValue() {
		Options options = new Options();
		options.put("--matrix=csv:matrix.txt");
		options.put("--dataset=mlva:dataset.txt");
		options.put("--tree=newick:tree.txt");

		Optional<String> value = options.remove(Option.DATASET);

		assertTrue(value.isPresent());
		assertEquals(value.get(), "mlva:dataset.txt");
		assertEquals(options.keys().size(), 2);
		assertTrue(options.keys().contains("--matrix"));
		assertTrue(options.keys().contains("--tree"));
	}

}
