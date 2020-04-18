package data;

import logging.Log;

import java.lang.reflect.InvocationTargetException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class File {

	private static final String SEPARATOR = ":";
	private static final String INVALID = "Ignoring invalid %s '%s'";
	private static final String FILE = "file";
	private static final String FORMAT = "format";
	private static final String PATH = "path";

	private final Object processor;
	private final Path path;

	public File(Object processor, Path path) {
		this.processor = processor;
		this.path = path;
	}

	public static Optional<File> get(String file, Processor processor) {
		String[] values = file.split(SEPARATOR, 2);
		if (values.length == 1 || values[0].isBlank() || values[1].isBlank()) {
			Log.warning(INVALID, FILE, file);
			return Optional.empty();
		}
		String format = values[0], path = values[1];
		if (!processor.hasType(format)) {
			Log.warning(INVALID, FORMAT, format);
			return Optional.empty();
		}
		try {
			return Optional.of(new File(processor.getType(format).newInstance(), Paths.get(path)));
		} catch (InvalidPathException e) {
			Log.warning(INVALID, PATH, path);
			return Optional.empty();
		} catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
			Log.throwing("File", "get", e);
			return Optional.empty();
		}
	}

	public Object getProcessor() {
		return processor;
	}

	public Path getPath() {
		return path;
	}

}