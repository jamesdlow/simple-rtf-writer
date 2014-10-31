@echo off
rem *** Diese Batchdatei startet alle Beispiele der Reihe nach (auﬂer das Servlet-Beispiel).
rem *** Dabei werden verschiedene "out_*.rtf"-Dateien erzeugt und im Root-Verzeichnis der
rem *** Beispiele abgelegt.

java -cp ..\srw.jar;./TestSimpleRtf/ TestSimpleRtf
java -cp ..\srw.jar;./TestNewRtfDocFull/ TestNewRtfDocFull
java -cp ..\srw.jar;./TestComplexRtfTemplate/ TestComplexRtfTemplate
java -cp ..\srw.jar;./TestRtfTemplate/ TestRtfTemplate
java -cp ..\srw.jar;./TestRtfTemplateRahmen/ TestRtfTemplateRahmen
java -cp ..\srw.jar;./TestRtfSerienbrief/ TestRtfSerienbrief
java -cp ..\srw.jar;./TestRtfTemplateCache/ TestRtfTemplateCache


pause
