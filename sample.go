package main

type aa struct {
	a int
}

type bb struct {
	b int
}

type cc struct {
	c int
}

func getA() aa {
	r := aa{}
	r.a = 10
	return r
}

func updateB(in bb, newNumber int) bb {
	in.b = newNumber
	return in
}

func updateC(in *cc, newNumber int) {
	(*in).c = newNumber
}

func main() {
	c := cc{}
	c.c = 10
	print(toString(getA().a+7+updateB(bb{}, -4).b+c.c) + " ")
	updateC(&c, 4)
	bbb := updateB(bb{}, 4)
	print(getA().a + 7 + bbb.b + c.c)
}
