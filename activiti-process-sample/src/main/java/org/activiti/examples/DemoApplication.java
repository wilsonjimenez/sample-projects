package org.activiti.examples;

import java.time.LocalDate;
import java.util.Map;

import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.process.model.builders.ProcessPayloadBuilder;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.api.process.runtime.connector.Connector;
import org.activiti.api.runtime.shared.query.Pageable;
import org.activiti.api.task.model.builders.TaskPayloadBuilder;
import org.activiti.api.task.runtime.TaskRuntime;
import org.activiti.examples.model.LocalDateContainer;
import org.activiti.examples.model.StringContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DemoApplication implements CommandLineRunner {

    private Logger logger = LoggerFactory.getLogger(DemoApplication.class);

    @Autowired
    private ProcessRuntime processRuntime;

    @Autowired
    private TaskRuntime taskRuntime;

    @Autowired
    private SecurityUtil securityUtil;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    public void run(String... args) {
        securityUtil.logInAs("system");

        /**
         * using StringContainer works, while using LocalDateContainer fails
          */

        //final var myContext = new StringContainer(LocalDate.now().toString());
        final var myContext = new LocalDateContainer(LocalDate.now());

        ProcessInstance processInstance = processRuntime.start(
            ProcessPayloadBuilder.start()
                .withProcessDefinitionKey("simpleProcess")
                .withVariable("myContext", myContext)
                .build());

        final var tasksPage =
            taskRuntime.tasks(
                Pageable.of(0, 1),
                TaskPayloadBuilder.tasks().withProcessInstanceId(processInstance.getId()).build());

        final var availableTaskId = tasksPage.getContent().get(0).getId();

        taskRuntime.complete(
            TaskPayloadBuilder.complete()
                .withTaskId(availableTaskId)
                .build());

    }

    @Bean
    public Connector serviceTask1() {
        return integrationContext -> {
            final Map<String, Object> inBoundVariables = integrationContext.getInBoundVariables();
            logger.info("running task 1 with variables: " + inBoundVariables);
            return integrationContext;
        };
    }

}
