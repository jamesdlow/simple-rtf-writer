package com.itseasy.rtf;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.itseasy.rtf.exception.BookmarkIsNotCheckboxException;
import com.itseasy.rtf.exception.BookmarkNotFoundException;
//import com.itseasy.rtf.text.MultiTextParts;
import com.itseasy.rtf.text.Font;
import com.itseasy.rtf.text.MultiParagraphs;
import com.itseasy.rtf.text.Paragraph;
import com.itseasy.rtf.text.Text;


/**
 * Repr�sentiert ein bestehendes RTF-Dokument, welches als Vorlage (Template) geladen und mit Inhalt an Textmarken und 
 * Formularfeldern erg�nzt werden kann. Derzeit werden als Formualrfelder Text und Checkboxen unterst�tzt. Listboxen
 * k�nnen nocht nicht gesetzt werden. <br><br>
 * Bei Templates besteht die M�glichkeit den Inhalt im Speicher zwischen zu spreichern (cachen) und  bei
 * Bedarf aus dem Hauptspeicher wieder mit dem alten Inhalt zu �berschreiben oder bei jeder Verwendung den 
 * Inhalt neu vom Filesystem zu laden (Default).<br><br> 
 * 
 * <code>
 *   // RTF Template lesen und Textmarken f�llen <br>
 *   RTFTemplate doc = new RTFTemplate(new File("c:/rtftest_template.rtf")); <br>
 *   // Text f�r Felder einf�gen <br>
 *   doc.setBookmarkContent("Nummer", "08/15"); <br>
 *   doc.setBookmarkContent("Von", "Mustermann GmbH, M�nchen"); <br>
 *   // Checkboxen mit Inhalt f�llen <br>
 *   doc.setBookmarkCheckbox("Bar", true); <br>
 *   // Dokument wieder speichern <br>
 *   doc.save(new File("c:/rtftest2.rtf")); <br>
 * </code>
 * 
 * @version 0.5.0 	22.05.2004
 * @author 			Stefan Finkenzeller
 */

public class RTFTemplate extends RTFAbstractDocument {
    private StringBuffer content = new StringBuffer();
    private Set fonts = new HashSet();				// Zwischenspeicher f�r Schriften (wird am Ende in das Dokument eingef�gt
    // Cache, wenn gew�nscht
    private String cache;
    private File cachefile;
    private boolean activecache = false;
    
    /**
     * L�d eine RTF-Datei als Vorlage in den Speicher. Die Cache-Option ist ausgeschaltet (Default). Dies bedeutet,
     * dass bei einem <code>reset()</code> die Vorlage erneut vom Filesystem eingelesen wird.
     * 
     * @param file - File-Objekt der zu ladenden RTF-Vorlage 
     * @throws IOException
     */
    public RTFTemplate(File file) throws IOException {
        this.activecache = false;
        this.cachefile = file; 
        readFile(file);
    }

    /**
     * L�d eine RTF-Datei als Vorlage in den Speicher. Die Cache-Option kann aktiviert ("true") oder 
     * ausgeschaltet ("false") werden. Ausgeschalteter Cache bedeutet, dass bei einem <code>reset()</code>
     * die Vorlage erneut vom Filesystem eingelesen wird. Ist er eingeschaltet wird die Vorlage vom
     * Vorlagencache (Hauptspeicher) kopiert. 
     * 
     * @param file     - File-Objekt der zu ladenden RTF-Vorlage
     * @param setcache - "true" = cachen im Hauptspeicher; "false" = jedesmal neu vom Filesystem einlesen 
     * @throws IOException
     */
    public RTFTemplate(File file, boolean setcache) throws IOException {
        // Setze Cache-Kennzeichen
        this.activecache = setcache;
        // Lese File ein
        this.cachefile = file; 
        readFile(file);
        // F�lle Cache, wenn gew�nscht
        if (setcache) this.cache = this.content.toString();
    }
    
