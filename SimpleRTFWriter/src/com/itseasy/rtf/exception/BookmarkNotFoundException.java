package com.itseasy.rtf.exception;

/**
 * BookmarkNotFoundException - Fehler, wenn keine Textmarke gefunden wurde. Den Namen der Textmarke
 * kann man über die Methode e.getBookmarkname() abrufen. 
 *
 * @version 1.0 	23.06.2004
 * @author 			Stefan Finkenzeller
 */

public class BookmarkNotFoundException extends Exception {
    // Bookmarkname
    private String name;
    
    /**
     * @param name	- Name der Textmarke
     * @param text  - Fehlertext
     */
    public BookmarkNotFoundException(String name, String text) {
        super(text);
        this.name = name;
    }

    /**
     * Gibt den Namen der Textmarke zurück, durch den der Fehler ausgelöst wurde.
     * 
     * @return Name der Textmarke
     */
    public String getBookmarkname() {
        return this.name;
    }
}
