package name

func good1() (string, int) {
    return "123", 23
}

func main() {
    var a string
    var b int
    a, b = good1()
}
