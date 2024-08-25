

```lua

<! Object mask
mask Vector 

object Vector2(x, y) wears Vector

object Vector3(x, y, z) wears Vector

let vectors = List(Vector2(5,5), Vector3(5,5,5))

foreach vector in vectors do print(vector wears Vector)

```
