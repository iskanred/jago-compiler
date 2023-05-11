
#include <iostream>
#include <string>
#include <vector>
#include <tuple>
using namespace std;

tuple<int, bool> test1(){
return make_tuple(42, true);
;
}
string test2(){
return "hello there";
;
}
int main(){
auto a1 = 1;
auto b1 = 2;
auto a2 = 1;auto b2 = 2;
a2 = 3;b2 = 4;
auto c1 = get<0>(test1());
auto d1 = get<1>(test1());
auto tuple_957e1159edc74bb79b1629dea564bd3b = test1();
auto c2 = get<0>(tuple_957e1159edc74bb79b1629dea564bd3b);auto d2 = get<1>(tuple_957e1159edc74bb79b1629dea564bd3b);
tie(c2, d2) = test1();
auto e1 = test2();
auto f1 = 3;
auto e2 = test2();auto f2 = 3;
e2 = test2();f2 = 3;
;
}