import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.*;
import javax.servlet.http.*;

import com.itseasy.rtf.RTFTemplate;


/**
 * Beispiel für ein Servlet, welches ein RTF Formular mit Werten füllt und an den aufrufenden
 * Browser zurückgibt.
 *
 * @version 0.1.0 	16.08.2004
 * @author 			IT'S EASY
 */
public class TestRtfServlet extends HttpServlet {
    
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        this.doGet(request, response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        byte[] content;
        OutputStream out = response.getOutputStream();
        // Dokument lesen und an Browser zurückgeben 
        try {
            // Pfad auf das Servlet-Basisverzeichnis lesen
            String rtfdoc = getServletContext().getRealPath("formular-demo.rtf");
            // RTF Template lesen und Textmarken füllen 
            RTFTemplate doc = new RTFTemplate(new File(rtfdoc)); 
            // Text für Felder einfügen 
            doc.setBookmarkContent("Nummer", "08/15"); 
            doc.setBookmarkContent("Wert", "4.060,00");
            doc.setBookmarkContent("MWSt", "560,00");
            doc.setBookmarkContent("Wert_Wort", "-- viertausendsechzig --");
            doc.setBookmarkContent("Von", "Mustermann GmbH, München"); 
            doc.setBookmarkContent("Ort_Datum", "München, " + new SimpleDateFormat("dd.MM.yyyy").format(new Date()));
            doc.setBookmarkContent("Fuer", "Tolle Dienste"); 
            // Checkboxen mit Inhalt füllen 
            doc.setBookmarkCheckbox("Bar", true); 
            content = doc.getDocument(); 
            response.setContentType("application/msword");
        } catch (Exception e) {
            // Mögliche Exceptions: BookmarkIsNotCheckboxException, BookmarkNotFoundException 
            response.setContentType("text/html");
            content = e.getMessage().getBytes();
        } 
        response.setContentLength(content.length); 
        out.write(content); 
        out.flush(); 
        out.close(); 
    }

}
