package org.example.framework.annotataion;

import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.Target;

@Target({METHOD})
public @interface Transactional {}
