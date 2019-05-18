import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class HTTPView {
    public static void sendBooks(HttpServletResponse resp, List<String> books ){
        resp.setContentType("text/html; charset=windows-1250");
        PrintWriter out = null;
        try {
            out = resp.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.println("<h2>Lista dostępnych książek</h2>");
        out.println("<hr><form method=\"get\" action=\"http://localhost:8080/msg\">");
        out.println("id<input type=\"text\" size=\"50\" name=\"ident\"><br>");
        out.println("<br><input type=\"submit\" value=\"Wyszukaj książki\">");
        out.println("</form>");

        for(String book: books){
            out.println(book);
        }

    }
}
