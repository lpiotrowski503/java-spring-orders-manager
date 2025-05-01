## ✅ Zakres funkcji dla auth-service

    -   Rejestracja użytkownika
    -   Logowanie (JWT)
    -   Resetowanie hasła przez email z linkiem
    -   Walidacja pól
    -   (Opcjonalnie później) Role, potwierdzenie emaila itd.


## 🔧 Struktura pakietów w auth-service

```lua
    com.example.auth
    ├── AuthApplication.java
    ├── controller         <-- REST kontrolery
    ├── dto                <-- Obiekty do przesyłania danych (request/response)
    ├── entity             <-- Encje JPA
    ├── repository         <-- Interfejsy do komunikacji z bazą
    ├── service            <-- Logika biznesowa
    ├── config             <-- Konfiguracje: security, JWT, mail itd.
    ├── exception          <-- Własne wyjątki i handler
    └── util               <-- Pomocnicze klasy (np. token utils, email utils)
```

## 🔧 Struktura pakietów w order-service

```lua
    orders-manager/
    ├── auth-service/
    ├── order-service/
    │   ├── src/main/java/com/example/order/
    │   │   ├── controller/
    │   │   ├── service/
    │   │   ├── repository/
    │   │   ├── entity/
    │   │   ├── dto/
    │   │   └── OrderServiceApplication.java
    │   └── src/main/resources/
    │       └── application.properties

```