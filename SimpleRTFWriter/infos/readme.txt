
			Simple RTF Writer (SRW) Version 0.6
				 Release Notes



** Kurzbeschreibung:
Schlanke Java-Bibliothek mit der man einfache RTF Dokumente generieren oder in bestgehenden RTF Dokumenten Textmarken und Felder ausf�llen kann.

** Text:
Das Standardausgabeformat im Internet ist unbestreitbar PDF. Allerdings ist es nicht in jedem Fall das ideale Format. Will man dem Empf�nger die M�glichkeit geben das generierte Dokument zu bearbeiten, so muss ist teure Zusatzsoftware auf dem Server oder PC notwendig oder man verwendet ein anderes Format. Ein guter Kompromiss � vor allem innerhalb von Firmennetzen � stellt das �Rich Text Format� (RTF) von Microsoft dar. Dieses Format wird auch vom �Simple RTF Writer� (SRW) unterst�tzt. Bei RTF handelt es sich um ein allgemeines Format zum Austausch von Textdokumenten. Es benutzt zum Dokumentenaustausch nur die darstellbaren Zeichen des ASCII-Zeichensatzes und kann von Microsoft Word (ab Version 4.0), wie auch vom kostenlosen OpenOffice gelesen werden.

Der SRW bietet zwei M�glichkeiten RTF Dokumente zu erstellen:
1. Generieren von einfachen RTF Dokumenten (RTFDocument)
2. F�llen von Textmarken und Formularfeldern in einem bestehenden RTF Dokument (RTFTemplate)

Gerade die zweite M�glichkeit er�ffnet eine F�lle von Einsatzszenarien. Sie entwerfen eine Vorlage/Formular in Word, markieren die Stellen mit Textmarken (oder Formularfeldern) die mit Inhalt gef�llt werden sollen und speichern das Ganze als RTF Dokument. Danach kann durch ihr Java-Programm und mittels des �Simple RTF Writer� (SRW) die Vorlage mit Inhalt gef�llt werden.

** SRW Feature:
- Beliebige Papiergr��endefinitionen m�glich (nur RTFDocument)
- Verschiedene Schriftarten und �gr��en
- Textformatierungen: kursiv, unterstrichen, fett, schatten, blinkend
- Abs�tze mit Links-, Rechts-, Mitte- oder Blockausrichtung
- Abs�tze mit Rahmen
- Aufz�hlungslisten
- Tabulatoren mit Links-, Rechts-, Mitte- oder Dezimalausrichtung
- Ausgabe direkt speichern oder als Byte-Array (zur Ausgabe in Servlets)
- Kleiner Footprint, nur 29 kB

Mit Version 0.6 wurde ein Redesign der internen Strukturen durchgef�hrt. Dabei wurde u.a. das Basispackage "com.itseasy.rtf.data" in "com.itseasy.rtf.text" umbenannt. Weitere �nderungen k�nnen Sie aus der Changelog.txt Datei entnehmen.


** Einfaches Beispiel zum F�llen eines RTF Formulars:

     // RTF Template lesen und Textmarken f�llen
     RTFTemplate doc = new RTFTemplate(new File("c:/rtftest_template.rtf"));
     // Text f�r Felder einf�gen
     doc.setBookmarkContent("Nummer", "08/15");
     doc.setBookmarkContent("Von", "Mustermann GmbH, M�nchen");
     // Checkboxen mit Inhalt f�llen
     doc.setBookmarkCheckbox("Bar", true);
     // Dokument wieder speichern
     doc.save(new File("c:/rtftest2.rtf"));


Genauso kann man �ber folgenden Code in einem Servlet das RTF-Dokument bearbeiten:

   public void doPost(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {

	byte[] content; 
	OutputStream out = response.getOutputStream(); 
	try { 
             // RTF Template lesen und Textmarken f�llen 
             RTFTemplate doc = new RTFTemplate(new File("c:/rtftest_template.rtf")); 
             // Text f�r Felder einf�gen 
             doc.setBookmarkContent("Nummer", "08/15"); 
             doc.setBookmarkContent("Von", "Mustermann GmbH, M�nchen"); 
             // Checkboxen mit Inhalt f�llen
             doc.setBookmarkCheckbox("Bar", true); 
             // Dokument lesen und an Browser zur�ckgeben 
             content = doc.getDocument(); 
             response.setContentType("application/msword"); 
	} catch (Exception e) { 
             response.setContentType("text/html");
             content = e.getMessage().getBytes(); 
        } 
	response.setContentLength(content.length); 
        out.write(content);           
        out.flush();
        out.close();
   }


** Arbeitsweise:
Der Simple RTF Writer (SRW) hat einen sehr kleinen Footprint und ist schnell. Die komplette Verarbeitung von RTF Dokumenten findet im Hauptspeicher statt. Erst am Ende der Verarbeitung, wenn das Dokument gespeichert wird oder als Byte-Array angefordert wird, beginnt der SRW mit dem Generieren des RTF Dokumentes. Bis zu diesem Zeitpunkt k�nnen somit noch �nderungen am Text vorgenommen werden. Tempor�re Dateien werden nicht angelegt.


** Support:
Auftretende Probleme, Bugs und Anregungen bitte an mailto:support@it-s-easy.com melden.

Danke.

Ihr IT's easy Team
