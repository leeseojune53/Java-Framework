package org.example.annotataion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Component
@Target({ElementType.TYPE})
public @interface Controller {}
