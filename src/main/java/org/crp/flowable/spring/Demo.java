package org.crp.flowable.spring;

import org.apache.log4j.Logger;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Demo {

    private static final Logger LOG = Logger.getLogger(Demo.class);

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        context.registerShutdownHook();
        RuntimeService runtimeService = context.getBean(RuntimeService.class);
        RepositoryService repositoryService = context.getBean(RepositoryService.class);
        TaskService taskService = context.getBean(TaskService.class);

        Deployment deployment = repositoryService.createDeployment().addClasspathResource("my-process.bpmn20.xml").deploy();
        LOG.info("deployment created");
        ProcessInstance processInstance = runtimeService.createProcessInstanceBuilder().processDefinitionKey("my-process").start();
        LOG.info("process instance started");
        Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
        taskService.complete(task.getId());
        LOG.info("task completed");
        repositoryService.deleteDeployment(deployment.getId());
        LOG.info("deployment deleted");
        context.close();
    }
}
