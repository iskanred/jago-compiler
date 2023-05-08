package main

func main(){
    var b = "hello"
    var a = &b
    *a, b = 5, "world"
}
