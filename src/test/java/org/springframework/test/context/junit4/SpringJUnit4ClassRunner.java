/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.test.context.junit4;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.internal.runners.InitializationError;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;
import org.springframework.test.annotation.ProfileValueUtils;
import org.springframework.test.context.TestContextManager;

/**
 * <p>
 * SpringJUnit4ClassRunner is a custom extension of
 * {@link org.junit.internal.runners.JUnit4ClassRunner} which provides
 * functionality of the <em>Spring TestContext Framework</em> to standard JUnit
 * 4.4+ tests by means of the
 * {@link org.springframework.test.context.TestContextManager} and associated
 * support classes and annotations.
 * </p>
 * <p>
 * The following list constitutes all annotations currently supported directly
 * by SpringJUnit4ClassRunner.
 * <em>(Note that additional annotations may be supported by various
 * {@link org.springframework.test.context.TestExecutionListener TestExecutionListeners})</em>
 * </p>
 * <ul>
 * <li>{@link org.junit.Test#expected() @Test(expected=...)}</li>
 * <li>{@link org.springframework.test.annotation.ExpectedException
 * @ExpectedException}</li>
 * <li>{@link org.junit.Test#timeout() @Test(timeout=...)}</li>
 * <li>{@link org.springframework.test.annotation.Timed @Timed}</li>
 * <li>{@link org.springframework.test.annotation.Repeat @Repeat}</li>
 * <li>{@link org.junit.Ignore @Ignore}</li>
 * <li>
 * {@link org.springframework.test.annotation.ProfileValueSourceConfiguration
 * @ProfileValueSourceConfiguration}</li>
 * <li>{@link org.springframework.test.annotation.IfProfileValue
 * @IfProfileValue}</li>
 * </ul>
 *
 * @author Sam Brannen
 * @author Juergen Hoeller
 * @see org.springframework.test.context.TestContextManager
 * @since 2.5
 */
public class SpringJUnit4ClassRunner extends JUnit4ClassRunner {

    /** The Constant logger. */
    private static final Log LOGGER = LogFactory
            .getLog(SpringJUnit4ClassRunner.class);

    /** The test context manager. */
    private final TestContextManager testContextManager;

    /**
     * Constructs a new <code>SpringJUnit4ClassRunner</code> and initializes a
     * {@link org.springframework.test.context.TestContextManager} to provide
     * Spring testing functionality to standard JUnit tests.
     * 
     * @param clazz
     *            the Class object corresponding to the test class to be run
     * @throws InitializationError
     *             the initialization error
     * @see #createTestContextManager(Class)
     */
    public SpringJUnit4ClassRunner(Class<?> clazz) throws InitializationError {
        super(clazz);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("SpringJUnit4ClassRunner constructor called with ["
                    + clazz + "].");
        }
        this.testContextManager = createTestContextManager(clazz);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.junit.internal.runners.JUnit4ClassRunner#run(org.junit.runner.
     * notification.RunNotifier)
     */
    @Override
    /**
     * Check whether the test is enabled in the first place. This prevents classes with
     * a non-matching <code>@IfProfileValue</code> annotation from running altogether,
     * even skipping the execution of <code>prepareTestInstance</code> listener methods.
     * @see org.springframework.test.annotation.IfProfileValue
     * @see org.springframework.test.context.TestExecutionListener
     */
    public void run(RunNotifier notifier) {
        if (!ProfileValueUtils.isTestEnabledInThisEnvironment(getTestClass()
                .getJavaClass())) {
            notifier.fireTestIgnored(getDescription());
            return;
        }
        super.run(notifier);
    }

    /**
     * Delegates to
     * {@link org.junit.internal.runners.JUnit4ClassRunner#createTest()} to
     * create the test instance and then to a
     * {@link org.springframework.test.context.TestContextManager} to
     * {@link org.springframework.test.context.TestContextManager#prepareTestInstance(Object)
     * prepare} the test instance for Spring testing functionality.
     * 
     * @return the object
     * @throws Exception
     *             the exception
     * @see org.junit.internal.runners.JUnit4ClassRunner#createTest()
     * @see org.springframework.test.context.TestContextManager#prepareTestInstance(Object)
     */
    @Override
    protected Object createTest() throws Exception {
        Object testInstance = super.createTest();
        getTestContextManager().prepareTestInstance(testInstance);
        return testInstance;
    }

    /**
     * Creates a new {@link org.springframework.test.context.TestContextManager}
     * . Can be overridden by subclasses.
     * 
     * @param clazz
     *            the Class object corresponding to the test class to be managed
     * @return the test context manager
     */
    protected TestContextManager createTestContextManager(Class<?> clazz) {
        return new TestContextManager(clazz);
    }

    /**
     * Get the {@link org.springframework.test.context.TestContextManager}
     * associated with this runner.
     * 
     * @return the test context manager
     */
    protected final TestContextManager getTestContextManager() {
        return this.testContextManager;
    }

    /**
     * Invokes the supplied {@link java.lang.reflect.Method test method} and
     * notifies the supplied {@link org.junit.runner.notification.RunNotifier}
     * of the appropriate events.
     * 
     * @param method
     *            the method
     * @param notifier
     *            the notifier
     * @see #createTest()
     * @see org.junit.internal.runners.JUnit4ClassRunner#invokeTestMethod(java.lang.reflect.Method,
     *      org.junit.runner.notification.RunNotifier)
     */
    @Override
    protected void invokeTestMethod(Method method, RunNotifier notifier) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Invoking test method [" + method.toGenericString()
                    + "]");
        }

        // The following is a 1-to-1 copy of the original JUnit 4.4 code, except
        // that we use custom implementations for TestMethod and MethodRoadie.

        Description description = methodDescription(method);
        Object testInstance;
        try {
            testInstance = createTest();
        } catch (InvocationTargetException ex) {
            // notifier.testAborted(description, ex.getCause());

            testAborted(notifier, description, ex);

            return;
        } catch (Exception ex) {
            testAborted(notifier, description, ex);
            // notifier.testAborted(description, ex);
            return;
        }

        SpringTestMethod testMethod = new SpringTestMethod(method,
                getTestClass());
        new SpringMethodRoadie(getTestContextManager(), testInstance,
                testMethod, notifier, description).run();
    }

    /**
     * 尝试解决spring-test.2.5.6与junit4.4已上版本的兼容性问题
     * 
     * @param notifier
     *            the notifier
     * @param description
     *            the description
     * @param ex
     *            the ex
     */
    private void testAborted(RunNotifier notifier, Description description,
            Exception ex) {
        notifier.fireTestStarted(description);
        notifier.fireTestFailure(new Failure(description, ex));
        notifier.fireTestFinished(description);
    }

}
