#!/opt/homebrew/bin/python3
# -*- coding: UTF-8 -*-

import MySQLdb

def testConn( db ):
    print("--")
    print("test using db = {}".format(db))

    try:
        conn = MySQLdb.connect( host="127.0.0.1", user="root", port=4000, db=db )
    
    except MySQLdb.Error as e:
        print("connect failed, err: {}".format(e))
        return
    
    try:
        cursor = conn.cursor()
        cursor.execute("SELECT DATABASE();")
        m = cursor.fetchone()
        print("using database: ", m[0])
    except err:
        print("query failed: {}".format(err.msg))
    
    cursor.close()
    conn.close()

testConn('')
testConn('test')
testConn('1234.test')
testConn('42.test')
testConn('42')