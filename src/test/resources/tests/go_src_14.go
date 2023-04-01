// The code snippet is obtained from 'https://gobyexample.com'

package main

import ( /* imports */
	"fmt" // fmt lib
	"strings" )

// Index2 /**
func Index2(vs []string,

	t string) int {
	for i, v := range vs {
		if v == t { /* equality checking */
			return i
		}
	}
	return -1
}

// Include2 /**
func Include2(vs []string, t string) bool {
	return Index(vs, t) >=/* comment */0
}

// Any2 /**
func Any2(vs []string, f func(string) bool) bool {
	for _, v := range vs {
		if f(v) {
			/* true */	return true
		}
	}
	return false
}

// All2 /**
func All2(vs []string, f func(string) bool
) bool {
	for _, v := range vs {
		if !f(v) {
			return false
		}
	}
	return true
}

func Filter2(vs []string, f func(string) bool) []string {
	vsf := make([]string, 0)
	for _, v := range vs {
		if f(v) {
			vsf = append(vsf, v)
		}
	}
	return vsf
}

func Map2(vs []string, f func(string) string) []string {
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

