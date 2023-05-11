
#include <iostream>
#include <string>
#include <vector>
#include <tuple>
using namespace std;

vector<int> a(){
return vector <int>{1, 2, 3, 4};
;
}
int main(){
auto b = a();
b[3] = int(1.4);
;
}