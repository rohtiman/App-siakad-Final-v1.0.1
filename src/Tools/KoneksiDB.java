package Tools;

import java.sql.*;
import javax.swing.JOptionPane;

public class KoneksiDB {
    public Connection getConnection() throws SQLException{//method koneksi DB
        Connection cnn;//deklarasi class Connection
        try{
            //String server = "jdbc:mysql://192.168.1.51/dbsiakadv1_132300109";//
            String server = "jdbc:mysql://localhost/dbsiakadv1_161530040";//nama server
            String drever = "com.mysql.jdbc.Driver";//nama driver koneksi DB MySQL
            Class.forName(drever);//eksekusi driver koneksi DB
            //cnn = DriverManager.getConnection(server, "d2rpl", "12345678");//
            cnn = DriverManager.getConnection(server, "root", "");//inisialisasi variabel cnn
            return cnn;
        }catch(SQLException | ClassNotFoundException se){//funsi catch:menampilkan error
            JOptionPane.showMessageDialog(null, "Error koneksi database : "+se);
            return null;
        }
    }
}
