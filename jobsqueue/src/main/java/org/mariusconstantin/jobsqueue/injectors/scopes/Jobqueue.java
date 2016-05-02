package org.mariusconstantin.jobsqueue.injectors.scopes;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by MConstantin on 1/28/2016.
 */
@Scope
@Retention(RUNTIME)
public @interface Jobqueue {
}
