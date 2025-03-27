
package io.runon.jdbc.sequence;
/**
 * 시퀀스 생성기
 * @author macle
 */
public interface SequenceMaker {

	/**
	 * 시퀀스값 얻기
	 * @param sequenceName 시퀀스 명
	 * @return next value
	 */
	String nextVal(String sequenceName);

	/**
	 * 시퀀스 num
	 * @param sequenceName 시퀀스 명
	 * @return next long
	 */
	long nextLong(String sequenceName);
}