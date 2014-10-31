package com.itseasy.rtf.text;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.itseasy.rtf.RtfUtil;

/**
 * Felder definieren variable Textbereiche, die durch die Textverarbeitung mit aktuellen Werten gefüllt
 * wird. Ein Beispiel für Felder sind "aktuelles Datum", "Seitennummer" oder "Gesamtseitenzahl".
 *
 * @version 0.1.0 	05.08.2004
 * @author 			Stefan Finkenzeller
 */
public class Field extends TextPart {
 	/**
 	 * Felddefinition: "aktuelle Seitennummer"
 	 */
 	public static final TextPart FIELD_CURRENT_PAGENO = new Field("PAGE", "1");	// Aktuelle Seitennummer
 	/**
 	 * Felddefinition: "Anzahl der Seiten des aktuellen Dokumentes"
 	 */
 	public static final TextPart FIELD_TOTAL_PAGES = new Field("NUMPAGES", "1");	// Gesamtzahl an Seitennummern
 	/**
 	 * Felddefinition: "Aktueller Dateiname"
 	 */
 	public static final TextPart FIELD_FILENAME = new Field("FILENAME \\\\p", "<n/a>");	// Aktueller Dateiname
 	/**
 	 * Felddefinition: "Aktuelles Datum"
 	 */
 	public static final TextPart FIELD_CURRENT_DATE = new Field("DATE \\\\@ dd.MM.yyyy", SimpleDateFormat.getInstance().format(new Date())	);	// Aktuelles Datum
 	     
    /**
     * Definiert ein Feld mit einer bestimmten Formel und einem Anfangswert, der beim aktuallisieren der Felddefinitionen
     * von der Textverarbeitung überschrieben wird.
     * 
     * @param formel	 - Formel, dem das Feld zugrundeliegt
     * @param firstvalue - Erster Wert des Feldes (wird von der Textverarbeitung bei der ersten Aktuallisierung überschrieben)
     */
    public Field(String formel, String firstvalue) {
        super("{\\field{\\*\\fldinst{" + formel + "}}{\\fldrslt{" + RtfUtil.getRTFString(firstvalue) + "}}}", true);
    }

}
