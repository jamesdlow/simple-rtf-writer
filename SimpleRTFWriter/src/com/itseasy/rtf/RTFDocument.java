package com.itseasy.rtf;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.itseasy.rtf.text.Font;
import com.itseasy.rtf.text.InfoGroup;
import com.itseasy.rtf.text.PageDefinition;
import com.itseasy.rtf.text.PageSize; 
import com.itseasy.rtf.text.Paragraph;

/**
 * Die Klasse RTFDocument repräsentiert ein RTF Dokument nach Microsoft Spezifikation
 * 1.6 (http://msdn.microsoft.com/library/default.asp?url=/library/en-us/dnrtfspec/html/rtfspec_6.asp).
 * Es wurde aber nur ein kleiner Teil der Spezifikation umgesetzt, so dass wirklich nur ein Simple RTF
 * Dokument generiert wird. <br><br>
 * <code>
 *   // RTF Dokument generieren  <br>
 *   RTFDocument doc = new RTFDocument();  <br>
 *   // Dokumenteninformation hinzufügen [optional]  <br>
 *   doc.getInfo().setInfoAsString(InfoGroup.INFO_AUTHOR, "IT'S EASY");  <br>
 *   doc.getInfo().setInfoAsString(InfoGroup.INFO_TITLE, "Dies ist ein RTF Generierungstest...");  <br>
 *   // Einfachen Texth hinzufügen  <br>
 *   Paragraph absatz = new Paragraph(0, 6);  <br>
 *   absatz.addText(new TextPart("Dies ist ein einfacher Text in der Standardschriftart."));  <br>
 *   doc.addParagraph(absatz);  <br>
 *   // Dokument wieder speichern  <br>
 *   doc.save(new File("c:/rtftest2.rtf"));  <br>
 * </code>
 * 
 * @version 0.6.0 	31.05.2008
 * @author 			James Low
 */

public class RTFDocument extends RTFAbstractDocument {
    /**
     * Anzeigezoom wird so gewählt, dass die komplette Seite dargestellt wird 
     */
    public static final int VIEWSCALE_FULLPAGE = -1;
    /**
     * Anzeigezoom wird so gewählt, dass die komplette Seitenbreite angezeigt wird 
     */
    public static final int VIEWSCALE_BESTFIT = -2;

    /**
     * Anzeigemodus "<Default>" 
     */
    public static final int VIEWKIND_NONE = 0;
    /**
     * Anzeigemodus "Seitenlayout" 
     */
    public static final int VIEWKIND_PAGELAYOUT = 1;
    /**
     * Anzeigemodus "Gliederung" 
     */
    public static final int VIEWKIND_OUTLINE = 2;
    /**
     * Anzeigemodus "Master-Dokumenten-Ansicht" 
     */
    public static final int VIEWKIND_MASTERDOCUMENT = 3;
    /**
     * Anzeigemodus "Normale" 
     */
    public static final int VIEWKIND_NORMAL = 4;
    /**
     * Anzeigemodus "Weblayout" 
     */
    public static final int VIEWKIND_ONLINELAYOUT = 5;
    
    // Variablen
    private InfoGroup info = new InfoGroup();
    private List paragraph = new ArrayList();		// Inhalt des Dokumentes
    private List header = new ArrayList();		// Kopfzeile
    private List footer = new ArrayList();		// Fußzeile
    private Font deffont = Font.ARIAL;;		// Default Schriftart
    private PageDefinition pagedef;
    private int viewscale;		// Anzeige-Zoom in Prozent (Default ist 100)
    private int viewkind;		// Anzeige-Modus (Konstanten VIEWMODE_*)
    
    /**
     * Erzeugt ein neues RTF Dokument im Hauptspeicher. Dem Dokument wird das Format DIN_A4 (Default)
     * zugeordnet. Die Seitenränder werden auf 2 cm gesetzt.
     */
    public RTFDocument() {
        this(PageSize.DIN_A4);
    }
    
    /**
     * Erzeugt ein neues RTF Dokument im Hauptspeicher, bei dem man das gewünschte 
     * Papierformat setzen kann. Die Seitenränder werden auf 2 cm gesetzt.
     *  
     * @param ps - gewünschtes Papierformat (z.B. PageSize.DIN_A4)
     */
    public RTFDocument(PageSize ps) {
        this(new PageDefinition(ps, 20, 20, 20, 20));	// Definiert die Seitengröße mit 2 cm Rändern
    }
    
    public RTFDocument(PageDefinition pagedef) {
        this.pagedef = pagedef;
    }

    /**
     * Setzt die default Schriftart. Wenn nichts angegeben wird, so ist "Arial" die Default Schriftart.
     * 
     * @param font	- Default Schriftart (z.B Font.ARIAL)
     */
    public void setDefaultFont(Font font) {
        this.deffont = font;
    }
   
