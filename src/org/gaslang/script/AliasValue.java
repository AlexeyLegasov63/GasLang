package org.gaslang.script;

public class AliasValue extends Value<String>
{
	
	private AliasValue(String realName) {
		super(realName, ValueType.NONE);
	}
	
	
}
