package org.gaslang.script.lib.boot;

import org.gaslang.script.lib.annotation.GasFunction;
import org.gaslang.script.lib.annotation.GasModule;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@GasModule(name = "date")
public class DateModule
{
	@GasFunction
	public String get(Optional<String> format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format.orElse("yyyy-MM-dd HH:mm:ss"));
		Date date = new Date(System.currentTimeMillis());
		return formatter.format(date);
	}
}
