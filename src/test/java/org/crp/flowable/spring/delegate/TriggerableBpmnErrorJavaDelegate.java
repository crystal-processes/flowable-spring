package org.crp.flowable.spring.delegate;

import java.util.Collections;

import org.apache.log4j.Logger;
import org.flowable.common.engine.impl.interceptor.CommandContext;
import org.flowable.common.engine.impl.logging.LoggingSessionConstants;
import org.flowable.engine.delegate.BpmnError;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.flowable.engine.impl.bpmn.helper.ErrorPropagation;
import org.flowable.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.flowable.engine.impl.delegate.TriggerableActivityBehavior;
import org.flowable.engine.impl.persistence.entity.ExecutionEntity;
import org.flowable.engine.impl.util.BpmnLoggingSessionUtil;
import org.flowable.engine.impl.util.CommandContextUtil;

public class TriggerableBpmnErrorJavaDelegate implements JavaDelegate, TriggerableActivityBehavior {

    private static final Logger LOGGER = Logger.getLogger(TriggerableBpmnErrorJavaDelegate.class);

    @Override
    public void execute(DelegateExecution execution) {
        LOGGER.info("Entering HelloWorldJavaDelegate.execute()");
        execution.setVariable("greeting", "Hello world");
        LOGGER.info("Leaving HelloWorldJavaDelegate.execute()");
    }

    @Override
    public void trigger(DelegateExecution execution, String signalEvent, Object signalData) {
        LOGGER.info("throwing BPMN Error");
        handleException(new BpmnError("bpmnErrorCode"), execution);
    }

    protected void handleException(Throwable exc, DelegateExecution execution) {
        CommandContext commandContext = CommandContextUtil.getCommandContext();
        ProcessEngineConfigurationImpl processEngineConfiguration = CommandContextUtil.getProcessEngineConfiguration(commandContext);
        boolean loggingSessionEnabled = processEngineConfiguration.isLoggingSessionEnabled();

        if (loggingSessionEnabled) {
            BpmnLoggingSessionUtil.addErrorLoggingData(LoggingSessionConstants.TYPE_SERVICE_TASK_EXCEPTION,
                    "Service task with delegate expression threw exception " + exc.getMessage(), exc, execution);
        }

        ErrorPropagation.handleException(exc, (ExecutionEntity) execution, Collections.emptyList());
    }
}