    /**
     * Setzt den Inhalt wieder auf den Anfangswert zur�ck. Alle ge�nderten Textmarkten und Formuarfelder haben
     * wieder den urspr�nglichen Wert.
     * 
     * @throws IOException
     */
    public void reset() throws IOException {
        // Zwischenspeicher Schriftartentabelle l�schen
        fonts.clear();
        // Inhalt zur�cksetzen
        if (this.activecache) {
            this.content = new StringBuffer(cache);
        } else {
            readFile(cachefile);
        }
    }
    
    /**
     * List ein RTF Dokument und speichert den Inhalt in diesem Objekt.
     * 
     * @param file - Zu lesende RTF Template
     * @throws IOException
     */
    private void readFile(File file) throws IOException {
        char[] buffer = new char[1024];		// Einlesebuffer 
        Reader in = new FileReader(file);
        // Datei einlesen und in "content" abspeichern
        while (in.read(buffer) >= 0) {
            content.append(buffer);
        }
        in.close();
    }
    
    
    /**
     * Abfrage, ob in dem RTF Dokument eine bestimmte Textmarke enthalten ist
     *  
     * @param name	- Name der Textmarke
     * @return true = Textmarke gefunden, false = Textmarke nicht vorhanden
     */
    public boolean existBookmark(String name) {
        int pos = content.toString().indexOf("\\bkmkstart " + name + "}");
        return (pos >= 0 ? true : false);
    }
    
    /**
     * Sucht eine bestimmte Textmarke und gibt Informationen dazu zur�ck. Existiert die Textmarke 
     * nicht, so wird NULL zur�ckgegeben.
     * 
     * @param name - Name der Textmarke
     * @return Wichtige Verwaltungsinformationen zu dieser Textmarke
     */
    private BookmarkInfo searchBookmark(String name) throws BookmarkNotFoundException {
        BookmarkInfo result = null;
        // RTF-Dokument in String umwandeln
        String rtfdoc = content.toString();
        // Anfang der Textmark suchen
        int begin = rtfdoc.indexOf("\\bkmkstart " + name + "}");
        // Falls kein Beginn gefunden wurde, dann wird nochmal etwas anders gesucht (evtl. ist das abschlie�ende "}" in die n�chste Zeile gerutscht
        if (begin < 0) begin = rtfdoc.indexOf("\\bkmkstart " + name + (char) 0x0D);
        if (begin >= 0) {
            // Contentbeginn suchen
            int contbegin = rtfdoc.indexOf("}", begin);
            // Folgende "}" �berspringen
            while (rtfdoc.charAt(contbegin) == '}') {
                contbegin++;
            }
            // Ende der Textmarke suchen
            int end = rtfdoc.indexOf("\\bkmkend " + name + "}", contbegin);
            // Falls kein Ende gefunden wurde, dann wird nochmal etwas anders gesucht (evtl. ist das abschlie�ende "}" in die n�chste Zeile gerutscht
            if (end < 0) end = rtfdoc.indexOf("\\bkmkend " + name + (char) 0x0D, contbegin);
            if (end >= 0) {
                // Contentende suchen
                int contend = rtfdoc.lastIndexOf("{", end);
                // Folgende "{" �berspringen
                while (rtfdoc.charAt(contend) == '{') {
                    contend--;
                }
                // Das Ende noch eine Stelle nach vorne verschieben
                contend++;
                result = new BookmarkInfo(name, contbegin, contend, rtfdoc.substring(contbegin, contend));
            } else {
                throw new BookmarkNotFoundException(name, "Endtag of bookmark '" + name + "' not found!");
            }
        } else {
           throw new BookmarkNotFoundException(name, "Bookmark '" + name + "' not found!");
        }
        return result;
    }
    
