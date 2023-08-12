package org.example.annotataion;

import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;

@Target({METHOD})
public @interface Transactional {
}
