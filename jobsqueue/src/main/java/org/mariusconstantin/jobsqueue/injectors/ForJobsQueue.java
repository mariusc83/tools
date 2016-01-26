package org.mariusconstantin.jobsqueue.injectors;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Defines the Scope for the JobsQueue Injector Class
 * Created by Marius on 1/26/2016.
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface ForJobsQueue {
}
