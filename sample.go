package main

type Node struct {
 next *Node
 val  int
}

func main() {
 node1 := &Node{}
 (*node1).val = 1

 node2 := &Node{}
 (*node2).val = 2

 node3 := &Node{}
 (*node3).val = 3

 (*node1).next = node2
 (*node2).next = node3

 print((*((*((*node1).next)).next)).val)
}
