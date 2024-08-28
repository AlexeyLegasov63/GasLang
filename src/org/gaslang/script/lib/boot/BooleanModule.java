package org.gaslang.script.lib.boot;

import org.gaslang.script.lib.annotation.GasFunction;
import org.gaslang.script.lib.annotation.GasModule;
import org.gaslang.script.lib.annotation.GasVariable;

@GasModule(name = "boolean")
public class BooleanModule
{
	@GasVariable
	public Boolean True = true;
	@GasVariable
	public Boolean False = false;
	
	@GasFunction
	public Integer toNumber(Boolean arg0) {
		return arg0 ? 1 : 0;
	}
	@GasFunction
	public Boolean fromNumber(Number arg0) {
		return arg0.intValue() == 1;
	}
	@GasFunction
	public Boolean fromString(String arg0) {
		return Boolean.parseBoolean(arg0);
	}
}
