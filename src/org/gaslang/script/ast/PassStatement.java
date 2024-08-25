package org.gaslang.script.ast;

import java.util.*;

import org.gaslang.script.*;
import org.gaslang.script.run.*;
import org.gaslang.script.visitor.Visitor;

public class PassStatement implements Statement
{	
	@Override
	public void execute(GasRuntime runtime) {
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
