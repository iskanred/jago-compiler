package main

func good2() int {
	for i, j := 0, 10; i < j; {

		var b = ((i*i + 1) - 7) - (j * j * j)

		i++
		j--
	}

	return 0
}

func main() {
	good2()
}
