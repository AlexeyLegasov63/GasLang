package org.gaslang.script.lib.boot;

import java.io.*;
import java.util.*;

import org.gaslang.script.lib.*;
import org.gaslang.script.lib.annotation.GasFunction;
import org.gaslang.script.lib.annotation.GasType;

@GasType
public class InputScanner
{
	public Scanner value;
	
	@GasFunction
	public void constructor(Optional<InputStream> inputStream) {
		value = new Scanner(inputStream.orElse(System.in));
	}

	@GasFunction
	public Boolean hasNext() {
		return value.hasNext();
	}

	@GasFunction
	public String next() {
		return value.next();
	}
}
