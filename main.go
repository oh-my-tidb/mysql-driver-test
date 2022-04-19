package main

import (
	"database/sql"
	"fmt"

	_ "github.com/go-sql-driver/mysql"
)

func main() {
	testConnString("root@tcp(127.0.0.1:4000)/")
	testConnString("root@tcp(127.0.0.1:4000)/test")
	testConnString("root@tcp(127.0.0.1:4000)/1234.test")
	testConnString("root@tcp(127.0.0.1:4000)/42.test")
	testConnString("root@tcp(127.0.0.1:4000)/42")
}

func testConnString(connStr string) {
	defer fmt.Println("--")
	fmt.Printf("connect with '%s'\n", connStr)
	db, err := sql.Open("mysql", connStr)
	if err != nil {
		fmt.Println("open error:", err)
		return
	}
	defer db.Close()
	rows, err := db.Query("SELECT DATABASE();")
	if err != nil {
		fmt.Println("query error:", err)
		return
	}
	defer rows.Close()
	rows.Next()
	var dbName string
	rows.Scan(&dbName)
	fmt.Println("using db:", dbName)
}
