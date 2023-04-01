// The code snippet is obtained from "https://gobyexample.com"
// Incorrect version

package main

hype Plant struct { // syntax incorrect line
	XMLName xml.Name `xml:"plant"`
	Id      int      `xml:"id,attr"`
	Name    string   `xml:"name"`
	Origin  []string `xml:"origin"`
}