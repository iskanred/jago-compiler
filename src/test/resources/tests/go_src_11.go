 /// The code snippet is obtained from 'https://groups.google.com/g/golang-nuts/c/C-xX-I6G_Fs' ///

package main

var a string
var done bool

func setup() {
	a = "hello, world"
	done = true
}

func main() {
	go setup()
	for !done {
	}
	print(a)
}