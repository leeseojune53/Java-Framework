package org.example.annotataion;

import org.example.core.HttpMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
public @interface Endpoint {

    String url();

    HttpMethod method();

}
