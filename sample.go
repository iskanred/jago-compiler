package tests

func good3(a int) float64 {
    b := 0.0
    for j := 0; ; j-- {
        if int((b+float64(a))/(b-float64(a))) > j {
            return float64(j)
        }
    }
    return float64(-1) // or -1.0
}

func main() {
    print(bool_to_string(true))
}
