package data.dataset;

import java.util.Arrays;
import java.util.stream.Stream;

public class MLVAFormatter implements IDatasetFormatter {

	@Override
	public Dataset parse(Stream<String> data) {
		return new Dataset(data.map(line -> line.split("\\t"))
				.map(values -> new Profile(values[0], Arrays.stream(Arrays.copyOfRange(values, 1, values.length))
						.map(value -> value.isBlank() ? null : Integer.valueOf(value))
						.toArray(Integer[]::new)))
				.toArray(Profile[]::new));
	}

}
