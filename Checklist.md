# Checkliste

## allgemeine Hinweise zur Checkliste
Bitte beachten Sie, dass die Checkliste eine dynamische Grundlage ist, die im Laufe des Semesters angepasst oder erweitert werden kann. Zudem sind die Punkte der Checkliste nicht stets gleichgewichtend; einige Aspekte könnten schwerwiegender sein als andere, abhängig von den Anforderungen des jeweiligen Projekts.

Wenn bestimmte Punkte der Checkliste in Ihrem Projekt nicht umgesetzt werden, ist es erforderlich, dass Sie begründen, warum diese für Ihr gewähltes Beispiel nicht relevant sind. Dies dient nicht nur der Vollständigkeit, sondern auch der Qualität der Dokumentation und wird bei der finalen Beurteilung berücksichtigt.

Es wird dringend empfohlen, diese Checkliste in Ihr eigenes Projekt-Repository zu kopieren und dort als eigenständige Datei zu führen. Auf diese Weise können Sie den Fortschritt Ihres Projekts kontinuierlich und transparent nachverfolgen. Neben dem Abhaken der erledigten Punkte ist es ebenfalls ratsam, fortlaufend zu dokumentieren, wie Sie die einzelnen Inhalte konkret implementiert haben. Diese Dokumentation kann sowohl als Lernressource für Sie selbst dienen als auch den Lehrenden und Kolleg:innen einen detaillierten Einblick in Ihre Arbeitsweise geben. Durch diese fortlaufende Dokumentation erleichtern Sie sich zudem die abschließende Projektdokumentation und stellen sicher, dass Ihr Projekt im Rahmen der Beurteilung vollumfänglich gewürdigt werden kann.

## Checkliste für die eigene fortlaufende Übung in Continuous Delivery

### Einführung und Grundlagen
- [x] Verständnis von Continuous Delivery und dessen Bedeutung
  - Regelmäßiges, automatisiertes Ausliefern von Software 
  - Wichtig für schnelle und effiziente Updates
- [x] Unterschiede zwischen Continuous Integration, Continuous Delivery und Continuous Deployment
  - CI: Code oft mergen, sofort testen 
  - CD (Continuous Delivery): Automatisiert bis zur Produktion, manueller Release 
  - Continuous Deployment: Automatischer Live-Release nach Hauptbranch-Merge
- [x] CI-Anti Pattern identifizieren
    zb:
    - Keine automatisierten Tests
    - Seltene Commits, große Änderungen 
    - Unzureichende Tests 
    - Mangelnde Kommunikation im Team
    - etc

### Automatisierung
- [ ] Automatisierte Builds eingerichtet
- [ ] Automatisierte Tests implementiert
- [ ] Automatisierte Deployments konfiguriert

### Testing
- [ ] Unit Tests geschrieben und automatisiert
- [ ] Integrationstests implementiert (optional)
- [ ] End-to-End Tests eingerichtet (optional)

### Deployment-Strategien
- [ ] Deployment-Strategien identifizieren
- [ ] Rollback-Strategien (optional)

### Containerisierung
- [ ] Docker oder ähnliche Technologien eingesetzt
- [ ] Integration in eine Build-Pipeline

### Konfigurationsmanagement
- [ ] Konfigurationsdateien versioniert und zentralisiert
- [ ] Verwendung in einer Build-Pipeline

### Feedback-Schleifen & Benachrichtigungen
- [ ] Feedback von Stakeholdern eingeholt und implementiert
- [ ] Developer Benachrichtigungen

### Sicherheit
- [ ] Zugangsdaten sicher hinterlegt

### Datenbanken
- [ ] Datenbank-Migrationen automatisiert
- [ ] Datenbank-Backups und Recovery-Pläne

### Abschluss und Dokumentation
- [ ] Projekt-Dokumentation vervollständigt
