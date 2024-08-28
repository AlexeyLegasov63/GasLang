package org.gaslang.script.lib.boot;

import org.gaslang.script.lib.annotation.GasFunction;
import org.gaslang.script.lib.annotation.GasType;

import java.io.InputStream;
import java.util.Optional;
import java.util.Scanner;

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
