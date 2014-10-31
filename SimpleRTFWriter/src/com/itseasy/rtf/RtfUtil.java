package com.itseasy.rtf;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * RtfUtil - Hilfsklasse, die nützliche Funktionen zur RTF Bearbeitung bereithält.
 *
 * @version 0.1.0 	22.05.2004
 * @author 			IT'S EASY
 */
public class RtfUtil {
    
//    private static final String SPECIAL_CHARACTER = "'-*:\\_{|}~";

    /**
     * Wandelt ein Datum in RTF Format um. Dies ist z.B. im Infoteil eines RTF Dokumentes notwendig.
     * 
     * @param datum	- umzuwandelndes Datum
     * @return RTF-String, der dieses Datum repräsentiert
     */
    public static String convertDateToRTFString(Date datum) {
        SimpleDateFormat formater = new SimpleDateFormat("'\\yr'yyyy'\\mo'MM'\\dy'dd'\\hr'HH'\\min'mm'\\sec'ss");
        return formater.format(datum);
    }
    
//    /**
//     * Ersetzt in einem String die Sonderzeichen, damit sie in das RTF Dokument eingetragen werden können
//     * 
//     * @param text
//     * @return
//     */
//    public static String getRTFStringX(String text) {
//        String workstr = text;
//        // Den übergebenen String nach besonderen Zeichen absuchen
//        for (int i=0; i<SPECIAL_CHARACTER.length(); i++) {
//            int begin = 0;
//            while ((begin = workstr.indexOf(SPECIAL_CHARACTER.charAt(i), begin)) >= 0) {
//                workstr = workstr.substring(0, begin) + "\\" + workstr.substring(begin);
//                // Zeiger um zwei weiter
//                begin = begin + 2;
//            }
//        }
//        
//        return workstr;
//    }
    
    /**
     * Ersetzt in einem String die Sonderzeichen, damit sie in das RTF Dokument eingetragen werden können.
     * Dies ist wichtig, da sonst Textinhalte unter Umständen als RTF-Steuerzeichen versehentlich angesehen
     * werden können.
     * 
     * @param text - umzuwandelnder String
     * @return RTF-konformer String
     */
    public static String getRTFString(String text) {
        StringBuffer workstr = new StringBuffer();
        // Den übergebenen String nach besonderen Zeichen absuchen
        for (int i=0; i<text.length(); i++) {
            char ch = text.charAt(i);
            switch(ch) {
//            	case '_': workstr.append("\\_"); break;
//            	case '\'': workstr.append("\\'"); break;
//            	case '*': workstr.append("\\*"); break;
//            	case ':': workstr.append("\\:"); break;
            	case '\\': workstr.append("\\\\"); break;
            	case '{': workstr.append("\\{"); break;
            	case '}': workstr.append("\\}"); break;
//            	case '|': workstr.append("\\|"); break;
//            	case '~': workstr.append("\\~"); break;
            	case 0x09: workstr.append("{\\tab}"); break;
            	case 0x10: workstr.append("{\\line}"); break;
            	default:  
            	    if (ch < 128)
            	        workstr.append(ch);
            	    else
            	        workstr.append("\\'" + Integer.toHexString(ch));
            }
        }
        return workstr.toString();
    }
  
    /**
     * Wandelt Millimeter in "Tiwps" um. Ein Twips ist ein 1/20 Punkt oder 1/1440 Zoll.
     * 
     * @param mm - Millimeter
     * @return	Twips
     */
    public static int getTwipFromMillimeter(double mm) {
        return (int)(mm / 25.4 * 1440 + 0.5);
    }
    
    /**
     * Wandelt "Tiwps" in Millimeter um. Ein Twips ist ein 1/20 Punkt oder 1/1440 Zoll.
     * 
     * @param twips - Twips
     * @return Millimeter
     */
    public static double getMillimeterFromTwip(int twips) {
        return (twips * 25.4 /1440);
    }

    /**
     * Liest einen Integer-Wert aus einem String heraus und gibt diesen als <b>int</b> zurück. Wird 
     * keine Zahl gefunden, so wird "-1" zurückgegeben. 
     *  
     * @param buf		- StringBuffer, aus dem die Zahl gelesen werden soll
     * @param startpos  - Startposition (1. Zeichen der Zahl)
     * @return gefundener Integerwert
     */
    public static int getIntegerFromText(String buf, int startpos) {
        int result = -1;
        int len = 0;
        // Wert lesen
        while (Character.isDigit(buf.charAt(startpos + len))) {
            len++;
        }
        try {
            // Wert von String in int umwandeln
            result = Integer.parseInt(buf.substring(startpos, startpos + len));
        } catch (NumberFormatException e) {
            // Fehler bei Umwandlung
            result = -1;
        }
        
        return result;
    }
    
    /**
     * Sucht das Ende eines RTF-Tags (Geschweifte Klammer). <br>
     * "begin" gibt die Position des öffnenden Tags an, innerhalb von "text". 
     * Als Ergebnis wird die Position des entsprechenden Endetags zurückgegeben.
     *  
     * @param text		Text, in dem gesucht werden soll
     * @param begin		Position des öffnenden Tags
     * @return			Position des schließenden Tags
     */
    public static int getPositionOfTagEnd(String text, int begin) {
        // Ende suchen
        int end = begin + 1;
        int klammercount = 1;
        while (klammercount > 0) {
            if (text.charAt(end) == '{')
                klammercount++;
            else if (text.charAt(end) == '}') klammercount--;
            end++;
        }
        return end;
    }
}
