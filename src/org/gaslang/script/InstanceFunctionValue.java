package org.gaslang.script;

import org.gaslang.script.run.GasRuntime;

public class InstanceFunctionValue extends FunctionValue
{
	/**
	 * <p1>Instance Function Value - <b>Method</b></p1>
	 * 
	 * Uses in an object instances
	 * 
	 * @since 1.11
	 */
	public InstanceFunctionValue(GasRuntime localStack, String name, Annotations annotations, Arguments arguments,
			Statement statement) {
		super(localStack, name, annotations, arguments, statement);
	
		arguments.getArguments().add(0, new Argument("self"));
	}
}
