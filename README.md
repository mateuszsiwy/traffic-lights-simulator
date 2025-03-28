# Traffic Lights Simulator

## Spis treści
- [Opis projektu](#opis-projektu)
- [Architektura](#architektura)
- [Algorytm świateł drogowych](#algorytm-świateł-drogowych)
- [Instalacja](#instalacja)
- [Uruchomienie](#uruchomienie)
- [API](#api)
- [Format pliku wejściowego](#format-pliku-wejściowego)
- [Przykład użycia](#przykład-użycia)

## Opis projektu
Inteligentny symulator świateł drogowych implementujący algorytm priorytetyzacji ruchu na skrzyżowaniu. System obsługuje zarówno ruch pojazdów jak i pieszych, dynamicznie dostosowując się do natężenia ruchu w każdym kierunku.

## Ważne informacje
Poniższa sekcja Architektura opisuje webowy aspekt aplikacji, lecz zgodnie z wymaganiami zadania można też uruchamiać projekt podając input.json i output.json w terminalu. W tym celu należy:
```bash
cd backend
./mvnw clean package
java -jar  .\target\traffic-lights-simulator-0.0.1-SNAPSHOT.jar input.json output.json
```

## Architektura
Projekt składa się z dwóch głównych komponentów:
- **Backend** (Spring Boot)
  - Logika biznesowa symulacji
  - REST API do obsługi żądań
  - Implementacja algorytmu świateł
- **Frontend** (React)
  - Interfejs użytkownika
  - Wizualizacja wyników symulacji
  - Obsługa przesyłania plików JSON

## Algorytm świateł drogowych

### Komponenty
- **Skrzyżowanie** (`Intersection`)
  - 4 drogi (N, S, E, W)
  - 4 przejścia dla pieszych
  - Sygnalizacja dla pojazdów i pieszych
  - System zarządzania ruchem

### System priorytetyzacji
1. **Wagi uczestników ruchu**
   - Początkowa waga: 1
   - Zwiększanie wagi: ×2 w każdym cyklu oczekiwania
   - Zapobiega głodzeniu uczestników ruchu

2. **Proces decyzyjny**
   ```
   dla każdego kierunku:
     oblicz sumę wag pojazdów
     oblicz sumę wag pieszych
     znajdź kierunek z najwyższą wagą
   jeśli wszystkie światła czerwone:
     wybierz kierunek z najwyższą wagą
     włącz zielone światło dla tego kierunku
     włącz zielone światło dla przeciwnego kierunku (jeśli ma uczestników ruchu)
   ```

3. **Zasady bezpieczeństwa**
   - Czerwone światło we wszystkich kierunkach przed zmianą
   - Synchronizacja świateł dla pieszych i pojazdów
   - Maksymalnie 1 uczestnik ruchu na cykl
   - Ograniczony czas zielonego światła

### Obsługa uczestników ruchu
1. **Pojazdy**
   - Obsługa skrętów (lewo, prawo, prosto)
   - System kolejkowania FIFO
   - Priorytetyzacja na podstawie wag

2. **Piesi**
   - Obsługa przejść w dwóch kierunkach
   - System kolejkowania FIFO
   - Priorytetyzacja na podstawie wag

## Instalacja

### Backend
```bash
cd backend
./mvnw clean install
```

### Frontend
```bash
cd frontend
npm install
```

## Uruchomienie

### Backend (lokalnie)
```bash
cd backend
./mvnw spring-boot:run
```

### Frontend (lokalnie)
```bash
cd frontend
npm start
```

### Docker
```bash
docker build -t traffic_lights_simulator  .
docker run -p 8080:8080 traffic_lights_simulator 
```

## API
- **POST** `/api/simulation/upload`
  - Przyjmuje plik JSON z komendami symulacji
  - Zwraca wyniki w formacie JSON

## Format pliku wejściowego
```json
{
  "commands": [
    {
      "type": "addVehicle",
      "vehicleId": "vehicle1",
      "startRoad": "north",
      "endRoad": "south"
    },
    {
      "type": "addPedestrian",
      "pedestrianId": "pedestrian1",
      "origin": "north_east",
      "destination": "south_east"
    },
    {
      "type": "step"
    }
  ]
}
```

## Przykład użycia
1. Przygotuj plik JSON z komendami symulacji
2. Otwórz aplikację w przeglądarce
3. Prześlij plik przez interfejs
4. Otrzymasz wyniki w formacie JSON pokazujące status każdego kroku symulacji

## Wdrożenie
- Backend: https://traffic-lights-simulator.onrender.com
- Frontend: https://traffic-lights-simulator.vercel.app

## Autor
Mateusz Siwy

## Licencja
MIT
