package org.gaslang.script.lib.boot;

import org.gaslang.script.Tuple;
import org.gaslang.script.ValueType;
import org.gaslang.script.lib.annotation.GasFunction;
import org.gaslang.script.lib.annotation.GasModule;

import java.util.Optional;

@GasModule(name = "string")
public class StringModule
{
	@GasFunction
	public Integer len(String text) {
		return text.length();
	}
	@GasFunction
	public void replace(String text, String target, String value) {
		text.replaceAll(target, value);
	}
	@GasFunction
	public String removeSpaces(String text) {
		return text.replace(" ", "");
	}
	@GasFunction
	public String format(Tuple args) {
		args.valueAtIndexMustBe(0, ValueType.STRING);
		return String.format(
				args.getString(0), 
				args.subTuple(1).getArray()
			);
	}
	
	@GasFunction
	public Number toNumber(String value) {
		try {
			return value.contains(".")
					? Double.parseDouble(value)
					: Integer.parseInt(value);
		} catch (NumberFormatException ignored) {
			return null;
		}
	}

	@GasFunction
	public String toString(Tuple tuple) {
		if (tuple.hasValue("sep")) {
			var separator = tuple.getValue("sep");
			separator.matchValueTypeOrThrow(ValueType.STRING);
			return tuple.concat(separator.asString());
		}
		return tuple.concat();
	}

	@GasFunction
	public char[] toArray(String text) {
		return text.toCharArray();
	}
	@GasFunction
	public Character charAt(String text, Integer index) {
		return text.charAt(index);
	}
	@GasFunction
	public String lower(String text) {
		return text.toLowerCase();
	}
	@GasFunction
	public String upper(String text) {
		return text.toUpperCase();
	}
	@GasFunction
	public String reverse(String text) {
		return new StringBuffer().append(text).reverse().toString();
	}
	@GasFunction
	public String[] split(String text, String separator, Optional<Integer> limit) {
		return limit.isEmpty() 
				? text.split(separator) 
				: text.split(separator, limit.get());
	}
}
