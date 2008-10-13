package com.gwtent.client.aop.intercept;

import com.gwtent.client.reflection.impl.ConstructorImpl;

/**
 * Description of an invocation to a constuctor, given to an
 * interceptor upon construtor-call.
 *
 * <p>A constructor invocation is a joinpoint and can be intercepted
 * by a constructor interceptor.
 *
 * @see ConstructorInterceptor */
public interface ConstructorInvocation extends Invocation {

    /**
     * Gets the constructor being called.
     *
     * <p>This method is a frienly implementation of the {@link
     * Joinpoint#getStaticPart()} method (same result).
     *
     * @return the constructor being called. */
    ConstructorImpl getConstructor();

}
