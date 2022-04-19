var mysql = require('mysql');

var testConn = function(db) {
    var connection = mysql.createConnection({
        host     : 'docker.for.mac.localhost',
        port     : 4000,
        user     : 'root',
        database : db
    });
    connection.connect(function(err) {
        console.log("test conn using db: ", db);
        if (err) {
            console.error('error connecting: ' + err);
            return;
        }
        connection.query('SELECT DATABASE();', function(err, rows) {
            if (err) {
                console.error('query error: ' + err);
                return;
            }
            console.log('using db: ', rows[0]['DATABASE()']);
        });
    });
    connection.query()
}

testConn("");
testConn("test");
testConn("1234.test");
testConn("42.test");
testConn("42");


