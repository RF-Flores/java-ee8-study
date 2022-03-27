package pers.ricardo.control;

import javax.enterprise.util.Nonbinding;
import javax.interceptor.InterceptorBinding;
import java.lang.annotation.*;

@InterceptorBinding
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@Documented
public @interface Tracked {

    @Nonbinding
    ProcessTracker.Category value(); //This is the category that will then be used in the tracked method

}