    /**
     * Setzt den Inhalt einer Textmarke bzw. eines Text-Formularfeldes. Als Inhalt wird ein RTF-konformer
     * String erwartet.
     * 
     * @param bookmark - Name der Textmarke
     * @param text - RTF-konformer String
     */
    private void setBookmarkRawContent(String bookmark, String text) throws BookmarkNotFoundException {
        // Textmarke suchen
        BookmarkInfo bmi = searchBookmark(bookmark);
        // Textmarke einf�gen, wenn vorhanden
        if (bmi.isField()) {
            String bkmkcontent = bmi.getRawContent();
            // Pr�fen, ob bereits ein Feldinhalt vorhanden ist
            int contpos = bkmkcontent.indexOf("\\fldrslt");
            if (contpos >= 0) {
                // Feldinhalt bereits vorhanden und muss �berschreiben werden.
                int begin = bkmkcontent.lastIndexOf("{", contpos);	// Beginn suchen
                int end = RtfUtil.getPositionOfTagEnd(bkmkcontent, begin);
//                // Ende suchen
//                int end = begin + 1;
//                int klammercount = 1;
//                while (klammercount > 0) {
//                    if (bkmkcontent.charAt(end) == '{') klammercount++;
//                    else if (bkmkcontent.charAt(end) == '}') klammercount--;
//                    end++;
//                }
                // Alten Feldinhalt ersetzen
                content.replace(bmi.getContentBegin() + begin , bmi.getContentBegin() + end, "{\\fldrslt " + text + "}");
            } else {
                // Es handelt sich um ein Feld ohne Inhalt - jetzt kommt ein Hack... 
                // In die erste innere Klammer positionieren (ist die "\field" - Klammer)
                int inspos = bkmkcontent.lastIndexOf("}");
                // Inhalt einf�gen
                content.insert(bmi.getContentBegin() + inspos, "{\\fldrslt " + text + "}");
            }
        } else {
            // Normale Textmarke
            content.replace(bmi.getContentBegin(), bmi.getContentEnd(), text);
        } 
    }

    /**
     * Setzt den Inhalt einer Textmarke bzw. eines Text-Formularfeldes mit dem �bergebenen String.
     * 
     * @param bookmark - Name der Textmarke
     * @param text - Neuer Inhalt der Textmarke
     */
    public void setBookmarkContent(String bookmark, String text) throws BookmarkNotFoundException {
		setBookmarkRawContent(bookmark, RtfUtil.getRTFString(text));
	}

    /**
     * Setzt den Inhalt einer Textmarke bzw. eines Text-Formularfeldes mit dem �bergebenen Text-Objekt
     * (z.B. TextPart, MultiTextParts, ..). Hierbei k�nnen dem Text auch Formatierungen, wie z.B. andere 
     * Schriftart/-gr��e oder fett, mitgegeben werden.
     * 
     * @param bookmark - Name der Textmarke
     * @param textparts - Text-Objekt mit dem neuen Inhalt
     */
    public void setBookmarkContent(String bookmark, Text textparts) throws BookmarkNotFoundException {
		setBookmarkRawContent(bookmark, textparts.getRtfContent());
	}

    /**
     * Setzt den Inhalt einer Textmarke bzw. eines Text-Formularfeldes mit dem �bergebenen Paragraph-Objekt.
     * Hiermit k�nnen auch komplexe Textkonstrukte in das Dokument eingef�gt werden. 
     * 
     * @param bookmark - Name der Textmarke
     * @param text - Paragraph-Objekt mit dem neuen Inhalt
     * @param insideTable - Kennzeichen, ob der Inhalt in eine Tabelle eingef�gt wird (dann muss ein
     * 						zus�tzliches RTF-Kennzeichen im Dokument eingef�gt werden).
     */
    public void setBookmarkContent(String bookmark, Paragraph text, boolean insideTable) throws BookmarkNotFoundException {
        // Speichere evtl. vorhandene Schriften ab
        Set fs = text.getAllFonts();
        if (fs != null) fonts.addAll(fs);
        // Schreibe Paragraph in das Dokument
		setBookmarkRawContent(bookmark, text.getRtfContent(insideTable));
	}

