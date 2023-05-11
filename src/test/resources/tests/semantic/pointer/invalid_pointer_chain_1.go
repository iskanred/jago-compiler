package main

func main() {
	v1 := 1
	ptr := &v1
	pptr := &ptr
	ppptr := &pptr
	var v2 int = **ppptr
}
