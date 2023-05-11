// The code snippet is obtained from "https://gobyexample.com"

package main

import ( /* imports */
	"fmt" // fmt lib
	"strings"// strings lit
)

// Index /**
func Index(vs []string, t string) int {
	for i, v := range vs {
		if v == t { /* equality checking */
			return i
		}
	}
	return -1
}

// Include /**
func Include(vs []string, t string) bool {
	return Index(vs, t) >= 0
}

// Any /**
func Any(vs []string, f func(string) bool) bool {
	for _, v := range vs {
		if f(v) {
/* true */	return true
		}
	}
	return false
}

// All /**
func All(vs []string, f func(string) bool) bool {
	for _, v := range vs {
		if !f(v) {
			return false
		}
	}
	return true
}

func Filter(vs []string, f func(string) bool) []string {
	vsf := make([]string, 0)
	for _, v := range vs {
		if f(v) {
			vsf = append(vsf, v)
		}
	}
	return vsf
}

func Map(vs []string, f func(string) string) []string {
	vsm := make([]string, len(vs))
	for i, v := range vs {
		vsm[i] = f(v)
	}
	return vsm
}

func main() {
	// string-array init
	var strs = []string{"peach", "apple", "pear", "plum"}

	fmt.Println(Index(strs, "pear"))

	fmt.Println(Include(strs, "grape"))

	fmt.Println(Any(strs, func(v string) bool {
		return strings.HasPrefix(v, "p")
	} )	)

	fmt.Println(All(strs, func(v string) bool {
		return strings.HasPrefix(v, "p")
	} )	)

	fmt.Println(Filter(strs, func(v string) bool {
		return strings.Contains(v, "e")
	} )	)

	fmt.Println(Map(strs, strings.ToUpper))
}


// end of source file

