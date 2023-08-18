package org.example.annotataion;

import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.Target;

@Target({METHOD})
public @interface Transactional {}
