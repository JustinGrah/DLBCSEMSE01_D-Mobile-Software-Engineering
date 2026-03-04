# Projekt: Mobile Software Engineering II
Kurskürzel: SLBSCSEMSE02_D

## Aufgabenstellung:
Entwicklung einer Android app für das planen eines spieleabends.

## User-Stories:
Userstories stellen die Anforderung an die App und welche funktionen minmal benötigt werden. 
Diese sind wie folgt:
- Als Spieler:in möchte ich immer wissen, wann und wo der nächste Spieltermin stattfindet, um ihn nicht zu verpassen.
- Als Spieler:in möchte ich, dass sich die Gruppe immer turnusmäßig bei einem:einer anderen Spieler:in zu Hause trifft, damit die mit der Einladung verbundenen Aufwände gleich verteilt sind.
- Als Spieler:in möchte ich ein oder mehrere Spiele vorschlagen können, um den Spieleabend mitgestalten zu können.
- Als Spieler:in möchte ich im Vorfeld des Termins über die vorgeschlagenen Spiele abstimmen können, um den Spieleabend mitgestalten zu können.
- Als Spieler:in möchte ich den:die Gastgeber:in, das Essen und den Abend allgemein im Anschluss an den Termin bewerten können.
- Als Spieler:in möchte ich allen anderen Spieler:innen schnell eine Nachricht zukommen lassen können, um mich entschuldigen zu können, falls ich mich verspäten sollte.
- Optional: Als Spieler:in möchte ich in Vorbereitung des Fast-Food-Bestellprozesses rechtzeitig daran erinnert werden, meine Lieblingsessensrichtung zu wählen (Italienisch, Griechisch, Türkisch usw.).
- Optional: Als Gastgeber:in möchte ich rechtzeitig über die mehrheitlich gewünschte Essensrichtung informiert werden, um einen passenden lokalen Lieferdienst auszusuchen.
- Optional: Als Spieler:in möchte ich rechtzeitig das Menü des ausgewählten Lieferdienstes sehen können, um rechtzeitig meine Bestellung an den:die Gastgeber:in übermitteln zu können.

### User-Stories mit Storypoints und priorität
| User Story                                                                 | MoSCoW | Story Points |
|----------------------------------------------------------------------------|--------|--------------|
| Nächster Spieltermin sichtbar                                              | MUST   | 3            |
| Nachricht an Gruppe senden (Verspätung)                                    | MUST   | 3            |
| Spiele vorschlagen                                                         | MUST   | 5            |
| Über vorgeschlagene Spiele abstimmen                                       | MUST   | 5            |
| Gastgeberrotation                                                          | SHOULD | 5            |
| Gastgeber/Essen/Abend bewerten                                             | SHOULD | 8            |
| Essensrichtung wählen (Erinnerung)                                         | COULD  | 3            |
| Gastgeber über gewünschte Essensrichtung informieren                       | COULD  | 2            |
| Menü des Lieferdienstes einsehen                                           | COULD  | 5            |

## Backlog
Identifizierte Backlog elemente (werden in ISSUES gepflegt und zugewiesen)
| Titel                                      | User Story                                                                                   | Akzeptanzkriterien                                                                                                | MoSCoW | Story Points |
|--------------------------------------------|----------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------|--------|--------------|
| Nächster Spieltermin                       | Als Spieler:in möchte ich immer wissen, wann und wo der nächste Spieltermin stattfindet.     | Termin wird angezeigt; enthält Datum, Uhrzeit, Ort; Änderungen aktualisieren die Anzeige automatisch.             | MUST   | 3            |
| Schnellnachricht an Gruppe                 | Als Spieler:in möchte ich allen anderen Spieler:innen schnell eine Nachricht senden können.  | Nachricht kann eingegeben und an alle gesendet werden; funktioniert mobil und am Desktop.                         | MUST   | 3            |
| Spiele vorschlagen                         | Als Spieler:in möchte ich ein oder mehrere Spiele vorschlagen können.                        | Spieleingabe möglich; Vorschläge werden gespeichert; mehrere Vorschläge pro Person möglich.                       | MUST   | 5            |
| Spiele abstimmen                           | Als Spieler:in möchte ich über vorgeschlagene Spiele abstimmen können.                       | Liste aller Vorschläge sichtbar; jede Person kann abstimmen; Ergebnis wird automatisch berechnet.                 | MUST   | 5            |
| Gastgeberrotation                          | Als Spieler:in möchte ich, dass sich die Gruppe turnusmäßig bei jemand anderem trifft.       | System berechnet nächste:n Gastgeber:in; Reihenfolge nachvollziehbar; Ausnahmen können berücksichtigt werden.     | SHOULD | 5            |
| Abendbewertung                             | Als Spieler:in möchte ich Gastgeber:in, Essen und Abend bewerten können.                     | Bewertungsskala verfügbar; optionaler Kommentar; Bewertungen werden gespeichert und einsehbar.                    | SHOULD | 8            |
| Essensrichtung wählen                      | Als Spieler:in möchte ich rechtzeitig daran erinnert werden, meine Essensrichtung zu wählen. | Erinnerung erfolgt rechtzeitig; Auswahl zwischen mehreren Richtungen; Auswahl wird gespeichert.                   | COULD  | 3            |
| Essensrichtung-Info für Gastgeber          | Als Gastgeber:in möchte ich über die gewünschte Essensrichtung informiert werden.            | System zeigt meistgewählte Essensrichtung; Anzeige erfolgt rechtzeitig; Änderungen werden berücksichtigt.         | COULD  | 2            |
| Lieferdienstmenü anzeigen                  | Als Spieler:in möchte ich das Menü des Lieferdienstes sehen können.                          | Menü wird angezeigt; Kategorien und Preise sichtbar; Bestellung kann an Gastgeber:in übermittelt werden.          | COULD  | 5            |

