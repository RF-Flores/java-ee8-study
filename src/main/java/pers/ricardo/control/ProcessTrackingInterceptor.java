package pers.ricardo.control;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.Interceptors;
import javax.interceptor.InvocationContext;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.function.Function;

@Interceptor()
@Tracked(ProcessTracker.Category.UNSUSED) //This is fundamental in order to not have to tightly couple interceptors.
@Priority(Interceptor.Priority.APPLICATION) // this is a way of specifying how will the interceptor be active see notes
public class ProcessTrackingInterceptor {

    @Inject
    ProcessTracker processTracker; //This can be used to track once a method ha been intercepted.

    @AroundInvoke()
    public Object aroundInvoke(InvocationContext context) throws Exception {
        Tracked tracked = retrieveAnnotation(context);
        processTracker.track(tracked.value());
        return context.proceed();
    }

    private Tracked retrieveAnnotation(InvocationContext context) {
        Function<AnnotatedElement, Tracked> extractor = c -> c.getAnnotation(Tracked.class);
        Method method = context.getMethod();
        Tracked tracked = extractor.apply(method);
        return tracked != null ? tracked : extractor.apply(method.getDeclaringClass()); //Since this can be annotated on the class itself a validation is needed.
    }

}
