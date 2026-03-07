## Here are some notes

**Staring up the mongoDB in terminal**

brew services start mongodb-community 

Starting the maven spring boot api:
mvn clean spring-boot:run

Every new terminal: 
export JAVA_HOME=$(/usr/libexec/java_home -v 17)



## Links:
https://www.geeksforgeeks.org/java/spring-boot-mongorepository-with-example/
used for seeing the whole picture of repo, service, controller, model and the code

https://tom-collings.medium.com/controller-service-repository-16e29a4684e5
used for seeing the overview of how the parts work together

https://www.tutorialspoint.com/spring_boot_jpa/spring_boot_jpa_repository_methods.htm
How to make a repository

https://stackoverflow.com/questions/68701181/get-request-and-error-handling-with-the-jparepository-findbyid-method
&
https://stackoverflow.com/questions/66561147/java-with-spring-boot-throw-an-exception-that-returns-a-specific-http-code-to-t/66562083
How to work with the Throw error 404


https://www.baeldung.com/spring-requestparam-vs-pathvariable
How to work with the parameters ID and author
Aka the whole controller code

https://docs.spring.io/spring-framework/reference/web/webflux/controller/ann-methods/requestbody.html
How to work with requestbody

https://spring.io/guides/tutorials/rest
The old rest PUT code

https://medium.com/@AlexanderObregon/javas-optional-orelseget-method-explained-d65c97250a9e
Or else get

### Unit tests
https://site.mockito.org/
Mockito docs

https://medium.com/@alxkm/mocking-with-mockito-simplifying-unit-testing-in-java-1cc50d78d2c0
Article explaining mocking with mockito
Good code example for 1st test i made

https://www.baeldung.com/mockito-verify
How to delete and confrim it calls database

https://junit.org/junit4/javadoc/4.8/org/junit/Assert.html
Junit docs, for assertEquals

https://www.baeldung.com/junit-assert-exception
AssertException - working with the exception 404

Here is all the claude help and notes from claude for use later on:
https://claude.ai/share/f24e8c8a-23f2-4bfe-82b0-e8bc644f8dce


### Integration tests
https://www.baeldung.com/mockito-behavior
When/then cookbook


#### Integration Test Notes — MockMvc

##### What MockMvc is
In unit tests you called `bookService.findById()` directly — just a Java method call.
Integration tests test the **full HTTP flow**: does the right URL trigger the right controller, with the right status code and response body?

MockMvc lets you simulate a real HTTP request without running a server.

---

###### The Three Building Blocks

###### 1. `perform()` — fires the fake HTTP request
```java
mockMvc.perform(get("/api/books"))
mockMvc.perform(post("/api/books")
    .contentType(MediaType.APPLICATION_JSON)
    .content(jsonString))
mockMvc.perform(delete("/api/books/123"))
```

###### 2. `andExpect()` — checks something about the response
```java
.andExpect(status().isOk())            // 200
.andExpect(status().isCreated())       // 201
.andExpect(status().isNotFound())      // 404
.andExpect(jsonPath("$.title").value("Clean Code"))  // check JSON field
```

###### 3. `jsonPath()` — navigates the JSON response body
```java
jsonPath("$.title")         // field on root object
jsonPath("$.id").exists()   // just check the field exists
jsonPath("$[0].title")      // first element of an array
```

---

##### Required Imports
```java
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.http.MediaType;
```

---

###### Full Example
```java
mockMvc.perform(get("/api/books"))
       .andExpect(status().isOk())
       .andExpect(jsonPath("$[0].title").value("Clean Code"));
```


### DB
mongosh

use ecolink_db

ecolink_db> db.books.find()
[
{
_id: ObjectId('69a9e273250b382e0ab8f126'),
title: 'Clean Code',
author: 'Robert C. Martin',
publishedYear: 2008,
isbn: '978-0132350884',
_class: 'com.ecolink.api.model.Book'
},
{
_id: ObjectId('69ac47df99cd181c10d6e8fc'),
title: 'The Pragmatic Programmer',
author: 'David Thomas',
publishedYear: 1999,
isbn: '978-0135957059',
_class: 'com.ecolink.api.model.Book'
},
{
_id: ObjectId('69ac47e799cd181c10d6e8fd'),
title: 'Refactoring 2nd Edition',
author: 'Martin Fowler',
publishedYear: 2018,
isbn: '978-0134757599',
_class: 'com.ecolink.api.model.Book'
}
