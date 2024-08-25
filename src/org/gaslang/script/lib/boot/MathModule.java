package org.gaslang.script.lib.boot;

import static org.gaslang.script.api.ScriptAPI.*;

import java.util.Optional;
import java.util.Random;

import org.gaslang.script.*;
import org.gaslang.script.lib.*;
import org.gaslang.script.lib.annotation.GasFunction;
import org.gaslang.script.lib.annotation.GasModule;
import org.gaslang.script.lib.annotation.GasVariable;

@GasModule(name = "math")
public class MathModule
{
	private static final Random RANDOM = new Random();
	
	@GasVariable
	public Double e = Math.E;
	
	@GasVariable
	public Double pi = Math.PI;
	
	@GasVariable
	public Integer huge = Integer.MAX_VALUE;

	@GasFunction
	public NumberValue rand(Optional<Integer> minBase, Optional<Integer> maxBase) {
		if (minBase.isEmpty() && maxBase.isEmpty())
			return number(RANDOM.nextInt());
		else if (maxBase.isEmpty()) 
			return number(RANDOM.nextInt(minBase.get()));
		return number(RANDOM.nextInt(maxBase.get())
				- minBase.get().longValue());
	}
	
	@GasFunction
	public Integer max(Integer x, Integer y) {
		return Math.max(x, y);
	}
	
	@GasFunction
	public Integer min(Integer x, Integer y) {
		return Math.min(x, y);
	}
	
	@GasFunction
	public Integer abs(Double x) {
		return (int) Math.abs(x);
	}
	
	@GasFunction
	public Integer clamp(Integer x, Integer y, Integer z) {
		return Math.min(Math.max(x, y), z);
	}
	
	@GasFunction
	public Integer square(Integer x) {
		return x*x;
	}
}
