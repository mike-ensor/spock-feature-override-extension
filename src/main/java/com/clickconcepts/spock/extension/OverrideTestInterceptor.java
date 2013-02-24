package com.clickconcepts.spock.extension;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spockframework.runtime.extension.AbstractMethodInterceptor;
import org.spockframework.runtime.extension.IMethodInvocation;
import org.spockframework.runtime.model.ErrorInfo;

public class OverrideTestInterceptor extends AbstractMethodInterceptor {

    private static final Logger LOG = LoggerFactory.getLogger(OverrideTestInterceptor.class);
    private int errorCount;
    private int totalSpecCount;

    public OverrideTestInterceptor() {
        LOG.debug("+++++++++Creating an interceptor");
        this.errorCount = 0;
        this.totalSpecCount = 0;
    }

    @Override
    public void interceptSetupSpecMethod(IMethodInvocation invocation) throws Throwable {
        super.interceptSetupSpecMethod(invocation);
        LOG.debug("Intercept SetupSpec Method");
        LOG.debug("Error Count = {}", errorCount);
    }

    @Override
    public void interceptSetupMethod(IMethodInvocation invocation) throws Throwable {
        super.interceptSetupMethod(invocation);
        LOG.debug("Intercept Setup Method");
    }

    @Override
    public void interceptCleanupMethod(IMethodInvocation invocation) throws Throwable {
        super.interceptCleanupMethod(invocation);
        LOG.debug("Intercept Cleanup Method");
    }

    @Override
    public void interceptSpecExecution(IMethodInvocation invocation) throws Throwable {
        super.interceptSpecExecution(invocation);
        LOG.debug("Intercept SpecExecution");
    }

    @Override
    public void interceptFeatureExecution(IMethodInvocation invocation) throws Throwable {
        super.interceptFeatureExecution(invocation);
        LOG.debug("Intercept Feature Execution");
    }

    @Override
    public void interceptCleanupSpecMethod(IMethodInvocation invocation) throws Throwable {
        super.interceptCleanupSpecMethod(invocation);    //To change body of overridden methods use File | Settings | File Templates.
        LOG.debug("After All cleanup");
        LOG.debug("Final Error Count = {}", errorCount);
        LOG.debug("\n\n------------------\n\n");
        totalSpecCount--;
        if (totalSpecCount <= 1) {
            LOG.debug("======== Make the call to Sauce with session {} and reporting {} errors", System.getProperty("BLAH"), errorCount);
        }
    }

    public void error(ErrorInfo error) {
        errorCount++;
        LOG.debug("Error Recorded, count={}", errorCount);
    }

    public void addSpec() {
        totalSpecCount++;
    }
}
