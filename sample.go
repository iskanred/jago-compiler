// You can edit this code!
// Click here and start typing.
package main

import "fmt"

func main() {
    a := 5
    pt := &a
    ppt := &pt
    pppt := &ppt
    fmt.Println(***pppt)
}
