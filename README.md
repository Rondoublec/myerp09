# MyERP : Mon Application de comptabilité

Test de build automatique.

## Context
Application de comptabilité, projet 9 du cursus de formation développeur Java d'OpenClassrooms.

GitHub : https://github.com/Rondoublec/myerp09

Travis *intégration continue* : [![Build Status](https://api.travis-ci.org/Rondoublec/myerp09.svg)](https://travis-ci.org/github/Rondoublec/myerp09)

Sonarcloud *analyse de code* : [![Coverage](https://sonarcloud.io/api/project_badges/measure?project=Rondoublec_myerp09&metric=coverage)](https://sonarcloud.io/dashboard?id=Rondoublec_myerp09)

## Description
Il s'agit d'une application composée de 4 modules
* Business (Logique Métier)
* Consumer (Accès aux données)
* Model (Modèle de données)
* Technical (Fonctions techniques réutilisables)

## Prérequis techniques 
Version de Java : 1.8  
JDK : jdk1.8.0_191  
Maven 3.6  
Base de données : 
PostgresSQL 11.2


## Organisation des dossiers

*   `bdd_myerp` : dump PostgreSQL de la base de données d'exemple (compatible avec les tests d'intégration)
*   `doc` : documentation
    *   `apidocs` : Javadoc
*   `docker` : répertoires relatifs aux conteneurs _docker_ utiles pour le projet
    *   `dev` : environnement de développement
*   `myerp-business` : *code source de l'application* Module Métier
*   `myerp-consumer` : *code source de l'application* Module Accès aux données
*   `myerp-model` : *code source de l'application* Module Modèle de données
*   `myerp-technical` : *code source de l'application* Module Composants transverses

## Autres fichiers
 *   `init_db_myerp_test.sql` : Ce fichier contient les commandes SQL si vous voulez reconstituer manuelle votre base PostgreSQL d'exemple. Lancez les commandes suivantes :  
psql -c "DROP DATABASE db_myerp;" -U postgres  
psql -c "CREATE USER usr_myerp WITH PASSWORD 'myerp';" -U postgres  
psql -c "CREATE DATABASE db_myerp;" -U postgres  
psql -c "GRANT ALL PRIVILEGES ON DATABASE db_myerp to usr_myerp;" -U postgres 
psql -U usr_myerp -d db_myerp -a -f init_db_myerp_test.sql

 *   `.travis.yml` : Ce fichier contient les éléments pour l'automatisation de la compilation avec Travis CI, ainsi que les informations de compte pour la connexion à Sonarcloud qui sont spécifiques au projet.  
organization: `"nom de votre repository"`  
-Dsonar.projectKey=`clé de votre projet sonar` -Dsonar.login=`token de connexion sonar`   
Base de données : 
PostgresSQL 11.2
    

## Si vous voulez utiliser docker (facultatif) comme environnement de développement

Les composants nécessaires lors du développement sont disponibles via des conteneurs _docker_.
L'environnement de développement est assemblé grâce à _docker-compose_
(cf docker/dev/docker-compose.yml).

Il comporte :

*   une base de données _PostgreSQL_ contenant un jeu de données de démo (`postgresql://127.0.0.1:9032/db_myerp`)

### Lancement

    cd docker/dev
    docker-compose up

### Arrêt

    cd docker/dev
    docker-compose stop

### Remise à zero

    cd docker/dev
    docker-compose stop
    docker-compose rm -v
    docker-compose up
