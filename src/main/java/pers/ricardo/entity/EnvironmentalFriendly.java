package pers.ricardo.entity;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.METHOD,ElementType.ANNOTATION_TYPE,ElementType.PARAMETER})
@Constraint(validatedBy = EnvironmentalFriendlyValidator.class)
@Documented
public @interface EnvironmentalFriendly {
    String message() default "";

    Class<?> [] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
