package org.gaslang.script.lib.boot;

import org.gaslang.script.lib.annotation.GasFunction;
import org.gaslang.script.lib.annotation.GasType;

import java.nio.ByteBuffer;
import java.util.Optional;

@GasType(name = "ByteBuffer")
public class GasByteBuffer
{
	ByteBuffer buffer;
	
	@GasFunction
	public void constructor(Optional<Integer> allocate) {
		buffer = ByteBuffer.allocate(allocate.orElse(256));
	}

	@GasFunction
	public Integer capacity() {
		return buffer.capacity();
	}

	@GasFunction
	public Integer position() {
		return buffer.position();
	}

	@GasFunction
	public Character getChar() {
		return buffer.getChar();
	}

	@GasFunction
	public void putChar(Character value) {
		buffer.putChar(value);
	}

	@GasFunction
	public Short getShort() {
		return buffer.getShort();
	}

	@GasFunction
	public void putShort(Short value) {
		buffer.putShort(value);
	}

	@GasFunction
	public Integer getInt() {
		return buffer.getInt();
	}

	@GasFunction
	public void putInt(Integer value) {
		buffer.putInt(value);
	}

	@GasFunction
	public Long getLong() {
		return buffer.getLong();
	}

	@GasFunction
	public void putLong(Long value) {
		buffer.putLong(value);
	}

	@GasFunction
	public Float getFloat() {
		return buffer.getFloat();
	}

	@GasFunction
	public void putFloat(Float value) {
		buffer.putFloat(value);
	}

	@GasFunction
	public Double getDouble() {
		return buffer.getDouble();
	}

	@GasFunction
	public void putDouble(Double value) {
		buffer.putDouble(value);
	}
}
