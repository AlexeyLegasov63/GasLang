package org.gaslang.script.lib;

import java.util.*;

public class GasPackage
{
	private final ArrayList<NativeObject> jClasses;

	public GasPackage(ArrayList<NativeObject> jClasses) {
		this.jClasses = jClasses;
	}
	
	public List<NativeObject> getModules() {
		return jClasses.stream()
				.filter((NativeObject obj) -> obj instanceof ScriptNativeModule)
				.toList();
	}
	
	public List<NativeObject> getTypes() {
		return jClasses.stream()
				.filter((NativeObject obj) -> obj instanceof ScriptNativeType)
				.toList();
	}
}
