package org.gaslang.script;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Arguments
{
	private final ArrayList<Argument> arguments;
	private final boolean isFree;
	
	public Arguments(ArrayList<Argument> arguments, boolean isFree) {
		this.arguments = arguments;
		this.isFree = isFree;
	}
	public Arguments(ArrayList<Argument> arguments) {
		this(arguments, false);
	}
	public Arguments(boolean isFree) {
		this(new ArrayList<>(), isFree);
	}
	public Arguments() {
		this(new ArrayList<>(), false);
	}
	public int getLength() {
		return arguments.size();
	}
	public boolean contains(Object o) {
		return arguments.contains(o);
	}
	public int indexOf(Object o) {
		return arguments.indexOf(o);
	}
	public Argument get(int index) {
		return arguments.get(index);
	}
	public Argument set(int index, Argument element) {
		return arguments.set(index, element);
	}
	public void add(int index, Argument element) {
		arguments.add(index, element);
	}
	public Argument remove(int index) {
		return arguments.remove(index);
	}
	public void clear() {
		arguments.clear();
	}
	public Stream<Argument> stream() {
		return arguments.stream();
	}
	public void forEach(Consumer<? super Argument> action) {
		arguments.forEach(action);
	}
	public boolean removeIf(Predicate<? super Argument> filter) {
		return arguments.removeIf(filter);
	}
	public ArrayList<Argument> getArguments() {
		return arguments;
	}

	public boolean isFree() {
		return isFree;
	}
}
