package org.gaslang.script.lib.boot;

import static org.gaslang.script.api.ScriptAPI.*;

import java.util.Optional;
import java.util.Random;

import org.gaslang.script.*;
import org.gaslang.script.lib.*;
import org.gaslang.script.lib.annotation.GasFunction;
import org.gaslang.script.lib.annotation.GasModule;
import org.gaslang.script.lib.annotation.GasVariable;
import org.gaslang.script.run.GasRuntime;

@GasModule(name = "system")
public class SystemModule
{
	
	@GasFunction
	public void exit(Integer code) {
		System.exit(code);
	}
	
}