    /**
     * Setzt den Inhalt einer Textmarke bzw. eines Text-Formularfeldes mit dem �bergebenen MultiParagraphs-Objekt.
     * Hiermit k�nnen auch komplexe Textkonstrukte in das Dokument eingef�gt werden. Die Textmarkte darf
     * nicht innerhalb einer Tabelle stehen. <br><br> Diese Methode macht das Gleiche wie "setBookmarkContent(bookmark, text, false);"
     * 
     * @param bookmark - Name der Textmarke
     * @param text - MultiParagraphs-Objekt mit dem neuen Inhalt
     */
    public void setBookmarkContent(String bookmark, Paragraph text) throws BookmarkNotFoundException {
		setBookmarkContent(bookmark, text, false);
	}

    /**
     * Setzt den Inhalt einer Textmarke bzw. eines Text-Formularfeldes mit dem �bergebenen MultiParagraphs-Objekt.
     * Hiermit k�nnen auch komplexe Textkonstrukte in das Dokument eingef�gt werden. 
     * 
     * @param bookmark - Name der Textmarke
     * @param text - MultiParagraphs-Objekt mit dem neuen Inhalt
     * @param insideTable - Kennzeichen, ob der Inhalt in eine Tabelle eingef�gt wird (dann muss ein
     * 						zus�tzliches RTF-Kennzeichen im Dokument eingef�gt werden).
     */
    public void setBookmarkContent(String bookmark, MultiParagraphs text, boolean insideTable) throws BookmarkNotFoundException {
        // Speichere evtl. vorhandene Schriften ab
        Set fs = text.getAllFonts();
        if (fs != null) fonts.addAll(fs);
        // Schreibe Paragraphs in das Dokument
		setBookmarkRawContent(bookmark, text.getRtfContent(insideTable));
	}

    /**
     * Formularfelder, die vom Typ Checkbox sind, k�nnen mit dieser Funktion gesetzt werden.
     * 
     * @param bookmark - Name der Textmarke
     * @param value - "true" = Checkbox gesetzt; "false" = Checkbox nicht gesetzt
     */
    public void setBookmarkCheckbox(String bookmark, boolean value) throws BookmarkNotFoundException, BookmarkIsNotCheckboxException {
        // Textmarke suchen
        BookmarkInfo bmi = searchBookmark(bookmark);
        // Ergebniswert definieren - gesetzte Checkbox hat den Wert "\ffres1", nicht gesetzt den Wert "\ffres25"
        String cbresult = (value ? "1" : "25");
        // Textmarke einf�gen, wenn vorhanden
        if (bmi.isField()) {
            if (bmi.getFieldtype() == BookmarkInfo.TYPE_CHECKBOX) {
                // Pr�fen, ob Ergebniswert schon gesetzt ist -> muss dann nur ge�ndert werden
                int posres = bmi.getRawContent().indexOf("\\ffres");
                if (posres >= 0) {
                    // Pr�fen, wie lange der Wert hinter \ffres ist
                    int len = 0;
                    int countstart = bmi.getContentBegin() + posres + 6; 	// Auf Wert positionieren
                    while (Character.isDigit(content.charAt(countstart + len))) {
                       len++;
                    }
                    // Wert setzen
                    content.replace(countstart , countstart + len, cbresult);
                } else {
                    // Ergebniswert muss neu gesetzt werden. Parameter wird nach "\fftype" gesetzt
                    int postype = bmi.getRawContent().indexOf("\\fftype");
                    if (postype >= 0) {
                        // Wert danach einf�gen
                        content.insert(bmi.getContentBegin() + postype + 8, "\\ffres " + cbresult);
                    } else {
                        throw new BookmarkNotFoundException(bookmark, "Typedefinition not found!");
                    }
                }
            } else {
                throw new BookmarkIsNotCheckboxException(bookmark);
            }
        } else {
            throw new BookmarkIsNotCheckboxException(bookmark);
        }
    }
    
