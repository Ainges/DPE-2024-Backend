# DPE-2024 Backend

Dieses Projekt ist Teil des DPE-2024-Projektes und basiert auf zwei Repositories: `DPE-2024-Backend` und `DPE-2024-Models`.

## Projektstruktur

Der Grund für die Verwendung von zwei Repositories ist, dass die ProcessEngine Spiff Workflows die Modelle in einem separaten Git-Repository verwaltet. 

## Empfohlene Einrichtung

Um das Projekt einzurichten, folgen Sie diesen Schritten:

1. Erstellen Sie ein Verzeichnis namens `DPE-2024`:
    ```bash
    mkdir DPE-2024
    cd DPE-2024
    ```
2. Die beiden Repositories clonen:
    ```bash
    git clone https://github.com/Ainges/DPE-2024-Backend.git
    git clone https://github.com/Ainges/DPE-2024-Models.git
    ```
3. Die Verzeichnisstruktur sollte wie folgt ausschauen:
    ```bash
    DPE-2024
    ├── DPE-2024-Backend
    └── DPE-2024-Models
    ```
    > **Hinweis:** Die genaue Struktur ist wichtig, damit Spiff die Änderungen korrekt in das Repository schreibt.
4. Navigiere zum Verzeichnis DPE-2024-Backend und starte Spiff mit Docker Compose:
    ```bash
    cd DPE-2024-Backend
    docker compose up -d
    ```
5. Spiff ist nun lokal unter `http://localhost:8001` erreichbar und die Modelle werden beim Speichern in das `DPE-2024-Models` Repository gesichert.

> **Warnung:** Prozessmodelle sind schwierig zu mergen bei Konflikten. Das Anpassen von gemeinsam genutzten Modellen sollte daher diszipliniert geschehen. Ziehe immer zuerst die neuesten Änderungen (`git pull`), bearbeite das Modell, und committe und pushe die Änderungen sofort (`git commit` und `git push`), um Konflikte zu vermeiden.

## Beispiele

### Beispiel Workflow

1. **Vorbereitung:**
    ```bash
    cd DPE-2024/DPE-2024-Models
    git pull
    cd ../DPE-2024-Backend
    git pull
    ```

2. **Änderungen vornehmen:**
    - Bearbeite die Modelle im `DPE-2024-Models` Repository.
    - Bearbeite die Backend-Logik im `DPE-2024-Backend` Repository.

3. **Änderungen speichern und pushen:**
    ```bash
    cd ../DPE-2024-Models
    git add .
    git commit -m "Beschreibung der Änderungen"
    git push
    cd ../DPE-2024-Backend
    git add .
    git commit -m "Beschreibung der Änderungen"
    git push
    ```

