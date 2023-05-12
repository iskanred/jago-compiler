
#include <iostream>
#include <string>
#include <vector>
#include <tuple>
using namespace std;

int main(){
auto v1 = 1;
auto ptr = &v1;
auto pptr = &ptr;
auto ppptr = &pptr;
auto v2 = (*(*(*ppptr)));
;
}