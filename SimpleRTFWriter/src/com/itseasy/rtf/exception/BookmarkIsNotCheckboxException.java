
package com.itseasy.rtf.exception;

/**
 * BookmarkIsNotCheckbox - Dieser Fehler wird generiert, wenn eine Textmarke als Checkbox
 * angesprochen wird, obwohl sie keine Checkbox ist. Den Namen der Textmarke
 * kann man über die Methode e.getBookmarkname() abrufen. 
 *
 * @version 1.0 	23.06.2004
 * @author 			Stefan Finkenzeller
 */

public class BookmarkIsNotCheckboxException extends Exception {

    private String name;
    
    /**
     * @param name  - Name der Textmarke
     */
    public BookmarkIsNotCheckboxException(String name) {
        super("Bookmark '" + name + "' isn't a checkbox!");
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
