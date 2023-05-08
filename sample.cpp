
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
auto tuple_5e3e6a95fb354a14bdf5f1512d72f494 = good1();
auto a = get<0>(tuple_5e3e6a95fb354a14bdf5f1512d72f494);auto b = get<1>(tuple_5e3e6a95fb354a14bdf5f1512d72f494);
;
}