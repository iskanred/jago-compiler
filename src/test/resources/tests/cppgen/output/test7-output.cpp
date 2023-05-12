
#include <iostream>
#include <string>
#include <vector>
#include <tuple>
using namespace std;

struct Node {
 Node* next;
 int val;
};

int main(){
auto node1 = new Node();
(*node1).val = 1;
auto node2 = new Node();
(*node2).val = 2;
auto node3 = new Node();
(*node3).val = 3;
(*node1).next = node2;
(*node2).next = node3;
cout << (*node1).val << endl;
;
}