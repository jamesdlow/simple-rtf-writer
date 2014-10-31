package com.itseasy.rtf.text;

import java.util.HashSet;
import java.util.Set;

import com.itseasy.rtf.RtfUtil;

/**
 * Dies ist ein zusammengehöriger Textteil mit gleicher Formatierung. Ein Absatz (Paragraph) besteht aus
 * mehreren TextParts. Um einen einfachen Text im Fettdruck und unterstichen zu erstellen ist folgendes notwendig: <br><br>
 * <code> new TextPart(TextPart.FORMAT_BOLD + TextPart.FORMAT_UNDERLINE,"Ich bin ein Text in fett und unterstrichen!"); </code> <br><br>
 * Dem Text kann auch eine besondere (bewegte) Formatierung zugewiesen werden. Dies kann nicht über den Konstruktor, sondern
 * nur über die Methode <code>setAnimtext(int)</code> erfolgen.
 * 
 * @version 0.1.0 	22.05.2004
 * @author 			Stefan Finkenzeller
 * @see				Paragraph, MultiTextParts
 */
/**
 *  - <Beschreibung>
 *
 * @version 0.1.0 	11.09.2004
 * @author 			Stefan Finkenzeller
 */
public class TextPart implements Text {
    /**
     * Normale Formatierung (Default)
     */
    public static final int FORMAT_NORMAL = 0;
    /**
     * Formatierung: "Fettdruck"
     */
    public static final int FORMAT_BOLD = 1;
    /**
     * Formatierung: "Kursiv"
     */
    public static final int FORMAT_ITALIC = 2;
    /**
     * Formatierung: "Unterstrichen"
     */
    public static final int FORMAT_UNDERLINE = 4;
    /**
     * Formatierung: "Großbuchstaben"
     */
    public static final int FORMAT_CAPITALS = 8;
    /**
     * Formatierung: "Umrissdarstellung"
     */
    public static final int FORMAT_OUTLINE = 16;
    /**
     * Formatierung: "mit Schatten"
     */
    public static final int FORMAT_SHADOW = 32;

    /**
     * Kein bewegte Formatierung
     */
    public static final int ANIMTEXT_NO = 0;
    /**
     * Bewegte Formatierung "Lasvegas Lights"  
     */
    public static final int ANIMTEXT_LASVEGAS_LIGHTS = 1;
    /**
     * Bewegte Formatierung "Blinkender Hindergrund"  
     */
    public static final int ANIMTEXT_BLINKING_BACKGROUND = 2;
    /**
     * Bewegte Formatierung "Funkelnder Text"  
     */
    public static final int ANIMTEXT_SPARKLE_TEXT = 3;
    /**
     * Bewegte Formatierung "Wandernde schwarze Ameisen"  
     */
    public static final int ANIMTEXT_MARCHING_BLACK_ANTS = 4;
    /**
     * Bewegte Formatierung "Wandernde rote Ameisen"  
     */
    public static final int ANIMTEXT_MARCHING_RED_ANTS = 5;
    /**
     * Bewegte Formatierung "Schimmern"  
     */
    public static final int ANIMTEXT_SHIMMER = 6;
    // Besondere Zeichen
//    public static final char SIGN_NEWLINE = 0x10;	// Zeilenumbruch

    
    /**
     * Besonderes Zeichen: "Tabulator"
     */
    public static final char SIGN_TAB = 0x09;		// Tabulator
    
    
    /**
     * TextPart "Tabulator" (fügt ein Tabulatorzeichen als TextPart ein); Bewirkt das gleiche wie <code>SIGN_TAB</code>
     */
    public static final TextPart TABULATOR = new TextPart("\\tab", true);			// Tabulator mit Ausrichtung LINKS
	/**
	 * TextPart "Neue Zeile" (fügt einen Zeilenumbruch ein)
	 */
	public static final TextPart NEWLINE = new TextPart("\\line", true);	// Neue Zeile
	/**
	 * TextPart "Festes Leerzeichen" (zwei Wörter bleiben trotz dieses Leerzeichen verbunden, als wären sie ein
	 * zusammenhängedes Wort)
	 */
    public static final TextPart SIGN_HARDSPACE = new TextPart("\\~", true);			// Festes Leerzeichen 
	/**
	 * TextPart "Fester Trennstrich" (Wort wird dort <ul>nicht</ul> am Zeilenende getrennt)
	 */
  	public static final TextPart SIGN_NON_REQUIRED_HYPHEN = new TextPart("\\-", true);	// Fester Trennstrich 
	/**
	 * TextPart "Fester Bindestrich" (fügt einen festen Bindestrich ein)
	 */
  	public static final TextPart SIGN_NO_BREAKING_HYPHEN = new TextPart("\\_", true);	// Fester Bindestrich
  	
