package command;

import command.algorithm.Algorithm;
import command.correction.Correction;
import command.distance.Distance;
import command.optimization.Optimization;
import reflection.Reflection;

import java.lang.reflect.Constructor;
import java.util.Map;

public enum Command {

	DISTANCE(false, Distance.class),
	CORRECTION(false, Correction.class),
	ALGORITHM(false, Algorithm.class),
	OPTIMIZATION(true, Optimization.class);

	private final boolean repeatable;
	private final Class<?> command;
	private Map<String, Constructor<?>> types;

	Command(boolean repeatable, Class<?> command) {
		this.repeatable = repeatable;
		this.command = command;
	}

	public static Command get(String command) {
		return valueOf(command.toUpperCase());
	}

	@Override
	public String toString() {
		return name().toLowerCase();
	}

	public boolean isRepeatable() {
		return repeatable;
	}

	public Constructor<?> type(String type) {
		return (types == null ? types = Reflection.types("command." + toString(), command) : types).get(type);
	}

}
