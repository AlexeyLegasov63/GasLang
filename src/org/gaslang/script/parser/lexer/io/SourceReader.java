package org.gaslang.script.parser.lexer.io;

import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import java.nio.charset.*;
import java.util.Properties;

public class SourceReader
{
	public char[] getInput(File context) {
		try (final FileInputStream channel = new FileInputStream(context)) {
			int av = channel.available();
			byte[] input = new byte[av];
			while (channel.read(input) != -1); // stop off
			channel.close();
			return new String(input, "UTF-8").toCharArray();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return null;
	}
}
