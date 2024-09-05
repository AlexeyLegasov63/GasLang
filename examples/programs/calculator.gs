let input_scanner = InputScanner()

let raw_input, position, eof

function parse(input_string)
	raw_input = string.toArray(input_string)
	eof = string.len(input_string)
	position = 0
	println(additive())
end

function additive()
	let value = multiplicative()

	cycle
		continue if match('+') and value += multiplicative()

		continue if match('-') and value -= multiplicative()
	end

	return value
end

function multiplicative()
	let value = unary()

	cycle
		continue if match('*') and value *= unary()

		continue if match('/') and value /= unary()

		continue if match('%') and value %= unary()
	end

	return value
end

function unary()
	let negate, value = match('-'), primary()
	return negate and -value or value
end

function primary()
	let current = peek()

	if match('(')
		current = additive()
		consume(')')
		return current
	end

	let buffer = StringBuffer()

	while eof > position and (char.isDigit(current) or char == '.')
		buffer.append(current)
		current = next()
	end

	return string.toNumber(buffer.build())
end

function consume(c)
	if peek() != c
		debug.printStackTrace(3)
		system.exit(-1)
	end

	position += 1
end

function match(c)
	if eof <= position or peek() != c then return false
	position += 1
	return true
end

function peek() => raw_input[position]

function next() => raw_input[position += 1]

export function()
	while input_scanner.hasNext()
		do parse(input_scanner.next())
end as start

