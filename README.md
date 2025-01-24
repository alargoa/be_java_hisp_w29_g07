# Proyecto be_java_hisp_w29_g07

Bienvenido al proyecto **be_java_hisp_w29_g07**. Este repositorio contiene todos los documentos y recursos relacionados con el desarrollo de la aplicación.

---

## Índice

- [Documentación relacionada](#documentación-relacionada)
- [Seeds](#seeds)
- [Colección Postman](#colección-postman)
- [Swagger](#swagger)
- [Diagrama de Clases](#diagrama-de-clases)

---

## Documentación relacionada

Todos los documentos necesarios se pueden encontrar en la carpeta de recursos.

---

## Seeds

- **[Users.json](./src/main/resources/users.json)**  - Datos de usuarios.
- **[Posts.json](./src/main/resources/posts.json)**  - Datos de publicaciones.

---

## Colección Postman

Accede a la colección en **Recursos** -> **Sprint1Collection**.

---

## Swagger

Puedes acceder a la documentación de la API a través de la siguiente URL:

- **[Swagger UI](http://localhost:8080/swagger-ui/index.html#/)**
  
---

## Diagrama de Clases

Para visualizar el diagrama de clases, revisa el siguiente archivo:

- **[classDiagram.md](./src/main/resources/classDiagram.md)**

### Diagrama de Clases

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
