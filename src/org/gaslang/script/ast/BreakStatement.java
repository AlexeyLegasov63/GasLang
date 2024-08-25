package org.gaslang.script.ast;

import java.util.*;

import org.gaslang.script.*;
import org.gaslang.script.run.*;
import org.gaslang.script.visitor.Visitor;

public class BreakStatement extends RuntimeException implements Statement
{
	private static final long serialVersionUID = 1L;
	
	@Override
	public void execute(GasRuntime runtime) {
		throw this;
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
