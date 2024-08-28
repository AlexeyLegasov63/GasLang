package org.gaslang.script.lib.boot;

import org.gaslang.script.lib.annotation.GasFunction;
import org.gaslang.script.lib.annotation.GasModule;

@GasModule(name = "char")
public class CharacterModule
{
	@GasFunction
	public Boolean isDigit(Character character) {
		return Character.isDigit(character);
	}
	
	@GasFunction
	public Boolean isLetter(Character character) {
		return Character.isLetter(character);
	}
	
	@GasFunction
	public Boolean isLowerCase(Character character) {
		return Character.isLowerCase(character);
	}
	
	@GasFunction
	public Boolean isUpperCase(Character character) {
		return Character.isUpperCase(character);
	}
	
	@GasFunction
	public Character upper(Character character) {
		return Character.toUpperCase(character);
	}
	
	@GasFunction
	public Character lower(Character character) {
		return Character.toLowerCase(character);
	}
}