    // Variable
    private boolean rawrtf = false;
    private String content;
    private boolean bold = false;
    private boolean italic = false;
    private boolean underline = false;
    private boolean capitals = false;
    private boolean outline = false;
    private boolean shadow = false;
    private int animtext = ANIMTEXT_NO;
    private Font font;					// Schriftart
    private int fontsize = 0;			// Default = 12

/*
 * Was noch fehlt:
 * Background color (the default is 0) => \cbX
 * Foreground color (the dafualt ist 0) => \cfX
 * Strike through  (Durchgestrichen) => \strike, \striked1 (doppelt)
 * Hochgestellt => \super
 * Tiefgestellt => \sub
 */ 
    /**
     * Neuen TextPart definieren
     *
     * @param text - Inhalt des TextParts 
     */
    public TextPart(String text) {
        this.content = text;
    }
    
    /**
     * Konstruktor, über den auch RTF-formatierter Text übergeben werden kann. Dieser wird 1:1 so
     * wieder ausgegeben und nicht in einen RTF-konformen String umgewandelt.
     * 
     * @param rawtext - Inhalt des TextParts
     * @param raw     - "true" = RTF-konformer String, "false" = normaler Text 
     */
    protected TextPart(String rawtext, boolean raw) {
        if (raw) {
            this.content = rawtext;
            this.rawrtf = true;
        } else {
            this.content = rawtext;
        }
    }
    
    /**
     * Neuen TextPart mit Formatierung erzeugen
     *  
     * @param format - Formatierung des TextParts (TextPart.FORMAT_*)
     * @param text   - Inhalt des TextParts
     */
    public TextPart(int format, String text) {
        this(text);
        if ((format & 1) == 1) setBold(true);
        if ((format & 2) == 2) setItalic(true);
        if ((format & 4) == 4) setUnderline(true);
        if ((format & 8) == 8) setCapitals(true);
        if ((format & 16) == 16) setOutline(true);
        if ((format & 32) == 32) setShadow(true);
    }
    
    /**
     * Neuen TextPart mit Formatierung erzeugen
     *  
     * @param format   - Formatierung des TextParts (TextPart.FORMAT_*)
     * @param fontsize - Schriftgröße in Punkten für diesen TextPart
     * @param font     - Schrift des TextParts
     * @param text     - Inhalt des TextParts
     */
    public TextPart(int format, int fontsize, Font font, String text) {
        this(format, text);
        this.fontsize = fontsize;
        this.font = font;
    }
    
    /**
     * TextPart "kursiv" darstellen
     * 
     * @param value - "true" = kursiv ein; "false" = kursiv aus
     */
    public void setItalic(boolean value) {
        this.italic = value;
    }
    
    /**
     * Abfrage, ob TextPart kursiv dargestellt wird
     * 
     * @return "true" = kursiv ein; "false" = kursiv aus
     */
    public boolean isItalic() {
        return this.italic;
    }
    
    /**
     * TextPart "fett" darstellen
     * 
     * @param value - "true" = fett ein; "false" = fett aus
     */
    public void setBold(boolean value) {
        this.bold = value;
    }
    
    /**
     * Abfrage, ob TextPart fett dargestellt wird
     * 
     * @return "true" = fett ein; "false" = fett aus
     */
    public boolean isBold() {
        return this.bold;
    }

    /**
     * TextPart "unterstrichen" darstellen
     * 
     * @param value - "true" = unterstrichen ein; "false" = unterstrichen aus
     */
    public void setUnderline(boolean value) {
        this.underline = value;
    }
    
    /**
     * Abfrage, ob TextPart unterstrichen dargestellt wird
     * 
     * @return "true" = unterstrichen ein; "false" = unterstrichen aus
     */
    public boolean isUnderline() {
        return this.underline;
    }

