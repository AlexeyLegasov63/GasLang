package org.gaslang.script;

import java.util.ArrayList;
import java.util.HashMap;

import static org.gaslang.script.api.ScriptAPI.NULL;

/**
 * 
 */
public class Tuple extends Value<ArrayList<Value<?>>>
{
	public final HashMap<String, Value<?>> named;
	private final ArrayList<Value<?>> values;

	public static Tuple valueOf(HashMap<String, Value<?>> named, Value<?>... values) {
		var tuple = new ArrayList<Value<?>>();
		
		for (int i = 0, l = values.length; i < l; i++) {
			if (values[i] instanceof Tuple) {
				var other = (Tuple)values[i];
				tuple.addAll(other.values);
				named.putAll(other.named);
				continue;
			}
			tuple.add(values[i]);
		}
		
		return new Tuple(tuple, named);
	}
	
	public static Tuple valueOf(Value<?>... values) {
		return valueOf(new HashMap<>(), values);
	}
	
	public Tuple(ArrayList<Value<?>> values) {
		this(values, new HashMap<>());
	}
	
	public Tuple(ArrayList<Value<?>> values, HashMap<String, Value<?>> named) {
		super(values);
		this.values = values;
		this.named = named;
	}

	@Override
	public String asString() {
		return concat();
	}
	
	public String concat(String sep) {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0, l = getSize(); i < l; i++) {
			var value = values.get(i);
			buffer.append(value.asString());
			if (i + 1 == l) break;
			buffer.append(sep);
		}
		return buffer.toString();
	}
	public String concat() {
		return concat("");
	}
	public void remove(int index) {
		values.remove(index);
	}
	public ArrayList<Value<?>> getValues() {
		return values;
	}
	public <T> ArrayList<T> getValuesAs() {
		ArrayList<T> list = new ArrayList<>();
		values.forEach(v -> list.add((T)v));
		return list;
	}
	public boolean hasValue(String name) {
		return named.containsKey(name);
	}
	/**
	 * @since Version 1.1
	 * @param The array length must be equals or more than -1
	 * @returns The tuple's gas values as an array 
	 */
	public Value<?>[] getValuesAsArray(int arrayLength) {
		assert arrayLength >= -1;
		Value<?>[] args = new Value<?>[arrayLength == -1 ? values.size() : arrayLength];
		for (int i = 0, l = args.length; i < l; i++) {
			args[i] = values.size() > i ? values.get(i) : NULL;
		}
		return args;
	}
	/**
	 * @since Version 1.1
	 * @returns The tuple's gas values as an array 
	 */
	public Value<?>[] getValuesAsArray() {
		return getValuesAsArray(-1);
	}
	/**
	 * @since Version 1.1
	 * @param The array length must be equals or more than -1
	 * @returns The tuple's java values as an Object Array 
	 */
	public Object[] getArray(int arrayLength) {
		assert arrayLength >= -1;
		Object[] args = new Object[arrayLength == -1 ? values.size() : arrayLength];
		for (int i = 0, l = args.length; i < l; i++) {
			args[i] = (values.size() > i ? values.get(i) : NULL).jValue();
		}
		return args;
	}
	/**
	 * @since Version 1.1
	 * @returns The tuple's java values as an Object Array 
	 */
	public Object[] getArray() {
		return getArray(-1);
	}
	/**
	 * @since 1.21
	 * @returns Value at index or NullValue if there's no such one
	 */
	public Value<?> getValue(String name) {
		return getValue(name, NULL);
	}
	/**
	 * @since 1.21
	 * @returns Value at index or defaultValue if there's no such one
	 */
	public Value<?> getValue(String name, Value<?> defaultValue) {
		return named.getOrDefault(name, defaultValue);
	}
	/**
	 * @since 1.1
	 * @param Index must be equals or more than 0
	 * @returns Value at index or NullValue if there's no such one
	 */
	public Value<?> getValue(int index) {
		return getValue(index, NULL);
	}
	/**
	 * @since 1.1
	 * @param Index must be equals or more than 0
	 * @returns Value at index or defaultValue if there's no such one
	 */
	public Value<?> getValue(int index, Value<?> defaultValue) {
		assert index >= 0 : "The index must be equals or more than 0";
		if (values.size() <= index) 
			return defaultValue;
		return values.get(index) == null ? defaultValue : values.get(index);
	}
	/**
	 * @since 1.1
	 * @param Index must be equals or more than 0
	 * @returns Value by index or throw RuntimeException if there's no such one
	 */
	public Value<?> getValueOrThrow(int index) {
		assert index >= 0 : "The index must be equals or more than 0";
		if (values.size() <= index || values.get(index) == null) 
			throw new RuntimeException();
		return values.get(index);
	}
	/**
	 * @since 1.1
	 * @param Index must be equals or more than 0
	 * @returns Value by index or throw RuntimeException if there's no such one
	 */
	public boolean hasValue(int index) {
		return values.size() > index && values.get(index) != null;
	}
	
	public boolean valueAtIndexMustBe(int index, ValueType type) {
		Value<?> value = getValue(index);
		return value.matchValueType(type);
	}
	
	public void assertTypes(ValueType type) {
		assertTypes(type, 0, Math.max(getSize()-1, 0));
	}
	
	public void assertTypes(ValueType type, int startIndex) {
		assertTypes(type, startIndex, getSize()-1);
	}
	
	public void assertTypes(ValueType type, int startIndex, int endIndex) {
		assert startIndex >= 0 && startIndex <= endIndex;
		
		for (int i = startIndex; i <= endIndex; i++) {
			Value<?> value = getValue(i);
			
			if (value.isNull() || value.matchValueType(type)) continue;

			throw new RuntimeException();
		}
	}
	
	public int getSize() {
		return values.size();
	}
	
	public Tuple subTuple(int startIndex) {
		return subTuple(startIndex, values.size());
	}
	
	public Tuple subTuple(int startIndex, int endIndex) {
		return new Tuple(new ArrayList<>(
				values.subList(startIndex, endIndex)
			));
	}
	
	public String getString(int index) {
		Value<?> value = getValue(index);
		return value.asString();
	}
	
	public Number getNumber(int index) {
		Value<?> value = getValue(index);
		return value.asNumber();
	}
	
	public Boolean getBoolean(int index) {
		Value<?> value = getValue(index);
		return value.asBoolean();
	}
}
