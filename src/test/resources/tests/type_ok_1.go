package name;
func find(a int) (string) {
    //ints := [10]int{0, 1, 2, 3, 4, 5, 6, 7, 8, 9}
    strs := [10]string{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};

    if (a >= 0) && (a <= 9) {
        return strs[a];
    };

    return "x";
};
func good1(a int) (string) {
    var b string;
    for a > 0 {
        b += find(a % 10);
        a /= 10;
    };
    return b;
};
func main() {
    good1(100);
};
