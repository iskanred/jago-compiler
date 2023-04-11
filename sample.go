// The code snippet is obtained from "https://gobyexample.com"

package main

import "fmt"

func vals(a int) (int, int) {
	return 3, a
}

func main() {
	a, b := vals(5)
}
