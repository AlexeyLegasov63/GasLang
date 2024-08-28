
<! Just non-prime object
object YourType(x, y)

<! Methods
function YourType:sum() => self.x + self.y

function YourType:toString() => string.format("x: %s, y: %s", self.x, self.y)

<! Creating a new instance

let yourInstance = YourType(7, 3)

print(yourInstance, yourInstance.sum())

