package main

type name struct{ a int }

type surname struct{ a int }

func main() {
	var b name
	c := name{}
	b = c
}
