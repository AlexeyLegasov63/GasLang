package org.gaslang.script;

import org.gaslang.script.visitor.Visitor;

public interface Visitable
{
	void accept(Visitor visitor);
}
