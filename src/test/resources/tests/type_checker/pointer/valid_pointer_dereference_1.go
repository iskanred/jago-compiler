package main

func main(){
    var b = 10
    var a *int = &b
    *a, b = 5, 4
}
