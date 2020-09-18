package ch.bluecare.springplayground.controller;

import ch.bluecare.springplayground.model.Employee;
import ch.bluecare.springplayground.model.EmployeeRepository;
import ch.bluecare.springplayground.model.consumingrest.EmployeeModelAssembler;
import ch.bluecare.springplayground.model.other.EmployeeNotFoundException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Tutorial 4: https://spring.io/guides/tutorials/rest/
 *
 * <p>Building REST services with Spring. Covering GET, POST, PUT, DELETE
 *
 * <p>uses Spring MVC + Spring HATEOAS with HAL representations
 */
@RestController
public class EmployeeController {
  private final EmployeeRepository repository;
  private final EmployeeModelAssembler assembler;

  // The EmployeeRepository and EmployeeModelAssembler is injected by constructor into the
  // controller
  public EmployeeController(EmployeeRepository repository, EmployeeModelAssembler assembler) {
    this.repository = repository;
    this.assembler = assembler;
  }

  // Aggregate root
  @GetMapping("/employees")
  public CollectionModel<EntityModel<Employee>> all() {
    // return repository.findAll();
    List<EntityModel<Employee>> employees =
        repository.findAll().stream().map(assembler::toModel).collect(Collectors.toList());

    return CollectionModel.of(
        employees, linkTo(methodOn(EmployeeController.class).all()).withSelfRel());
  }

  // Example: curl -X POST localhost:8080/employees -H 'Content-type:application/json' -d '{"name": "Samwise Gamgee", "role": "gardener"}'
  @PostMapping("/employees")
  ResponseEntity<EntityModel<Employee>> newEmployee(@RequestBody Employee newEmployee) {
    // return repository.save(newEmployee);
    EntityModel<Employee> entityModel = assembler.toModel(repository.save(newEmployee));

    // The ResponseEntity is used to create an HTTP 201 Created status message
    return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
        .body(entityModel);
  }

  // Single item
  @GetMapping("/employees/{id}")
  public EntityModel<Employee> one(@PathVariable Long id) {

    // The exception is used by spring to render an HTTP 404
    // return repository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));

    // HATEOAS
    // When just returning the Employee we get the id, name and role of the found Employee
    // With HATEOAS and returning an EntityModel we also get the link (URL) to itself and the link
    // to find all employees (localhost:8080/employees)
    // HATEOAS basically leads the client through the REST API. The links instruct clients when
    // various state-driving operations are available.
    // Advantage: Links of the REST Service are allowed to change
    Employee employee =
            repository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
    return assembler.toModel(employee);
  }

  // Example (Requirement: Execute curl POST command above):
  // curl -X PUT localhost:8080/employees/3 -H 'Content-type:application/json' -d '{"name": "Samwise Gamgee", "role": "ring bearer"}'
  @PutMapping("/employees/{id}")
  Employee replaceEmployee(@RequestBody Employee newEmployee, @PathVariable Long id) {

    return repository
        .findById(id)
        // When employee is found with that ID update it
        .map(
            employee -> {
              employee.setName(newEmployee.getName());
              employee.setRole(newEmployee.getRole());
              return repository.save(employee);
            })
        .orElseGet(
            () -> {
              newEmployee.setId(id);
              return repository.save(newEmployee);
            });

    /*
    EntityModel<Employee> entityModel = assembler.toModel(updatedEmployee);

    return ResponseEntity //
        .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
        .body(entityModel);
       */
  }

  // Example: curl -X DELETE localhost:8080/employees/2
  @DeleteMapping("/employees/{id}")
  ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
    repository.deleteById(id);
    // This returns an HTTP 204 No Content response
    return ResponseEntity.noContent().build();
  }
}
