package org.crp.flowable.spring.delegate;

import org.apache.log4j.Logger;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.flowable.engine.impl.delegate.TriggerableActivityBehavior;
import org.flowable.engine.impl.el.FixedValue;


public class HelloWorldJavaDelegate implements JavaDelegate, TriggerableActivityBehavior {

    private static final Logger LOGGER = Logger.getLogger(HelloWorldJavaDelegate.class);

    @Override
    public void execute(DelegateExecution execution) {
        LOGGER.info("Entering HelloWorldJavaDelegate.execute()");
        execution.setVariable("greeting", "Hello world");
        LOGGER.info("Leaving HelloWorldJavaDelegate.execute()");
    }

    @Override
    public void trigger(DelegateExecution execution, String signalEvent, Object signalData) {
        LOGGER.info("Entering HelloWorldJavaDelegate.trigger()");
        execution.setVariable("greeting", "Bye bye world");
        LOGGER.info("Leaving HelloWorldJavaDelegate.trigger()");
    }
}
