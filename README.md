# 🛍️ Product Management API - Spring Boot Application

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.6-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![MySQL](https://img.shields.io/badge/MySQL-Database-blue.svg)](https://www.mysql.com/)
[![Maven](https://img.shields.io/badge/Maven-Build%20Tool-red.svg)](https://maven.apache.org/)

## 📝 Description

Une application Spring Boot simple qui démontre les concepts fondamentaux du développement d'APIs REST avec Spring Data
JPA. Cette application gère un système de produits avec des opérations CRUD et des requêtes personnalisées.

## 🏗️ Architecture du Projet

```
src/
├── main/
│   ├── java/net/zakaria/firstspringbootapp/
│   │   ├── entities/
│   │   │   └── Product.java           # 📦 Entité JPA
│   │   ├── repositories/
│   │   │   └── ProductRepository.java # 🗄️ Interface de données
│   │   ├── web/
│   │   │   └── ProductRestService.java # 🌐 Contrôleur REST
│   │   └── FirstSpringBootAppApplication.java # ⚡ Classe principale
│   └── resources/
│       └── application.properties     # ⚙️ Configuration
└── pom.xml                           # 📋 Dépendances Maven
```

## 📦 Entité Product

Le fichier `Product.java` définit l'entité principale de l'application :

```java

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double price;
    private int quantity;
}
```

### 🔧 Annotations utilisées :

- `@Entity` : Marque la classe comme une entité JPA
- `@Data` : Génère automatiquement les getters, setters, toString, etc. (Lombok)
- `@NoArgsConstructor` / `@AllArgsConstructor` : Génère les constructeurs (Lombok)
- `@Builder` : Implémente le pattern Builder (Lombok)
- `@Id` : Définit la clé primaire
- `@GeneratedValue` : Auto-incrémentation de l'ID

## 🗄️ Repository - ProductRepository

L'interface `ProductRepository.java` étend `JpaRepository` et fournit des méthodes de requête personnalisées :

```java
public interface ProductRepository extends JpaRepository<Product, Long> {
    // 🔍 Recherche par nom (contient)
    List<Product> findByNameContains(String mc);

    // 💰 Recherche par prix supérieur
    List<Product> findByPriceGreaterThan(double price);

    // 📝 Requête JPQL personnalisée pour la recherche
    @Query("select p from Product p where p.name like :x")
    List<Product> search(@Param("x") String mc);

    // 💵 Requête JPQL pour le prix
    @Query("select p from Product p where p.price > :x")
    List<Product> searchByPrice(@Param("x") double price);
}
```

### ✨ Fonctionnalités :

- **Méthodes dérivées** : Spring Data génère automatiquement l'implémentation
- **Requêtes JPQL** : Requêtes personnalisées avec `@Query`
- **Paramètres nommés** : Utilisation de `@Param` pour la sécurité

## 🌐 Contrôleur REST - ProductRestService

Le fichier `ProductRestService.java` expose les endpoints REST :

```java

@RestController
public class ProductRestService {
    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/products")
    public List<Product> products() {
        return productRepository.findAll();
    }

    @GetMapping("/products/{id}")
    public Product findProduct(@PathVariable Long id) {
        return productRepository.findById(id).orElse(null);
    }
}
```

### 🛣️ Endpoints disponibles :

- `GET /products` : Récupère tous les produits
- `GET /products/{id}` : Récupère un produit par son ID

## ⚡ Classe Principale - FirstSpringBootAppApplication

La classe principale `FirstSpringBootAppApplication.java` démarre l'application et teste les fonctionnalités :

```java

@SpringBootApplication
public class FirstSpringBootAppApplication implements CommandLineRunner {

    @Autowired
    private ProductRepository productRepository;

    public static void main(String[] args) {
        SpringApplication.run(FirstSpringBootAppApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // 🧪 Tests des différentes méthodes du repository
        // Affichage de tous les produits
        // Tests de recherche par nom et prix
    }
}
```

### 🧪 Tests inclus :

- ✅ Affichage de tous les produits
- ✅ Recherche d'un produit par ID
- ✅ Recherche par nom (contient "C")
- ✅ Recherche JPQL avec pattern ("%S%")
- ✅ Recherche par prix supérieur à 1200

## ⚙️ Configuration - application.properties

Le fichier `application.properties` configure l'application :

```properties
# 🏷️ Nom de l'application
spring.application.name=FirstSpringBootApp
# 🌐 Port du serveur
server.port=8085
# 🗃️ Configuration MySQL
spring.datasource.url=jdbc:mysql://localhost:3306/product_db?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=
# 🔧 Configuration Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MariaDBDialect
# 💾 Configuration H2 (commentée)
#spring.datasource.url=jdbc:h2:mem:product-db
#spring.h2.console.enabled=true
```

### 🎯 Points clés :

- **Port personnalisé** : 8085 au lieu de 8080
- **Base de données** : MySQL avec création automatique
- **Mode Hibernate** : `update` pour la mise à jour du schéma
- **Alternative H2** : Configuration en mémoire disponible (commentée)

## 📋 Dépendances Maven - pom.xml

Le fichier `pom.xml` définit les dépendances du projet :

```xml

<dependencies>
    <!-- 🌱 Spring Boot Starters -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <!-- 🗄️ Bases de données -->
    <dependency>
        <groupId>com.mysql</groupId>
        <artifactId>mysql-connector-j</artifactId>
        <scope>runtime</scope>
    </dependency>
    <dependency>
        <groupId>com.h2database</groupId>
        <artifactId>h2</artifactId>
        <scope>runtime</scope>
    </dependency>

    <!-- 🛠️ Outils de développement -->
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>
</dependencies>
```

## 🚀 Installation et Exécution

### Prérequis

- ☕ **Java 17** ou supérieur
- 🗃️ **MySQL** en fonctionnement (localhost:3306)
- 📦 **Maven** pour la gestion des dépendances

### Étapes d'installation

1. **Cloner le repository** :

```bash
git clone https://github.com/zakaria-bouzouba/first-spring-boot-app.git
cd first-spring-boot-app
```

2. **Configurer la base de données** :
    - Démarrer MySQL
    - La base `product_db` sera créée automatiquement

3. **Compiler et exécuter** :

```bash
mvn clean install
mvn spring-boot:run
```

4. **Tester l'application** :
    - 🌐 API disponible sur : `http://localhost:8085`
    - 📋 Tous les produits : `http://localhost:8085/products`
    - 🔍 Produit par ID : `http://localhost:8085/products/1`

## 🧪 Tests API avec curl

```bash
# Récupérer tous les produits
curl -X GET http://localhost:8085/products

# Récupérer un produit spécifique
curl -X GET http://localhost:8085/products/1
```

## 📚 Technologies Utilisées

| Technologie         | Version | Usage                         |
|---------------------|---------|-------------------------------|
| ☕ Java              | 17      | Langage principal             |
| 🌱 Spring Boot      | 3.5.6   | Framework principal           |
| 🗄️ Spring Data JPA | -       | Accès aux données             |
| 🐬 MySQL            | -       | Base de données principale    |
| 💾 H2 Database      | -       | Base de données de test       |
| 🛠️ Lombok          | -       | Réduction du code boilerplate |
| 📦 Maven            | -       | Gestion des dépendances       |

## 🔄 Améliorations Possibles

- ✨ Ajouter des endpoints POST, PUT, DELETE
- 📊 Ajouter la validation des données
- 📖 Intégrer Swagger pour la documentation API

## 👨‍💻 Auteur

**Zakaria Bouzouba** - [GitHub](https://github.com/zakaria-bouzouba)