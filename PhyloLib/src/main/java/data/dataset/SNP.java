package data.dataset;

import logging.Log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public final class SNP implements IDatasetProcessor {

	@Override
	public Dataset parse(Stream<String> data) {
		Iterator<String[]> iterator = data.map(line -> line.split("\\t", 2)).iterator();
		List<Profile> profiles = new ArrayList<>();
		int counter = 1;
		while (iterator.hasNext()) {
			String[] next = iterator.next();
			if (next.length == 2 && next[1].matches("^[0 1]+$") && (profiles.isEmpty() || next[1].length() == profiles.get(0).length()))
				profiles.add(new Profile(next[0], next[1]));
			else
				Log.warning(INVALID_PROFILE, counter);
			counter++;
		}
		return profiles.isEmpty() ? null : new Dataset(profiles);
	}

}