    /**
     * Gibt das komplette RTF Dokument als String zur�ck
     * 
     * @return RTF Dokument als String
     */
    protected String getDocumentAsString() {
        // Zus�tzliche Schriften in das Dokument einf�gen
        if (fonts.size() > 0 ) setFonttable();
        // Dokumen zur�ckgeben
        return content.toString();
    }

    
    /**
     * Erg�nzt die Schriftartentabelle des Dokumentes. Dabei werden alle eingef�gten Text bzw. Paragraph
     * Objekte nach gesetzten Schriften abgefragt und ggf. die Dokumentenschriftentabelle erg�nzt. Die 
     * neuen Schriften beginnen mit der Schriftnummer 200.
     */
    protected void setFonttable() {
        if (fonts.size() > 0) {
            // Alle Fonts sammeln
            StringBuffer tbl = new StringBuffer();
            Iterator fsit = fonts.iterator();
            while(fsit.hasNext()) {
                Font nextfnt = (Font) fsit.next();
                if (nextfnt != null) {
                    tbl.append("{" + nextfnt.getRtfContent() + "}");
                }
            }

            // Position der Fonttabelle finden 
            int pos = content.indexOf("\\fonttbl" , 0);
        	if (pos >= 0) {
        	    int insertpos = RtfUtil.getPositionOfTagEnd(content.toString(), pos);
            	content.insert(insertpos - 1, tbl.toString());
        	} else {
            	// Keine Fonttabelle gefunden; Neue wird angelegt
            	pos = content.indexOf("{", 1);
            	content.append("\\fonttbl");  	// Beginn der Fonttabelle
            	content.insert(pos, tbl.toString());
        	}
        }
    }
    
    /**
     * Gibt alle im Dokument enthaltenen Textmarken als List zur�ck.
     *  
     * @return	 - ArrayList mit allen Textmarkennamen
     */
    public List getBookmarkNames() {
        List allbm = new ArrayList();
        // Alle Textmarken finden
        int pos = 0;
        while ((pos = content.indexOf("\\bkmkstart " , pos)) >= 0) {
            // Ende der Textmarke finden
            int end = content.indexOf("}", pos);
            // Name der Textmarke der List hinzuf�gen
            allbm.add(content.substring(pos + 11, end));
            pos++;
        }
        return allbm;
    }
    
    
    /**
     * R�ckgabeobjekt, in dem alle wichtigen Informationen einer Textmarke zusammengefasst sind,
     * die f�r das Einf�gen von Text notwendig sind. 
     */
    protected class BookmarkInfo {
        // Konstanten
        public static final int TYPE_NIL = -1;
        public static final int TYPE_TEXT = 0;
        public static final int TYPE_CHECKBOX = 1;
        public static final int TYPE_LISTBOX = 2;
        // Variablen
        private String name;
        private int contentbegin;
        private int contentend;
        private boolean field;
        private String rawcontent;
        
        /**
         * Legt ein neues BookmarkInfo Objekt mit den entsprechenden (internen) Werten an.
         * 
         * @param name	- Name der Textmarke
         * @param begin - Position innerhalb des RTF Dokumentes, ab dem die Textmarkte beginnt (nach dem "/bkmstart" Tag)
         * @param end  - Position innerhalb des RTF Dokumentes, an dem die Textmarkte endet (vor dem "/bkmend" Tag)
         * @param rawcontent - Inhalt (inkl. RTF Steuerzeichen), der zwichen <begin> und <end> liegt 
         */
        public BookmarkInfo(String name, int begin, int end, String rawcontent) {
            this.name = name;
            this.contentbegin = begin;
            this.contentend = end;
            this.rawcontent = rawcontent;
            // Pr�fen, ob es sich um ein Feld handelt
            if ((rawcontent != null) && (rawcontent.indexOf("\\formfield") >= 0)) {
                this.field = true;
            } else {
                this.field = false;
            }
        }
        
