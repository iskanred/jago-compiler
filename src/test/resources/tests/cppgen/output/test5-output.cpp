
#include <iostream>
#include <string>
#include <vector>
#include <tuple>
using namespace std;

int main(){
auto b = 10;
auto a = &b;
(*a) = 5;b = 4;
;
}