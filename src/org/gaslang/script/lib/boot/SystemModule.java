package org.gaslang.script.lib.boot;

import org.gaslang.script.lib.annotation.GasFunction;
import org.gaslang.script.lib.annotation.GasModule;

@GasModule(name = "system")
public class SystemModule
{
	
	@GasFunction
	public void exit(Integer code) {
		System.exit(code);
	}


	@GasFunction
	public Long time() {
		return System.currentTimeMillis();
	}

}