        /**
         * Gibt die Position zur�ck, an dem die Textmarke beginnt
         * 
         * @return - Position innerhalb des RTF Dokumentes
         */
        public int getContentBegin() {
            return contentbegin;
        }
        
        /**
         * Gibt die Position zur�ck, an dem die Textmarke endet
         * 
         * @return - Position innerhalb des RTF Dokumentes
         */
        public int getContentEnd() {
            return contentend; 
        }
        
        /**
         * Wenn es sich bei der Textmarke um ein Formularfeld handelt, wird "true" zur�ckgegeben. 
         * 
         * @return - Kennzeichen, ob es sich um ein Formularfeld handelt oder nicht.
         */
        public boolean isField() {
            return field;
        }
        
        /**
         * Gibt TRUE zur�ck, wenn es sich um ein Feld handelt und es gesperrt (Read-Only) ist. Bei 
         * FALSE kann das Feld ge�ndert werden. Es handelt sich hierbei aber nur um eine Information,
         * da auch gesperrte Felder ge�ndert werden k�nnen und der SimpleRTFWriter darauf keine
         * R�cksicht nimmt.
         * 
         * @return   - TRUE = Feld ist gesperrt, FALSE = Feld darf geschrieben werden
         */
        public boolean isFieldReadOnly() {
            boolean result = false;
            if (isField()) {
                // Pr�fen, ob Feld gesperrt ist
                if ((rawcontent != null) && (rawcontent.indexOf("\\fldlock") >= 0)) {
                    result = true;
                } else {
                    // Pr�fen, ob Feld gesch�tzt ist
                    if ((rawcontent != null) && (rawcontent.indexOf("\\ffprot1") >= 0)) {
                        result = true;
                    } else {
                        result = false;
                    }
                }
            }
            return result;
        }
        
        /**
         * Gibt die maximale Gr��e des einzugebenden Feldtextes aus. Ist keine maximale L�nge gesetzt
         * oder ist die Textmarke kein Feld, so wird "-1" zur�ckgegeben. Es handelt sich hierbei aber nur
         * um eine Information, da der SimpleRTFWriter darauf keine R�cksicht beim Ver�ndern nimmt.
         * 
         * @return  - maximale Feldgr��e (-1 = unbegrenzt)
         */
        public int getFieldMaxLength() {
            int result = -1;
            if (isField()) {
                // Pr�fen, ob Maximall�nge gesetzt ist
                if (rawcontent != null) {
                    int pos = rawcontent.indexOf("\\ffmaxlen");
                    if (pos >= 0) {
                        result = RtfUtil.getIntegerFromText(rawcontent, pos + 9);
                    }
                }
            }
            return result;
        }
        
        /**
         * Gibt den Typ des Feldes an, wenn es ein Formularfeld ist. Ist es ein unbekannter Typ 
         * oder kein Formularfeld, so wird -1 (TYPE_NIL) zur�ckgegeben.
         * 
         * @return Feldtyp (TYPE_TEXT = Textfeld, TYPE_CHECKBOX = Checkbox, TYPE_LISTBOX = Listbox , TYPE_NIL = unbekannt)
         */
        public int getFieldtype() {
            int result = TYPE_NIL;
            if (this.field) {
                // Felddefinition suchen
                int posfd = rawcontent.indexOf("\\fftype");
                if (posfd >= 0) {
                    switch(rawcontent.charAt(posfd + 7)) {
                    	case '0': result = TYPE_TEXT; break;
                    	case '1': result = TYPE_CHECKBOX; break;
                    	case '2': result = TYPE_LISTBOX; break;
                    	default: result = TYPE_NIL;
                    }
                }
            }
            return result;
        }
        
        /**
         * Gibt den Inhalt zwischen den beiden Bookmark-Markierungen zur�ck (inkl. RTF-Steuerzeichen).
         * 
         * @return Inhalt der Textmarke (inkl. RTF-Steuerzeichen) 
         */
        public String getRawContent() {
            return rawcontent;
        }
    }

}
