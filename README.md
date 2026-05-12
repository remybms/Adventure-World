# 🐉 Adventure World - Projet Final Web Full Stack

## Présentation
Ce projet consiste à enrichir une application de gestion d'aventuriers avec un système complexe de gestion de compétences et de prérequis. L'objectif est de garantir la cohérence métier (invariants) à travers toutes les couches de l'application.

## 📂 Structure du Monorepo
- **/documentation** : Contrat OpenAPI mis à jour et documents de conception.
- **/backend** : API Spring Boot (logique métier, persistence H2/MongoDB).
- **/frontend** : Interface utilisateur React.

## 📋 Suivi de projet

## 🚀 Fonctionnalités attendues

**Référentiel de compétences**
- [ ] **#F1** : GET /competences liste paginée  
- [ ] **#F2** : GET /competences/{id} détail avec prérequis résolus  
- [ ] **#F3** : POST /competences création  
- [ ] **#F4** : PUT /competences/{id} modification avec vérification de cohérence (Règle 2)  
- [ ] **#F5** : DELETE /competences/{id} suppression si aucun possesseur  

**Association compétences / aventuriers**
- [ ] **#F6** : GET /aventuriers/{id}/competences compétences acquises  
- [ ] **#F7** : POST /aventuriers/{id}/competences/{id} attribution avec vérification des prérequis (Règle 1)  
- [ ] **#F8** : DELETE /aventuriers/{id}/competences/{id} retrait conditionnel (Règle 3)  

**Vues enrichies**
- [ ] **#F9** : GET /aventuriers/{id}/competences/disponibles acquérables et bloquées  
- [ ] **#F10** : GET /competences/{id}/aventuriers possesseurs et éligibles  

**Frontend**
- [ ] **#F11** : Page liste des compétences  
- [ ] **#F12** : Page détail d’une compétence  
- [ ] **#F13** : Formulaire de création  
- [ ] **#F14** : Formulaire de modification avec gestion du 409  
- [ ] **#F15** : Vue enrichie du détail d’un aventurier (acquises / acquérables / bloquées)  

## ✨ Fonctionnalités Bonus
- [ ] **Bonus 1** : ?force=true sur PUT /competences/{id}  
- [ ] **Bonus 2** : Détection de cycle dans les prérequis  
- [ ] **Bonus 3** : Docker Compose (backend + frontend + MongoDB)  
- [ ] etc.

## Documentation
- [README Frontend](./frontend/README.md)
- [README Backend](./backend/README.md)

## 👥 Équipe
- Etienne LEMEE
- Rémy BAMAS LUNAY
- Marine RETAILLEAU