package eu.jaloszynski.todoapp.controller;

import eu.jaloszynski.todoapp.TaskConfigurationProperties;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class InfoController {

    private DataSourceProperties dataSource;
    private TaskConfigurationProperties myProp;


    public InfoController(DataSourceProperties dataSource, TaskConfigurationProperties myProp) {
        this.dataSource = dataSource;
        this.myProp = myProp;
    }

    @GetMapping("/info/url")
    String url() {
        return dataSource.getUrl();
    }

    @GetMapping("/info/prop")
    Boolean myProp() {
        return myProp.getTemplate().isAllowMultipleTasks();
    }
}
