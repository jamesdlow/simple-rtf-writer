package com.itseasy.rtf;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Die Klasse RTFAbstractDocument ist die Basisklasse für RTFDocument und RTFTemplate. Sie wird normalerweise
 * nur für interne Zwecke verwendet und tritt nicht außen in Erscheinung.
 *
 * @version 0.5.0 	30.08.2004
 * @author 			Stefan Finkenzeller
 */

abstract class RTFAbstractDocument {
    // RTF Constants
    protected static final String APPVERSION = "0.6";
    protected static final int RTFVERSION = 1;
    protected static final String GENERATOR = "SimpleRTFWriter V" + APPVERSION + " by it-s-easy.com";

    // Abstrakte Methoden
    protected abstract String getDocumentAsString();
   
    /**
     * Gibt das komplette RTF Dokument als char-Array zurück
     * 
     * @return RTF Dokument als char-Array
     */
    public byte[] getDocument() {
        return getDocumentAsString().getBytes();
    }
    
    /**
     * Speichert das RTF Dokument auf Platte
     * 
     * @param file
     */
    public void save(File file) throws IOException {
        BufferedWriter out = new BufferedWriter(new FileWriter(file));
        out.write(getDocumentAsString());
        out.close();
    }
}
