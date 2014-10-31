package com.itseasy.rtf.text;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.itseasy.rtf.RtfUtil;


/**
 * InfoGroup - Beinhaltet Informationen des RTF Dokumentes über den Autor, Schlüsselwörter, Kommentare
 *  
 * 1.0.0	22.05.2004	S. Finkenzeller		First Version
 * 
 * @version 0.6.0 	22.05.2004
 * @author Stefan Finkenzeller
 */
public class InfoGroup {
    /**
     * Dokumententitel
     */
    public static final String INFO_TITLE = "title";
    /**
     * Betreff des Dokuments
     */
    public static final String INFO_SUBJECT = "subject";
    /**
     * Name des Dokumentenautor
     */
    public static final String INFO_AUTHOR = "author";
    /**
     * Name des Vorgesetzten des Autors
     */
    public static final String INFO_MANAGER = "manager";
    /**
     * Name des letzten Dokumentenbearbeiters
     */
    public static final String INFO_OPERATOR = "operator";
    /**
     * Kategorie in das dieses Dokument eingeordent werden kann
     */
    public static final String INFO_CATEGORY = "category";
    /**
     * Schluesselwoerter, die das Dokument beschreiben
     */
    public static final String INFO_KEYWORDS = "keywords";
    /**
     * Allgemeiner Kommentar
     */
    public static final String INFO_COMMENT = "comment";
    /**
     * Kommentar zum Dokument, der in den "Summary Infos" und den "Eigenschaften" des Dokumentes angezeigt wird  
     */
    public static final String INFO_DOCCOMM = "doccomm";
    /**
     * Basis-URL, auf die sich alle im Dokument befindlichen Hyperlinks beziehen
     */
    public static final String INFO_HLINKBASE = "hlinkbase";
    /**
     * Datum, an dem das Dokument erstellt wurde
     */
    public static final String INFO_CREATIM = "creatim";
    /**
     * Datum, an dem das Dokument das letzte Mal geaendert wurde
     */
    public static final String INFO_REVTIM = "revtim";
    /**
     * Datum, an dem das Dokument das letzte Mal gedruckt wurde
     */
    public static final String INFO_PRINTIM = "printim";
    /**
     * Datum, an dem das Dokument das letzte Mal gesichert wurde
     */
    public static final String INFO_BUPTIM = "buptim";
    
    // Instanzvariablen
    private Map infos;
    private int version;
    
    /**
     * Erzeugt ein neues Dokumenteninformations-Objekt
     */
    public InfoGroup() {
        infos = new HashMap();
        // Anlegezeitpunkt des Dokumentes einfügen
        setInfoAsDate(INFO_CREATIM, new Date());
    }

    /**
     * Setzt einen Dokumenteninformationswert als String
     * 
     * @param key   - Key der Dokumenteninformation
     * @param value - Wert der Dokumenteninformation
     */
    public void setInfoAsString(String key, String value) {
        infos.put(key, RtfUtil.getRTFString(value));
    }

    /**
     * Setzt einen Dokumenteninformationswert als Date
     * 
     * @param key   - Key der Dokumenteninformation
     * @param value - Wert der Dokumenteninformation
     */
 	public void setInfoAsDate(String key, Date value) {
 	    infos.put(key, RtfUtil.convertDateToRTFString(value));
 	}

 	/**
 	 * Setzt eine Version für das aktuelle Dokument
 	 * 
 	 * @param value - Dokumentenversion
 	 */
 	public void setVersion(int value) {
 	    this.version = value;
 	}
 	
 	/**
 	 * Gibt die Dokumentenversion zurück (Default: 0)
 	 * 
 	 * @return Dokumentenversion
 	 */
 	public int getVersion() {
 	    return this.version;
 	}
 	
 	/**
 	 * Gibt die RTF Definition der Dokumenteninfo zurück
 	 * 
 	 * @return RTF-konformer String
 	 */
 	public String getRtfContent() {
 	    StringBuffer content = new StringBuffer();
 	    content.append("{\\info");
 	    // Alle Werte aus Map holen
 	    Iterator it = infos.keySet().iterator();
 	    while(it.hasNext()){
 	        String key = (String)it.next();
 	        content.append("{\\" + key + " " + infos.get(key) + "}");
 	    }
 	    // Version setzen
 	    content.append("{\\version" + getVersion() + "}");
 	    content.append("}");
 	    return content.toString();
 	}
}
