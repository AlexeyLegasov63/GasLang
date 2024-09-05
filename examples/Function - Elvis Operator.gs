function getFunction(get)
	return get and
		function(text) => println(text)
end

let fn = getFunction(false)

fn?("Test") <! Do not execute if fn is null

fn("Test") <! Not valid because fn is null
