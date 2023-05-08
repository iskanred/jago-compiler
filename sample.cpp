
#include <iostream>
#include <string>
#include <vector>
#include <tuple>
using namespace std;

tuple<string, int> good1(){
return make_tuple("123", 23);
;
}
int main(){
auto a = get<0>(good1());
auto b = get<1>(good1());
;
}