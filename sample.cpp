
#include <iostream>
#include <string>
#include <vector>
#include <tuple>
using namespace std;

struct aa {
 int a;
};

struct bb {
 int b;
};

struct cc {
 int c;
};

aa getA(){
auto r = aa();
r.a = 10;
return r;
;
}
bb updateB(bb in, int newNumber){
in.b = newNumber;
return in;
;
}
void updateC(cc* in, int newNumber){
(*in).c = newNumber;
;
}
int main(){
auto c = cc();
c.c = 10;
cout << (std::to_string((((getA().a) + (7)) + (updateB(bb(), -4).b)) + (c.c))) + (" ") << endl;
updateC(&c, 4);
auto bbb = updateB(bb(), 4);
cout << (((getA().a) + (7)) + (bbb.b)) + (c.c) << endl;
;
}