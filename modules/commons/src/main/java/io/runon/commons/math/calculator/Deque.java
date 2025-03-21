
package io.runon.commons.math.calculator;

 interface Deque {
	
	/**
	 * 데크 사이즈를 돌려준다
	 * @return 데크 사이즈(데크에 있는 Object 개수)
	 */
	public int size();
	
	/**
	 * 데크가 비어있는지 체크한다.
	 * @return 데크가 비어잇는지에 대한 여부(true, false)
	 */
	public boolean isEmpty();
	
	/**
	 * 데크에 앞쪽에 object를 삽입한다
	 * @param object
	 */
	public void insertFirst(Object object);
	/**
	 * 데크에 뒤쪽에 object를 삽입한다.
	 * @param object
	 */
	public void insertLast(Object object);
	

	public Object removeFirst();
	

	public Object removeLast() ;
	
	

	public Object firstElement() ;

	public Object lastElement() ;
}