import com.mysql.jdbc.Driver;
import java.sql.*;

class Main {
    public static void main(String args[]) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        Main t = new Main();
        t.run();
    }

    void run() {
        testConn("jdbc:mysql://docker.for.mac.localhost:4000/");
        testConn("jdbc:mysql://docker.for.mac.localhost:4000/test");
        testConn("jdbc:mysql://docker.for.mac.localhost:4000/1234.test");
        testConn("jdbc:mysql://docker.for.mac.localhost:4000/42.test");
        testConn("jdbc:mysql://docker.for.mac.localhost:4000/42");
    }

    void testConn(String s) {
        System.out.println("conn using: " + s);
        try {
            Connection conn = DriverManager.getConnection(s+"?user=root");
            Statement stmt = conn.createStatement();
            try {
                ResultSet rs = stmt.executeQuery("SELECT DATABASE();");
                if (rs.next()) {
                    System.out.println("using db:" + rs.getString(1));
                }
            } catch (Exception e) {
                System.out.println("exec error:" + e);
            }
            
        } catch (Exception e) {
            System.out.println("connect error:" + e);
        }
        System.out.println("--");
    }
}