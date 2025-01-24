# be_java_hisp_w29_g07
## You can find the related documents in resources folder
### Seeds:
#### [Users.json](./src/main/resources/users.json)
#### [Posts.json](./src/main/resources/posts.json)
### Postman collection:
#### Resources -> Sprint1Collection
### Swagger:
#### Url -> http://localhost:8080/swagger-ui/index.html#/
### Class Diagram: 
#### Resources -> classDiagram.md
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

```
