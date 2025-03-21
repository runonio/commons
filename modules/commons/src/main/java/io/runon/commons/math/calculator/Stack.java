

package io.runon.commons.math.calculator;

 interface Stack{
	
	/**
	 * 가장위에있는 Object를 빼온다(stack에서 remove)
	 * @return
	 */
	public Object pop();
	

	public void push(Object object);
	
	/**
	 * 스텍 사이즈를 돌려준다
	 * @return
	 */
	public int size();
	
	/**
	 * 가장위에있는 Object를 가져온다(stack에서 남아있음)
	 * @return
	 */
	public Object top();
	
	/**
	 * 데이터가 비었는지 체크한다
	 * @return true, false
	 */
	public boolean isEmpty();
}