    /**
     * Setzt den Zoomlevel (in Prozent) zur Anzeige des Dokumentes. Default ist 100 %. Hierrüber können auch relative
     * Werte wie "Anzeige der kompletten Seite" (VIEWSCALE_FULLPAGE) oder "Anzeige komplette Seitenbreite"
     * (VIEWSCALE_BESTFIT). 
     * 
     * @param zoom - Zoomlevel des Dokumentes (Default = 100 %)
     */
    public void setViewscale(int zoom) {
        this.viewscale = zoom;
    }

    /**
     * Setzt den Anzeigemodus des Dokumentes. Das RTFDocument bietet hierfür 6 Konstanten an (RTFDocument.VIEWKIND_*)
     * 
     * @param mode - Anzeigemodus (VIEWKIND_*)
     */
    public void setViewkind(int mode) {
        this.viewkind = mode;
    }

    /**
     * Gibt das komplette RTF Dokument als String zurück
     * 
     * @return RTF Dokument als String
     */
    protected String getDocumentAsString() {
        return "{" + getHeader() + getDocInfo() + getPageDefinition().getRtfContent() + getDocView() + getHeaderFooterContent() + getContent() + "}";
    }
    
    /**
     * Gibt den Header des RTF Dokumentes zurück. Der Header hat folgenden Aufbau:<br>
     * "\rtf <charset> [\deff] <fonttbl> [<filetbl>] [<colortbl>] [<stylesheet>] [<listtables>] [<revtbl>]"
     * 
     * @return	Headerinhalt
     */
    private String getHeader() {
        return "\\rtf" + RTFVERSION 			// RTF Version, damit beginnt jedes RTF Dokument
        	 + "\\ansi\\ansicpg1252"			// <charset>	-> Default, ANSI mit Mitteleuropäischen Zeichensatz
        	 + "\\deff" + this.deffont.getFontnumber() + "{" + getFonttable() + "}"		// <fonttbl>	-> Schriftentabelle
        	 + "{" + getColortable() + "}"		// <colortbl>	-> Farbtabelle
        	 // \stylesheet ... http://msdn.microsoft.com/library/default.asp?url=/library/en-us/dnrtfspec/html/rtfspec_6.asp
             + "{\\*\\generator " + GENERATOR + ";}";	// Generatorname
    }

    
    /**
     * Gibt die Dokumentenanzeige-Definition des Dokumentes zurück.
     * 
     * @return Dokumentenanzeige-Definition als RTF-konformer String
     */
    private String getDocView() {
        StringBuffer result = new StringBuffer();
        //	Zoomlevel ausgeben (nur wenn nicht "0" und nicht "100" (Default)
        if ((viewscale > 0) && (viewscale != 100)) { 
            result.append("\\viewscale" + viewscale);
        } else if (viewscale == VIEWSCALE_FULLPAGE) {
            result.append("\\viewzk1");
        } else if (viewscale == VIEWSCALE_BESTFIT) {
            result.append("\\viewzk2");
        }
        // Anzeige-Modus ausgeben
        if (viewkind > 0) 
            result.append("\\viewkind" + viewkind);							
        return result.toString();
    }
    
    
    /**
     * Gibt die Fonttabelle des RTF Dokumentes für den RTF Header zurück. Derzeit sind drei Schriften
     * im SimpleRTFWriter definiert: <br>
     *   No 0: Times New Roman <br>
     *   No 1: Arial <br>
     *   No 2: Courier New <br> 
     *   No 3: Symbol <br>
     * Alles als ANSI Fonts mit default pitch.
     * @return	Fonttabelle
     */
    private String getFonttable() {
        // Alle Schriften sammeln - Im Inhalt
        Set fonts = new HashSet();
        Iterator it = paragraph.iterator();
        while(it.hasNext()) {
            Set fs = ((Paragraph) it.next()).getAllFonts();
            if (fs != null) fonts.addAll(fs);
        }
        // Alle Schriften sammeln - in der Kopfzeile
        if (header.size() > 0) {
            it = header.iterator();
            while(it.hasNext()) {
                Set fs = ((Paragraph) it.next()).getAllFonts();
                if (fs != null) fonts.addAll(fs);
            }
        }
        // Alle Schriften sammeln - in der Fußzeile
        if (footer.size() > 0) {
            it = footer.iterator();
            while(it.hasNext()) {
                Set fs = ((Paragraph) it.next()).getAllFonts();
                if (fs != null) fonts.addAll(fs);
            }
        }
        // Tabelle zurückgeben, wenn Schriften verwendet wurden 
        StringBuffer content = new StringBuffer();
        if (fonts.size() > 0) {
            content.append("\\fonttbl");  // Beginn der Fonttabelle
            Iterator fsit = fonts.iterator();
            while(fsit.hasNext()) {
                Font nextfnt = (Font) fsit.next();
                if (nextfnt != null) {
                    content.append("{" + nextfnt.getRtfContent() + "}");
                }
            }
//       	 + "{\\f0\\froman\\fcharset0 Times New Roman;}"
//       	 + "{\\f1\\fswiss\\fcharset0 Arial;}"
//       	 + "{\\f2\\fmodern\\fcharset0 Courier New;}";
        }
        return content.toString();
    }
    
