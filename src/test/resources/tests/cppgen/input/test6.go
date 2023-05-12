package main

func isEven(a int) bool {
    return a%2 == 0
}

func main() {
    var elements [10]int
    for i := 0; i < 10; i++ {
        if isEven(i) {
            elements[i] = 0
        } else {
            elements[i] = 1
        }
    }
}
