package org.example.framework.annotataion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

import org.example.core.HttpMethod;

@Target({ElementType.METHOD})
public @interface Endpoint {

    String url();

    HttpMethod method();
}
