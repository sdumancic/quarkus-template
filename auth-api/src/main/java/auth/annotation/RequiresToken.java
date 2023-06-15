package auth.annotation;


import jakarta.annotation.Priority;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Priority(10)
public @interface RequiresToken {}