    /**
     * Gibt die Farbtabelle des RTF Dokumentes zurück.
     * 
     * @return Fonttabelle als RTF String
     */
    private String getColortable() {
        return "\\colortbl;\\red0\\green0\\blue0;\\red0\\green0\\blue255;\\red0\\green255\\blue255;"
        	 + "\\red0\\green255\\blue0;"
        	 + "\\red255\\green0\\blue255;\\red255\\green0\\blue0;\\red255\\green255\\blue0;"
        	 + "\\red255\\green255\\blue255;\\red0\\green0\\blue128;\\red0\\green128\\blue128;"
        	 + "\\red0\\green128\\blue0;\\red128\\green0\\blue128;\\red128\\green0\\blue0;"
        	 + "\\red128\\green128\\blue0;\\red128\\green128\\blue128;\\red192\\green192\\blue192;";
    }
    
    /**
     * Gibt die Dokumenteninformation mit Autor, Erstellungsdatum zurück
     * 
     * @return Dokumenteninformation als RTF String
     */
    private String getDocInfo() {
        return info.getRtfContent();
    }
    
    /**
     * Gibt die Papierdefinition des RTF Dokumentes aus. Mit einem Papierdefinitions-Objekt
     * können z.B. die Seitenränder eingestellt werden.
     * 
     * @return - PaperDefinition
     */
    public PageDefinition getPageDefinition() {
        return pagedef;
//        return "\\paperw" + papersize.getWidth() + "\\paperh" + papersize.getHeight() 
//        		+ "\\margl1417\\margr1417\\margt1417\\margb1134";
    }

    /**
     * Gibt den Text des RTF Dokuments zurück ohne Headerinformationen
     * 
     * @return	Text des RTF Dokuments
     */
    private String getContent() {
//        return "\\pard\\plain\\par\\ql\\faauto {\\ul Stefan\\ul0 ist }{\\b super } \\pard\\plain\\par {\\b Aktuelles Datum ist \\chdate...}";
        StringBuffer content = new StringBuffer();
        Iterator it = paragraph.iterator();
        while(it.hasNext()) {
            content.append(((Paragraph) it.next()).getRtfContent());
        }
        return content.toString();
    }

    /**
     * Gibt den RTF-Text aus, der die Kopf- und Fußzeile definiert
     *  
     * @return RTF-Text der Kopf- und Fußzeile
     */
    private String getHeaderFooterContent() {
        StringBuffer content = new StringBuffer();
        // Kopfzeile ausgeben
        if (header.size() > 0) {
            content.append("{\\header ");		// Beginn des Headers
            // Inhalt der Kopfzeile sammeln
            Iterator it = header.iterator();
            while(it.hasNext()) {
                content.append(((Paragraph) it.next()).getRtfContent());
            }
            content.append("}");				// Ende des Headers
        }
        // Fukßzeile ausgeben
        if (footer.size() > 0) {
            content.append("{\\footer ");		// Beginn des Footers
            // Inhalt der Fußzeile sammeln
            Iterator it = footer.iterator();
            while(it.hasNext()) {
                content.append(((Paragraph) it.next()).getRtfContent());
            }
            content.append("}");				// Ende des Footers
        }
        return content.toString();
    }
    
    /**
     * Gibt das Informationsobjekt zurück, in dem die Dokumenteninformationen grupppiert sind. Hierrüber
     * können die Dokumenteninformationen gesetzt werden. Um z.B. den Autor des Dokumentes zu setzen ist
     * folgendes auszuführen: <br>
     * <code> doc.getInfo().setInfoAsString(InfoGroup.INFO_AUTHOR, "IT'S EASY");</code>
     * 
     * @return Dokumenteninformationsobjekt
     */
    public InfoGroup getInfo() {
        return info;
    }

    /**
     * Fügt einen Paragraph dem RTF Dokument hinzu. Damit wird der Inhalt gesetzt. <br>
     * <code>
     *   Paragraph absatz = new Paragraph(0, 6);<br>
     *   absatz.addText(new TextPart("Inhalt eines Absatzes"));
     * 	 doc.addParagraph(absatz);<br>
     * </code>
     * 
     * @param para - Paragraph Objekt mit dem Textinhalt
     */
    public void addParagraph(Paragraph para) {
        paragraph.add(para);
    }
    
    /**
     * Fügt dem RTF Dokument eine Kopfzeile hinzu.
     * 
     * @param para - Paragraph Objekt mit dem Textinhalt
     */
    public void addHeader(Paragraph para) {
        header.add(para);
    }
    
    /**
     * Fügt dem RTF Dokument eine Fußzeile hinzu.
     * 
     * @param para - Paragraph Objekt mit dem Textinhalt
     */
    public void addFooter(Paragraph para) {
        footer.add(para);
    }
}
