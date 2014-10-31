package com.itseasy.rtf.text;

import com.itseasy.rtf.RtfUtil;

/**
 * Steht f�r eine Papiergr��e und wird im Konstruktor des RTFDocument verwendet. Neben DIN_A5,
 * DIN_A4 und DIN_A3, sind auch amerikanische Gr��en wie LETTER und LEGAL vordefiniert. Sondergr��en k�nnen
 * selbst kreiert werden. 
 *
 * @version 0.2.0 	31.05.2008
 * @author 			James Low
 */

public class PageSize {
    /**
     * Vordefinierte Papiergr��e f�r <code>DIN_A5</code> (210 mm x 148 mm)
     */
    public static final PageSize DIN_A5 = new PageSize(210, 148);
    /**
     * Vordefinierte Papiergr��e f�r <code>DIN_A4</code> (297 mm x 210 mm)
     */
    public static final PageSize DIN_A4 = new PageSize(297, 210);
    /**
     * Vordefinierte Papiergr��e f�r <code>DIN_A4_QUER</code> (210 mm x 297 mm)
     */
    public static final PageSize DIN_A4_QUER = new PageSize(210, 297);
    /**
     * Vordefinierte Papiergr��e f�r <code>DIN_A3</code> (420 mm x 297 mm)
     */
    public static final PageSize DIN_A3 = new PageSize(420, 297);
    /**
     * Vordefinierte Papiergr��e f�r <code>LETTER</code> (279,4 mm x 215,9 mm)
     */
    public static final PageSize LETTER = new PageSize(279.4, 215.9);
    /**
     * Vordefinierte Papiergr��e f�r <code>LEGAL</code> (355,6 mm x 215,9 mm)
     */
    public static final PageSize LEGAL = new PageSize(355.6, 215.9);
    /**
     * Vordefinierte Papiergr��e f�r <code>EXECUTIVE</code> (266,7 mm x 184,1 mm)
     */
    public static final PageSize EXECUTIVE = new PageSize(266.7, 184.1);
    
    // Interne Variablen
    private int height = 0;		// Einheit ist twip (1 twip = 1/20 point oder 1/1440 Zoll
    private int width = 0;		// Einheit ist twip (1 twip = 1/20 point oder 1/1440 Zoll
    private boolean portrait = true;
    
    /**
     * Kreiert eine neue Papiergr��e. Die Werte m�ssen in Millimeter angegeben werden.
     * 
     * @param height - H�he in Millimetern
     * @param width  - Breite in Millimetern
     */
    public PageSize(double height, double width) {
        this.height = RtfUtil.getTwipFromMillimeter(height);
        this.width = RtfUtil.getTwipFromMillimeter(width);
    }
    
    /**
     * Kreiert eine neue Papiergr��e. Die Werte m�ssen in Millimeter angegeben werden.
     * 
     * @param height - H�he in Millimetern
     * @param width  - Breite in Millimetern
     */
    public PageSize(double height, double width, boolean portrait) {
        this(height,width);
        this.portrait = portrait;
    }
    
    /**
     * Gibt die H�he der Seite in Twips zur�ck
     * 
     * @return Seitenh�he (in Twips)
     */
    public int getHeight() {
    	if (portrait) {
    		return height;
    	} else {
    		return width;
    	}
    }
    
    /**
     * Gibt die Breite der Seite in Twips zur�ck
     * 
     * @return Seitenbreite (in Twips)
     */
    public int getWidth() {
    	if (portrait) {
    		return width;
    	} else {
    		return height;
    	}
    }
}
