// You can edit this code!
// Click here and start typing.
package main

import "fmt"

func main() {
	var ff1 func(int, int) bool
	ff1 = func(a, b int) bool {
		return a+b == 2
	}
	ff1(1, 2)

	func(num int) {
		fmt.Println(num)
	}(1)

	ff2 := func(a, b int) bool {
		return a+b == 2

	}
	ff2(1, 2)
}
