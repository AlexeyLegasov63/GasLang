package org.gaslang.script.api;

import org.gaslang.script.Value;
import org.gaslang.script.ValueType;

import java.util.HashMap;

import static org.gaslang.script.api.ScriptAPI.string;

public class ScriptModule extends Value<HashMap<String, Value<?>>>
{
	private final String name;
	
	public ScriptModule(String name) {
		super(new HashMap<>());
		this.name = name;
		
		set("Name", string(name));
	}
	
	public void set(String fieldName, Value<?> value) {
		jValue().put(fieldName, value);
	}
	
	public String getName() {
		return name;
	}

	@Override
	public Value<?> set(Value<?> arg0, Value<?> arg1) {
		throw new RuntimeException();
	}
	
	@Override
	public String asString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append('{');
		for (String key : jValue().keySet()) {
			buffer.append(String.format(" %s = %s ", key, jValue().get(key)));
		}
		buffer.append('}');
		return buffer.toString();
	}
	
	@Override
	public Value<?> index(Value<?> arg0) {
		arg0.matchValueTypeOrThrow(ValueType.STRING);
		
		return jValue().get(arg0.asString());
	}
}
