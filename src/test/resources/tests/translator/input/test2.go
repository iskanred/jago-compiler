package input

func test1() (int, bool) {
    return 42, true
}

func test2() string {
    return "hello there"
}

func main() {
    var a1, b1 = 1, 2
    a2, b2 := 1, 2
    a2, b2 = 3, 4

    var c1, d1 = test1()
    c2, d2 := test1()
    c2, d2 = test1()

    var e1, f1 = test2(), 3
    e2, f2 := test2(), 3
    e2, f2 = test2(), 3
}
