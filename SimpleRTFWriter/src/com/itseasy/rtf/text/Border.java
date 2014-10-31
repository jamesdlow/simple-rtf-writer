package com.itseasy.rtf.text;


/**
 * Definiert einen Rahmen für einen Absatz
 *
 * @version 0.1.0 	05.08.2004
 * @author 			Stefan Finkenzeller
 */
public class Border {
    /**
     * Kein Rahmen zeichnen
     */
    public static final int BORDER_NOTHING = 0;
    /**
     * Rahmen nur am oberen Rand zeichnen
     */
    public static final int BORDER_TOP = 1;
    /**
     * Rahmen nur am unteren Rand zeichnen
     */
    public static final int BORDER_BOTTOM = 2;
    /**
     * Rahmen nur am linken Rand zeichnen
     */
    public static final int BORDER_LEFT = 3;
    /**
     * Rahmen nur am rechten Rand zeichnen
     */
    public static final int BORDER_RIGHT = 4;
    /**
     * Rahmen komplett zeichnen (oben, unten, links und rechts)
     */
    public static final int BORDER_BOX = 5;
    
    
    /**
     * Dicke des Rahmens in Standardstärke 
     */
    public static final int BORDERTHICKNESS_DEFAULT = 0;
    /**
     * Dicke des Rahmens in einfacher Stärke (dünn)
     */
    public static final int BORDERTHICKNESS_SINGLE = 1;
    /**
     * Dicke des Rahmens in doppelter Stärke (fett)
     */
    public static final int BORDERTHICKNESS_DOUBLE = 2;
    
    // Variablen
    private int kind = BORDER_NOTHING;
    private int thickness = BORDERTHICKNESS_DEFAULT;
    
    /**
     * Definiert einen Rahmen für einen Absatz
     * 
     * @param kind	- Art des Rahmens (BORDER_*)
     * @param thickness - Dicke des Rahmens (BORDERTHICKNESS_*)
     */
    public Border(int kind, int thickness) {
        super();
        this.kind = kind;
        this.thickness = thickness;
    }

    /**
     * Gibt die RTF Definition dieses Rahmens zurück.
     * 
     * @return	String
     */
    public String getRtfContent() {
        StringBuffer format = new StringBuffer();
        // Art des Rahmens
        switch(kind) {
        	case BORDER_NOTHING: break;
        	case BORDER_TOP: format.append("\\brdrt"); break;
        	case BORDER_BOTTOM: format.append("\\brdrb"); break;
        	case BORDER_LEFT: format.append("\\brdrl"); break;
        	case BORDER_RIGHT: format.append("\\brdrr"); break;
        	case BORDER_BOX: format.append("\\box"); break;
        }
        // Dicke des Rahmens
        switch(thickness) {
    		case BORDERTHICKNESS_DEFAULT: break;
    		case BORDERTHICKNESS_SINGLE: format.append("\\brdrs"); break;
    		case BORDERTHICKNESS_DOUBLE: format.append("\\brdrth"); break;
        }
        // Genaue Dicke des Rahmens
        format.append("\\brdrw30");
        // Abstand des Rahmens vom Absatz
        format.append("\\brsp20");
        return format.toString();		
    }
}
