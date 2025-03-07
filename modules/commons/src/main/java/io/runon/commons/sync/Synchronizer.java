

package io.runon.commons.sync;
/**
 * 동기화
 * 이 클래스를 implements 하여 구현 하고 
 * Synchronization annotation 을 지정 하거나
 * SynchronizerManager 인스턴스에 추가함
 * @author macle
 */
public interface Synchronizer {
    /**
     * 동기화
     */
    void sync();

}
