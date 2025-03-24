
package io.runon.commons.service;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 자동 동기화 annotation
 * annotation 이 지정 되면 SynchronizerManager 에서 자동 호출 됨
 * @author macle
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Synchronization {
}
