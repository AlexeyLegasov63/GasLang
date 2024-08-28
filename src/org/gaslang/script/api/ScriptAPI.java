package org.gaslang.script.api;

import org.gaslang.script.*;
import org.gaslang.script.lib.GasPackage;
import org.gaslang.script.lib.NativeFunctionValue;
import org.gaslang.script.lib.ScriptBootstrap;
import org.gaslang.script.parser.lexer.token.TokenType;
import org.gaslang.script.run.GasRuntime;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static org.gaslang.script.NullValue.NIL_VALUE;

public class ScriptAPI
{
	public static final StringValue VERSION = string("1.0");
	
	public static ScriptAPI SCRIPT_API;
	
	public static final StringValue STRUCT_SIGNATURE = string("struct");
	
	public static final BooleanValue TRUE = new BooleanValue(true), FALSE = new BooleanValue(false);
	
	public static final NullValue NULL = NIL_VALUE;
	
	public static NativeFunctionValue function(FunctionBlock block) {
		return new NativeFunctionValue(block);
	}
	public static NativeFunctionValue function(String name, FunctionBlock block) {
		return new NativeFunctionValue(name, block);
	}
	
	public static void struct(TableValue data, String signature) {
		data.set(string("_name"), string(signature));
		data.set(string("_type"), STRUCT_SIGNATURE);
	}
	public static Tuple tuple(Value<?>... value) {
		return new Tuple(new ArrayList<>(Arrays.asList(value)));
	}
	public static TableValue table() {
		return new TableValue(new HashMap<>());
	}
	public static BooleanValue bool(Boolean bool) {
		return new BooleanValue(bool);
	}
	public static StringValue string(String text) {
		return new StringValue(text);
	}
	public static NumberValue number(Number number) {
		return new NumberValue(number);
	}
	public static CharacterValue character(Character character) {
		return new CharacterValue(character);
	}
	public static MaskValue mask(String mask) {
		return new MaskValue(mask, new Annotations());
	}
	public static ArrayValue array(Value<?>... values) {
		HashMap<Integer, Value<?>> array = new HashMap<>();
		for (int i = 0, l = values.length; i < l; i++) {
			array.put(i, values[i]);
		}
		return new ArrayValue(array);
	}
	public static ArrayValue array(Object... values) {
		Value<?>[] vals = new Value[values.length];
		for (int i = 0, l = values.length; i < l; i++) {
			vals[i] = value(values[i]);
		}
		return array(vals);
	}
	public static ArrayValue array(String... values) {
		Value<?>[] vals = new Value[values.length];
		for (int i = 0, l = values.length; i < l; i++) {
			vals[i] = value(values[i]);
		}
		return array(vals);
	}
	public static ArrayValue array(Number... values) {
		Value<?>[] vals = new Value[values.length];
		for (int i = 0, l = values.length; i < l; i++) {
			vals[i] = value(values[i]);
		}
		return array(vals);
	}
	public static ArrayValue array(Object value) {
		HashMap<Integer, Value<?>> array = new HashMap<>();
		for (int i = 0, l = Array.getLength(value); i < l; i++) {
			array.put(i, value(Array.get(value, i)));
		}
		return new ArrayValue(array);
	}
	public static Value<?> value(Object value) {
		if (value instanceof Value) {
			return (Value<?>) value;
		} else if (value == null) {
			return NIL_VALUE;
		} else if (value.getClass().isArray()) {
			return array(value);
		} else if (value instanceof Character) {
			return character((Character) value);
		} else if (value instanceof Number) {
			return number((Number) value);
		} else if (value instanceof String) {
			return string((String) value);
		} else if (value instanceof Boolean) {
			return bool((Boolean) value);
		}
		throw new RuntimeException("Unknown jGasLang type: " + value.getClass().getName());
	}
	
	private final ArrayList<ScriptType> types;
	private final ArrayList<ScriptModule> modules;
	private final ScriptBootstrap gasPackageLoader;
	
	public ScriptAPI() {
		this.modules = new ArrayList<>();
		this.types = new ArrayList<>();
		this.gasPackageLoader = new ScriptBootstrap();

		SCRIPT_API = this;

		registerPackage(("org.gaslang.script.lib.boot"));
	}
	
	public void global(String name, Value<?> value) {
		GasRuntime.GLOBAL_VALUES.put(name, value);
	}
	public void register(ScriptModule module) {
		modules.add(module);
		GasRuntime.GLOBAL_VALUES.put(module.getName(), module);
	}
	public void register(ScriptType type) {
		types.add(type);
		GasRuntime.GLOBAL_VALUES.put(type.getName(), type);
	}
	public void unregister(ScriptModule module) {
		modules.remove(module);
		GasRuntime.GLOBAL_VALUES.remove(module.getName());
	}
	public void unregister(String module) {
		modules.removeIf((child) -> child.getName().equals(module));
		GasRuntime.GLOBAL_VALUES.remove(module);
	}
	
	public void registerPackage(GasPackage gasPackage) {
		gasPackage.getModules().forEach(
				(child) -> SCRIPT_API.register((ScriptModule) child));
		
		gasPackage.getTypes().forEach(
				(child) -> SCRIPT_API.register((ScriptType) child));
	}
	
	public void registerPackage(String packageName) {
		registerPackage(gasPackageLoader.loadPackage(packageName));
	}

	
	public static enum BinaryOperator {
		ADD(TokenType.PLUS, '+'),
		SUB(TokenType.MINUS, '-'),
		DIV(TokenType.SLASH, '/'),
		MUL(TokenType.STAR, '*'),
		MOD(TokenType.PERC, '%');
		
		private TokenType type;
		private char lit;
		
		private BinaryOperator(TokenType t, char l) {
			type = t;
			lit = l;
		}
		
		@Override
		public String toString() {
			return String.valueOf(lit);
		}
	}
	public static enum ConditionalyOperator {
		OR {
			@Override
			public String toString() {
				return "or";
			}
		},
		AND {
			@Override
			public String toString() {
				return "and";
			}
		},
		WEARS {
			@Override
			public String toString() {
				return "wears";
			}
		},
		INSTANCEOF {
			@Override
			public String toString() {
				return "is";
			}
		},
		EQ {
			@Override
			public String toString() {
				return "==";
			}
		},
		NQ {
			@Override
			public String toString() {
				return "!=";
			}
		},
		MR {
			@Override
			public String toString() {
				return ">";
			}
		},
		LS {
			@Override
			public String toString() {
				return "<";
			}
		},
		EQLS {
			@Override
			public String toString() {
				return "<=";
			}
		},
		EQMR {
			@Override
			public String toString() {
				return ">=";
			}
		}
	}
	public static enum UnaryOperator {
		NEG {
			@Override
			public String toString() {
				return "!";
			}
		},
		INV {
			@Override
			public String toString() {
				return "not";
			}
		},
		INC_SUB,	// --x
		PST_SUB,	// x--
		INC_ADD,	// ++x
		PST_ADD		// x++
	}
	public static enum StackSpace {
		GLOBAL,
		LOCAL,
		CURRENT
	}
}
