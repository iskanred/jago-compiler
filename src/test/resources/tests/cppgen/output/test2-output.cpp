
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
a2 = 3;b2 = 4;;
auto c1 = get<0>(test1());
auto d1 = get<1>(test1());
auto tuple_24be90155bff492a89d7f6cfe7b76d63 = test1();
auto c2 = get<0>(tuple_24be90155bff492a89d7f6cfe7b76d63);auto d2 = get<1>(tuple_24be90155bff492a89d7f6cfe7b76d63);
tie(c2, d2) = test1();
auto e1 = test2();
auto f1 = 3;
auto e2 = test2();auto f2 = 3;
e2 = test2();f2 = 3;;
;
}
