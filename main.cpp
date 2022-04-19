#include <iostream>
#include <string>
using namespace std;

#include "mysql/include/jdbc/mysql_driver.h"
#include "mysql/include/jdbc/mysql_connection.h"

void testConn(string db) {
    sql::mysql::MySQL_Driver *driver = sql::mysql::get_mysql_driver_instance();
    string host = "tcp://127.0.0.1:3306/"+db;
    sql::Connection *conn = driver->connect(host.c_str(), "root", "");
    
    delete conn;
}

int main() {
    return 0;
}