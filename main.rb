#!/usr/bin/ruby

require 'mysql'

def test_conn(db)
    begin
        puts "testing with db: #{db}"
        con = Mysql.new 'localhost', 'root', '', db, 4000
        puts conn.query 'SELECT DATABASE()'
        puts rs.fetch_row
    
    rescue Mysql::Error => e
        puts e.error

    ensure
        conn.close if conn
    end
end

test_conn '42:test'

    