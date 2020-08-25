package ch.bluecare.springplayground.controller;

import ch.bluecare.springplayground.model.Greeting;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;

// Basics on how to build a RESTful Web Service (https://spring.io/guides/gs/rest-service/)
@RestController
public class GreetingController {

  private static final String template = "Hello, %s!";
  private final AtomicLong counter = new AtomicLong();

  // Synonym:
  // @RequestMapping(method = RequestMethod.GET)
  @GetMapping("/greeting")
  public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
    // Difference traditional MVC controller and RESTful web service controller
    // We don't rely on a view technology to perform server-side rendering of greeting data to HTML.
    // This RESTful web service controller populates and returns a Greeting object and will be
    // written directly to the HTTP response as JSON
    return new Greeting(counter.incrementAndGet(), String.format(template, name));
  }
}
