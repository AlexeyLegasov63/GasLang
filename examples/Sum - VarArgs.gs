function sum(...)
	let x = 0
	<! key (Ignore), value y
	foreach -, y in {...}
		x += y
	end
	return x
end

println(sum(1,2,3,4,5,6,7,8,9))