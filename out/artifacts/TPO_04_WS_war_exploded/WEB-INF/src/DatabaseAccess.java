import java.sql.*;

public class DatabaseAccess {
    public static final String CSdriver = "org.apache.derby.jdbc.EmbeddedDriver";
    Connection myCon;

    public DatabaseAccess() throws SQLException, ClassNotFoundException {
        Class.forName(CSdriver);
        myCon = DriverManager.getConnection("jdbc:derby:C:\\DerbyDbs\\ksidb;create=false\";create=false");
        System.out.println("Connected to database ksidb");
    }
    public String selectAllBooks(){
        String sel = "select * from pozycje";
        String full = "";
        try  {
            Statement stmt = myCon.createStatement();
            ResultSet rs = stmt.executeQuery(sel);
            while (rs.next())  {
                String tytul = rs.getString("tytul");
                float cena  = rs.getFloat("cena");
                full +="<li>" + tytul + ", " + cena + " " + "</li>" + "\n";
            }
            rs.close();
            stmt.close();
        } catch (SQLException exc)  {
            System.out.println(exc.getMessage());
        }

        return full;
    }
    public String selectFindBooks(String sqlCommand){



        return null;
    }

}
