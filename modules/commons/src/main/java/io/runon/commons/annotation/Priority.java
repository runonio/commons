

package io.runon.commons.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 우선순위
 * @author macle
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Priority {
	/**
	 * 우선순위
	 * @return int
	 */
	int seq() default 1000;
}
