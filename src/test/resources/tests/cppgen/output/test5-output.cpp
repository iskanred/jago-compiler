
#include <iostream>
#include <string>
#include <vector>
#include <tuple>
using namespace std;

struct name {
 int a;
};

struct dog {
 string b;
};

int main(){
auto b = 10;
auto a = &b;
(*a) = 5;b = 4;
;
}