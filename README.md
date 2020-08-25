# spring-playground
First steps with Spring Boot

This Project was created using Spring boot via the [Spring initializer](https://start.spring.io/)

It contains the main features of the quick [REST-Service guide](https://spring.io/guides/gs/rest-service/) and the Tutorial on [building REST services with Spring](https://spring.io/guides/tutorials/rest/)
In addition to that there is a simple example on how to [consume a RESTful Web Service](https://spring.io/guides/gs/consuming-rest/)

The HelloWorldController shows the most basic form and just provides a route for one GET Request with one parameter.

The GreetingController also provides a route for one GET Request. 
The Response is being directly mapped into a DAO which is automatically converted to JSON via the Jackson JSON library. So the client will see the JSON representation of that DAO when doing that GET request.

The ConsumingRestController shows simplified how to consume a RESTful Web Service. We request a Web Service that randomly returns a Quote, consisting of a type and value as JSON. The returned data from the web service is bound to our DAO automatically via Jackson.

The EmployeeController was written with the Tutorial on [building REST services with Spring](https://spring.io/guides/tutorials/rest/).
This is the most complex REST example in this project which uses Spring MVC + Spring HATEOAS with HAL representations of each resource.
We use an Assembler to not only return the DAO as JSON but return an entire EntityModel, that also provides links to the resource itself and other links that might be helpful to lead the client through the REST API. In addition to that this lets us return nice HTTP status codes like HTTP 201 when a new resource is created. 
The EmployeeRepository extends Spring Data JPA's (Java Persistence API). This interface supports creating new instances, updating existing ones, deleting and finding (one, all, by simple/complex properties) by default. 
