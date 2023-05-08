package main

func a() [4]int {
    return [4]int{1, 2, 3, 4}
}

func main() {
    b := a()
    b[3] = int(1.4)
}
