package org.gaslang.script.lib.boot;

import org.gaslang.script.Tuple;
import org.gaslang.script.lib.annotation.GasFunction;
import org.gaslang.script.lib.annotation.GasType;

@GasType(name = "StringBuffer")
public class GasStringBuffer
{
	StringBuffer buffer;
	
	@GasFunction
	public void constructor(Tuple sources) {
		buffer = new StringBuffer(sources.asString());
	}

	@GasFunction
	public Boolean isEmpty() {
		return buffer.isEmpty();
	}

	@GasFunction
	public Integer length() {
		return buffer.length();
	}

	@GasFunction
	public void setLength(Integer newLength) {
		buffer.setLength(newLength);
	}

	@GasFunction
	public Character charAt(Integer index) {
		return buffer.charAt(index);
	}

	@GasFunction
	public void append(Tuple args) {
		buffer.append(args.concat());
	}

	@GasFunction
	public void deleteCharAt(Integer index) {
		buffer.deleteCharAt(index);
	}

	@GasFunction
	public void insert(Integer offset, String str) {
		buffer.insert(offset, str);
	}
	@GasFunction
	public String build() {
		return buffer.toString();
	}
}
