


package io.runon.commons.service.memory;


public class MemoryUse {
	/**
	 * 메가바이트
	 */
	public static final long MB = 1024*1024;
	/**
	 * 기가바이트
	 */
	public static final long GB = MB*1024;
	/**
	 * 테라바이트
	 */
	public static final long TB = GB*1024;
	

	/**
	 * 실사용 메모리
	 * @return
	 */
	public static long getUsedMemory(){
	    Runtime runtime = Runtime.getRuntime();
	    return runtime.totalMemory() - runtime.freeMemory();
	}
	
	
	/**
	 * 총 사용 메모리(실제 물리적으로 잡고있는 메모리)
	 * @return
	 */
	public static long getTotalMemory(){
	    Runtime runtime = Runtime.getRuntime();
	    return runtime.totalMemory() ;
	}
	
	/**
	 * 여유 메모리
	 * @return
	 */
	public static long getFreeMemory(){
	    Runtime runtime = Runtime.getRuntime();
	    return runtime.freeMemory() ;
	}
	
	/**
	 * 가변적으로 늘어날수있는 최대메모리를 돌려준다.
	 * @return
	 */
	public static long getMaxMemory(){
		Runtime runtime = Runtime.getRuntime();
		 return runtime.maxMemory() ;
	}

	/**
	 * 생성자
	 */
	private MemoryUse(){	
	}
	
}