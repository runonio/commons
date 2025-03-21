
package io.runon.commons.utils.time;


/**
 * 시간 관련 유틸성 내용
 * @author macle
 */
public class TimeUtil {
	
	//일분 밀리세컨드 값
	private static final long minuteTime = 60000L;
	
	//한시간 밀리세컨드 값
	private static final long hourTime = 3600000L;
	private static final long dayTime = 86400000L;
	/**
	 * 초단위 값을 가져온다.
	 * @param milleSecond long 천분의 1초
	 * @return double second
	 */
	public static double getSecond(long milleSecond){
		return (double)milleSecond/(double)1000;
	}
	


	/**
	 * 분단위 값을 가져온다.
	 * @param milleSecond long 1/1000 seconds
	 * @return double
	 */
	public static double getMinute(long milleSecond){
		return (double)milleSecond/(double)minuteTime;
	}
	
	
	/**
	 * 시간단위 값으로 변환해서 가져온다.
	 * @param milleSecond long 천분의 1초
	 * @return double hour
	 */
	public static double getHour(long milleSecond){
		return (double)milleSecond/(double)hourTime;
	}
	
	/**
	 * 일,시간,분,초, 나머지 밀리세컨드 형태의 문자열로 가져온다.
	 * @param milleSecond long 천분의 1초
	 * @return String TimeValue
	 */
	public static String getTimeValue(long milleSecond){
		
			
		StringBuilder timeValueBuilder = new StringBuilder();

		//하루 밀리세컨드 값

		long day = milleSecond/dayTime;
		
		milleSecond = milleSecond - dayTime *day;
		
		
		int hour = (int)(milleSecond/hourTime);
		
		milleSecond = milleSecond - hourTime*hour;
		
		int minute = (int)(milleSecond/60000L);
		milleSecond = milleSecond - minuteTime*minute;
		
		double second = (double)milleSecond/1000.0;

		
		timeValueBuilder.append(day);
		timeValueBuilder.append("day");
		
		timeValueBuilder.append("  ").append(hour);
		timeValueBuilder.append("Hour");
		timeValueBuilder.append("  ").append(minute);
		timeValueBuilder.append("Minute");

		timeValueBuilder.append("  ").append(String.format("%.2f", second));
		timeValueBuilder.append("Second");
			
			
		return timeValueBuilder.toString();
	}

}
	
