package data.matrix;

import logging.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class CSV implements IMatrixProcessor {

	@Override
	public Matrix parse(Stream<String> data) {
		Iterator<String[]> iterator = data.map(line -> line.split("\\t")).iterator();
		List<List<Double>> matrix = new ArrayList<>();
		int counter = 1;
		while (iterator.hasNext()) {
			String[] next = iterator.next();
			if (Arrays.stream(next).allMatch(n -> n.matches("^(\\d*(\\.\\d+)?)$")) &&
				(matrix.isEmpty() || (next.length == matrix.get(0).size() && matrix.size() < matrix.get(0).size())))
				matrix.add(Arrays.stream(next).map(Double::parseDouble).collect(Collectors.toList()));
			else
				Log.warning(IGNORING_ROW, counter);
			counter++;
		}
		if (!matrix.isEmpty()) {
			for (int i = matrix.get(0).size(); i > matrix.size(); i--) {
				int column = i;
				Log.warning(IGNORING_COLUMN, column);
				matrix.forEach(row -> row.remove(column - 1));
			}
		}
		return new Matrix(matrix);
	}

	@Override
	public String format(Matrix matrix) {
		StringBuilder data = new StringBuilder();
		for (int i = 0; i < matrix.size(); i++) {
			for (int j = 0; j < matrix.size(); j++)
				data.append(matrix.get(i, j)).append('\t');
			data.replace(data.length() - 1, data.length(), "\n");
		}
		if (data.length() > 0)
			data.deleteCharAt(data.length() - 1);
		return data.toString();
	}

}