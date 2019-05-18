import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//View
public class Msg extends HttpServlet {
DataSource dataSource;  // źrodło danych

    public void init() throws ServletException {
        try {
            Context init = new InitialContext();
            dataSource = (DataSource) init.lookup("java:comp/env/jdbc/ksidb");
        } catch (NamingException exc) {
            System.out.println(exc.getMessage());
            throw new ServletException(
                    "Nie mogę uzyskać źródła java:comp/env/jdbc/ksidb", exc);
        }
    }

    public void sendQueryHTML(HttpServletResponse resp, String query)
    {

        Connection con = null;
        System.out.println("Connecting with data source");
        try {
            synchronized (dataSource) {
                con = dataSource.getConnection();
            }
            System.out.println("Connected with data source");
//            Statement stmt = con.createStatement();
            query = "%" + query + "%";
            PreparedStatement stmt =con.prepareStatement("SELECT * from pozycje WHERE  UPPER(TYTUL) LIKE  UPPER(?)  ");
            stmt.setString(1, query);
            List<String> retList = new ArrayList<String>();
            ResultSet rs = stmt.executeQuery();
            retList.add("<ol>");
            while (rs.next())  {
                String tytul = rs.getString("tytul");
                float cena  = rs.getFloat("cena");
                retList.add("<li>" + tytul + " - cena: " + cena + "</li>");
            }
            System.out.println("Sending query page");
            HTTPView.sendBooks(resp, retList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void sendFullHTML(HttpServletResponse resp)
    {
        Connection con = null;
        System.out.println("Connecting with data source");
        try {
            synchronized (dataSource) {
                con = dataSource.getConnection();
            }
            System.out.println("Connected with data source");
            Statement stmt = con.createStatement();

            List<String> retList = new ArrayList<String>();
            ResultSet rs = stmt.executeQuery("select * from pozycje");
            retList.add("<ol>");
            while (rs.next())  {
                String tytul = rs.getString("tytul");
                float cena  = rs.getFloat("cena");
                retList.add("<li>" + tytul + " - cena: " + cena + "</li>");
            }
            System.out.println("Sending full html page");
        HTTPView.sendBooks(resp, retList);
    } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        System.out.println("Received GET Method");
        String query = null;
        if(req.getQueryString()!=null) {
            query = req.getParameter("ident");
            sendQueryHTML(resp, query);

        }
        else {
            sendFullHTML(resp);
        }
    }

//
//    public void serviceRequest(HttpServletResponse resp)
//            throws IOException
//    {
//        resp.setContentType("text/html; charset=windows-1250");
//        PrintWriter out = resp.getWriter();
//        out.println("<h2>Lista dostępnych książek</h2>");
//        out.println("<hr><form method=\"get\" action=\"http://localhost:8080/msg\">");
//        out.println("id<input type=\"text\" size=\"50\" name=\"ident\"><br>");
//        out.println("<br><input type=\"submit\" value=\"Wyszukaj książki\">");
//        out.println("</form>");
//
//        if(req.getQueryString()!=null) {
//            out.println("Query : " + req.getQueryString());
//            String pnams = req.getParameter("ident");
//
//        }
//        Connection con = null;
//        try {
//            synchronized (dataSource) {
//                con = dataSource.getConnection();
//            }
//            Statement stmt = con.createStatement();
//
//
//            ResultSet rs = stmt.executeQuery("select * from pozycje");
//            out.println("<ol>");
//            while (rs.next())  {
//                String tytul = rs.getString("tytul");
//                float cena  = rs.getFloat("cena");
//                out.println("<li>" + tytul + " - cena: " + cena + "</li>");
//            }
//            rs.close();
//            stmt.close();
//        } catch (Exception exc)  {
//            out.println(exc.getMessage());
//        } finally {
//            try { con.close(); } catch (Exception exc) {}
//        }
//
//        out.close();
//
//
//    }


//    public void doGet(HttpServletRequest request,
//                      HttpServletResponse response)
//            throws ServletException, IOException
//    {
//        serviceRequest(request, response);
//    }
//
//    public void doPost(HttpServletRequest request,
//                       HttpServletResponse response)
//            throws ServletException, IOException
//    {
//        serviceRequest(request, response);
//    }



}
