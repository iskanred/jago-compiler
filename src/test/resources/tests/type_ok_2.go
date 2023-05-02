package main

func good2() (b int) {
    for i, j := 0, 10; i < j; {

        b -= ((i*i + 1) - 7) - (j*j*j)

        i++
        j--
    }

    return b
}

func main() {
    print(good2())
}
