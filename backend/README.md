# Adventure World - BackEnd

Ce projet est une API backend développée avec Spring Boot.

---

## 🚀 Prérequis

- Java 17+
- Maven ou Gradle

---

## ⚙️ Variables d’environnement

L’application nécessite les variables d’environnement suivantes :

### APP_JWT_SECRET
Clé secrète utilisée pour signer les tokens JWT.

### APP_JWT_ISSUER
Émetteur des tokens JWT.

### APP_JWT_EXP_MINUTES
Durée d’expiration des tokens JWT (en minutes).

---

## ▶️ Lancer l’application

### Avec Maven

```bash
mvn clean compile

mvn spring-boot:run