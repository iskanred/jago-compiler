package main

type a struct {
	a int
	b int
}

type b struct {
	b int
}

type c struct {
	c int
}

func getA() a {
	return a{a: 10}
}

func updateB(in b, newNumber int) b {
	in.b = newNumber
	return in
}

func updateC(in *c, newNumber int) {
	(*in).c = newNumber
}

func main() {
	c{}
	c := c{}
	c.c = 10
	print("")
	updateC(&c, 4)
	bb := updateB(b{}, 4)
	print("")
}
