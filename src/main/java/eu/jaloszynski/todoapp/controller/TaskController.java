package eu.jaloszynski.todoapp.controller;

import eu.jaloszynski.todoapp.model.Task;
import eu.jaloszynski.todoapp.model.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;


@RestController
public class TaskController {
    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);
    private final TaskRepository repository;

    public TaskController(TaskRepository repository) {
        this.repository = repository;
    }

    @GetMapping(value = "/tasks", params = {"!sort","!page","!size"})
    ResponseEntity<List<Task>> readAllTasks(){
        logger.warn("Exposing all the task");
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/tasks")
    ResponseEntity<List<Task>> readAllTasks(Pageable page){
        logger.warn("Exposing all the task");
        return ResponseEntity.ok(repository.findAll(page).getContent());
    }

    @PutMapping("/tasks/{id}")
    ResponseEntity<?> updateTask(@PathVariable("id") int id, @RequestBody @Valid Task toUpdate){
        if (!repository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        toUpdate.setId(id);
        repository.save(toUpdate);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/tasks/{id}")
    ResponseEntity<?> getTask(@PathVariable("id") int id){
        if (!repository.existsById(id)){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(repository.findById(id));
    }

    @PostMapping("/tasks")
    ResponseEntity<?> createTask(@RequestBody @Valid Task toCreate){

        Task tmpTask = repository.save(toCreate);
        URI location = URI.create(String.format("/tasks/%s", tmpTask.getId()));
        return ResponseEntity.created(location).body(tmpTask);
    }

}
