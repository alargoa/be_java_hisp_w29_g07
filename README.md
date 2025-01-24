# Project be_java_hisp_w29_g07

Welcome to the **be_java_hisp_w29_g07** project. This repository contains all documents and resources related to the development of the application.

---

## Table of Contents

- [Related Documents](#related-documents)
- [Seeds](#seeds)
- [Postman Collection](#postman-collection)
- [Swagger](#swagger)
- [Class Diagram](#class-diagram)

---

## Related Documents

All necessary documents can be found in the resources folder.

---

## Seeds

- <span style="color:blue;">**[Users.json](./src/main/resources/users.json)**</span>  - User data.
- <span style="color:green;">**[Posts.json](./src/main/resources/posts.json)**</span>  - Post data.

---

## Postman Collection

Access the collection in <span style="color:purple;">**Resources**</span> -> <span style="color:orange;">**Sprint1Collection**</span>.

---

## Swagger

You can access the API documentation through the following URL:

- <span style="color:teal;">**[Swagger UI](http://localhost:8080/swagger-ui/index.html#/)**</span>
  
---

## Class Diagram

To view the class diagram, check the following file:

- <span style="color:darkred;">**[classDiagram.md](./src/main/resources/classDiagram.md)**</span>

### Class Diagram

```mermaid
classDiagram
    class User {
        -Integer id
        -String username
        -String name
        -String lastname
        -String email
        -Enum userType
    }
    class Follow {
        -Integer id
        -User follower
        -User followed
        -LocalDate followDate
    }
    class Product {
        -Integer id
        -String name
        -String type
        -String brand
        -String color
        -String notes
    }
    class Post {
        -Integer id
        -Integer userId
        -LocalDate date
        -Product product
        -Integer category
        -Double price
        -Boolean hasPromo
        -Double discount
    }
    class Category {
        -Integer id
        -String name
    }
    Post --> Product
    Product --> Category
    Follow --> User
    Post --> User
