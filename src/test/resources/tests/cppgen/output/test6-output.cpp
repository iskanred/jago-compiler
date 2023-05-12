
#include <iostream>
#include <string>
#include <vector>
#include <tuple>
using namespace std;

bool isEven(int a){
return ((a) % (2)) == (0);
;
}
int main(){
vector <int> elements(10);
for (auto i = 0;(i) < (10); i++) { {
if (isEven(i)) { elements[i] = 0;; } else { elements[i] = 1;; } }
; }
;
}