    /**
     * TextPart "in Großbuchstaben" darstellen
     * 
     * @param value - "true" = Großbuchstaben ein; "false" = Großbuchstaben aus
     */
    public void setCapitals(boolean value) {
        this.capitals = value;
    }
    
    /**
     * Abfrage, ob TextPart in Großbuchstaben dargestellt wird
     * 
     * @return "true" = Großbuchstaben ein; "false" = Großbuchstaben aus
     */
    public boolean isCapitals() {
        return this.capitals;
    }

    /**
     * TextPart "in Umriss" darstellen
     * 
     * @param value - "true" = Umriss ein; "false" = Umriss aus
     */
    public void setOutline(boolean value) {
        this.outline = value;
    }
    
    /**
     * Abfrage, ob TextPart nur im Umriss dargestellt wird
     * 
     * @return "true" = Umriss ein; "false" = Umriss aus
     */
    public boolean isOutline() {
        return this.outline;
    }

    /**
     * TextPart "mit Schatten" darstellen
     * 
     * @param value - "true" = Schatten ein; "false" = Schatten aus
     */
    public void setShadow(boolean value) {
        this.shadow = value;
    }
    
    /**
     * Abfrage, ob TextPart mit Schatten dargestellt wird
     * 
     * @return "true" = Schatten ein; "false" = Schatten aus
     */
    public boolean isShadow() {
        return this.shadow;
    }

    /**
     * Bewegte Formatierung für diesen TextPart setzen
     * 
     * @param animtext TextPart.ANIMTEXT_*
     */
    public void setAnimtext(int animtext) {
        this.animtext = animtext;
    }

    /**
     * Abfrage, welche Schriftgröße für diesen TextPart gesetzt ist. Ist nichts gesetzt, so wird "0" zurückgegeben.
     * Die meisten RTF-Reader (wie z.B. Word) verwenden dann die Schriftgröße 10. 
     * 
     * @return Schriftgröße
     */
    public int getFontsize() {
        return this.fontsize;
    }

    /**
     * Setzt eine Schriftgröße für diesen TextPart
     * 
     * @param size - Schriftgröße in Punkten
     */
    public void setFontsize(int size) {
        this.fontsize = size;
    }

    /**
     * Gibt alle Schriftenobjekte zurück, die an diesem TextPart hängen.
     * Wenn keine Schriften angehängt sind, wird "null" zurückgegeben.
     * 
     * @return	- Set mit allen Fonts
     */
    public Set getAllFonts() {
        Set fs = new HashSet();
        if (getFont() != null) fs.add(getFont());
        return (fs.size() > 0 ? fs : null);
    }

    /**
     * Gibt das gesetzte Schriftartenobjekt für diesen TextPart zurück
     * 
     * @return Schirftarten Objekt
     */
    private Font getFont() {
        return this.font;
    }

    /**
     * Setzt eine Schriftart für diesen TextPart
     * 
     * @param font - Schrftarten Objekt
     */
    public void setFont(Font font) {
        this.font = font;
    }

    /**
     * Abfrage, ob eine bewegte Formatierung für diesen TextPart gesetzt ist
     * 
     * @return bewegte Formatierung
     */
    public int getAnimtext() {
        return this.animtext;
    }

    /**
     * Setzt den Inhalt dieses TextParts
     * 
     * @param value - TextPart Inhalt
     */
    public void setContent(String value) {
        this.content = value;
    }
    
    /* (non-Javadoc)
     * @see com.itseasy.rtf.text.Text#getRtfContent()
     */
    public String getRtfContent() {
        String format = (bold ? "\\b" : "") + (italic ? "\\i" : "") + (underline ? "\\ul" : "") + (capitals ? "\\caps" : "")
   	 				  + (outline ? "\\outl" : "") + (shadow ? "\\shad" : "")
   	 				  + (animtext > 0 ? "\\animtext" + animtext : "")
   	 				  + (font != null ? "\\f" + font.getFontnumber() : "")
        			  + (fontsize > 0 ? "\\fs" + (fontsize * 2) : "");
        
        return "{" + (format.length() > 0 ? format + " " : "")	// Wenn eine Formatierung angegeben ist, dann wird ein zusätzliches Leerzeichen eingefügt 
        	 + (rawrtf ? content : RtfUtil.getRTFString(content))
        	 + "}";		
    }
}
