package main

func saveAbs(a float64) func() float64 {
    if a >= 0 {
        return func() float64 {
            return a
        }
    }
    return func() float64 {
        return -a
    }
}

func main() {
    a := saveAbs(-2.123123)

    b := a()*(-a())*(-a())/(func() float64 { return -a()*a() + float64(int(3)) }()) - (-8)

    print(b)
}
