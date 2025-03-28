
package io.runon.jdbc.naming;

import io.runon.commons.config.Config;
import io.runon.commons.config.ConfigInfo;
import io.runon.commons.config.ConfigObserver;
import io.runon.commons.data.NullData;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
/**
 * naming data type 관리
 * 설정 정보 활용
 * @author macle
 */
@Slf4j
public class JdbcNamingDataType {
	private static class Singleton {
		private static final JdbcNamingDataType instance = new JdbcNamingDataType();
	}

	/**
	 * singleton instance get
	 * @return JdbcNamingDataType singleton instance
	 */
	public static JdbcNamingDataType getInstance(){
		return Singleton.instance;
	}
	
	/**
	 * 앞 우선순위여부 
	 * false 일경우 뒤 우선순위
	 * 우선순위는 앞, 뒤 헤더조건을 모두 사용할 경우 
	 */
	private boolean isFrontPriority = true;
	
	private boolean isFront = false;	
	private boolean isBack = false;
	
	private JdbcDataType defaultDataType = JdbcDataType.STRING;
	
	private final Map<JdbcDataType, TypeAndHeader> typeHeaderMap;
	
	// null 방지 
	private TypeAndHeader [] sortArray = new TypeAndHeader[0];
	
	private final Map<String, JdbcDataType> typeKeyMap;
	
	private final String [] typeKeyArray = {
			"string"
			,"double"
			,"integer"
			,"long"
			,"datetime"
			,"boolean"
			,"bigdecimal"
	};
	
	
	/**
	 * 생성자
	 */
	private JdbcNamingDataType(){
		
		typeKeyMap = new HashMap<>();
		typeKeyMap.put("string", JdbcDataType.STRING);
		typeKeyMap.put("double", JdbcDataType.DOUBLE);
		typeKeyMap.put("integer", JdbcDataType.INTEGER);
		typeKeyMap.put("long", JdbcDataType.LONG);
		typeKeyMap.put("datetime", JdbcDataType.DATE_TIME);
		typeKeyMap.put("boolean", JdbcDataType.BOOLEAN);
		typeKeyMap.put("bigdecimal", JdbcDataType.BIG_DECIMAL);

		final String headerPositionKey = "application.jdbc.naming.header.position";
		setHeaderPosition(Config.getConfig(headerPositionKey,"front"));
		
		final String defaultKey = "application.jdbc.naming.default";
		setDefaultKey(Config.getConfig(defaultKey,"string"));
		
		
		typeHeaderMap = new HashMap<>();
		JdbcDataType [] typeArray =JdbcDataType.values();
		for(JdbcDataType dataType : typeArray){
			typeHeaderMap.put(dataType, new TypeAndHeader(dataType));
		}
		
		for(String typeKey : typeKeyArray){
			setTypeHeader(typeKey, Config.getConfig("application.jdbc.naming." + typeKey) );
		}

		
		final String seqKey = "application.jdbc.naming.seq";
		String seqValue = Config.getConfig(seqKey);
		if(seqValue != null){
			setSeq(seqValue);
		}

		ConfigObserver configObserver = configInfos -> {

			outer:
			for(ConfigInfo configInfo : configInfos){
				if(configInfo.getKey().equals(headerPositionKey)){
					setHeaderPosition(configInfo.getValue());
					continue;
				}else if(configInfo.getKey().equals(defaultKey)){
					setDefaultKey(configInfo.getValue());
					continue;
				}


				for(String typeKey : typeKeyArray){
					if(configInfo.getKey().equals("application.jdbc.naming." + typeKey)){
						setTypeHeader(typeKey, configInfo.getValue());
						continue outer;
					}
				}

				if(configInfo.getKey().equals(seqKey)){
					setSeq(configInfo.getValue());
				}
			}

		};
		
		Config.addObserver(configObserver);
	}

	/**
	 * header 위치 설정
	 * 앞, 뒤, 앞뒤
	 * @param value String
	 */
	private void setHeaderPosition(String value){

		
		String checkValue = value.toLowerCase();
		int frontIndex = checkValue.indexOf("front");
		int backIndex = checkValue.indexOf("back");

		isFront = frontIndex != -1;
		isBack = backIndex != -1;
		
		if(isFront && isBack){
			isFrontPriority = frontIndex < backIndex;
		}
	}

	/**
	 * default set
	 * @param value String
	 */
	private void setDefaultKey(String value){
		String checkValue = value.toLowerCase().trim();
		JdbcDataType type = typeKeyMap.get(checkValue);
		
		if(type != null){
			defaultDataType = type;
		}else{
			log.error("Config check 'application.jdbc.naming.default' value in (string, double, integer, long, datetime, boolean, bigdecimal) ");
			defaultDataType = JdbcDataType.STRING;
		}
		
	}

	/**
	 * type header set
	 * @param typeKey String
	 * @param value String
	 */
	private void setTypeHeader(String typeKey, String value){
		
		JdbcDataType type  = typeKeyMap.get(typeKey);

		TypeAndHeader typeAndHeader = typeHeaderMap.get(type);
		if(value == null){
			typeAndHeader.setHeaderArray(NullData.EMPTY_STRING_ARRAY);
			return ;
		}
		value = value.trim();
		if("".equals(value)){
			typeAndHeader.setHeaderArray(NullData.EMPTY_STRING_ARRAY);
			return ;
		}
	
		typeAndHeader.setHeaderArray(value.split(","));
		
	}

	/**
	 * seq set
	 * @param value String
	 */
	private void setSeq(String value){
		value = value.trim().toLowerCase().replace(" ", "");
		
		String [] keyArray = value.split(",");
		
		for(String keyCheck : keyArray){
			if(typeKeyMap.containsKey(keyCheck)){
				continue;
			}
			
			log.error("Config check 'application.jdbc.naming.seq' value in (string, double, integer, long, datetime, boolean, bigdecimal) error -> "  + keyCheck);
		}
		
		
		TypeAndHeader [] sortArray = new TypeAndHeader[keyArray.length];
		for(int i=0 ; i<sortArray.length ; i++){
			sortArray[i] = typeHeaderMap.get(typeKeyMap.get(keyArray[i]));
		}
		
		this.sortArray = sortArray;

	}
	
	private static class TypeAndHeader{
		private String [] headerArray = NullData.EMPTY_STRING_ARRAY;
		private final JdbcDataType dataType;
		private TypeAndHeader(JdbcDataType jdbcDataType){
			dataType = jdbcDataType;
		}
		
		private void setHeaderArray(String [] array){
			headerArray = array;
		}
	}

	/**
	 * JdbcDataType get
	 * @param columnName String
	 * @return JdbcDataType
	 */
	public JdbcDataType getType(String columnName){
		JdbcDataType type = type(columnName);
		if(type == JdbcDataType.UNDEFINED){
			return defaultDataType;
		}
		return type;
	}
	public JdbcDataType type(String columnName){
		if(isFront && isBack){
			JdbcDataType type;
			if(isFrontPriority){
				type = front(columnName);
				if(type != null){
					return type;
				}
				type = back(columnName);

			}else{
				type = back(columnName);
				if(type != null){
					return type;
				}
				type = front(columnName);
			}
			if(type != null){
				return type;
			}

		}else if(isFront){
			JdbcDataType type = front(columnName);
			if(type != null){
				return type;
			}
		}else if(isBack){
			JdbcDataType type = back(columnName);

			if(type != null){
				return type;
			}

		}

		return JdbcDataType.UNDEFINED;
	}

	/**
	 * front first 를 활용 하여 JdbcDataType 얻기
	 * @param columnName String
	 * @return JdbcDataType
	 */
	private JdbcDataType front(String columnName){
		
		TypeAndHeader [] sortArray =this.sortArray ;
		//noinspection ForLoopReplaceableByForEach
		for(int i=0 ; i<sortArray.length ; i++){
			String [] headerArray = sortArray[i].headerArray;
			//noinspection ForLoopReplaceableByForEach
			for(int j=0 ; j<headerArray.length ; j++){
				if(columnName.startsWith(headerArray[j]+ "_") || columnName.equals(headerArray[j])){
					return sortArray[i].dataType;
				}
			}
		}
		
		return null;
	}

	/**
	 * back last 를 활용 하여 JdbcDataType 얻기
	 * @param columnName String
	 * @return JdbcDataType
	 */
	private JdbcDataType back(String columnName){
		
		TypeAndHeader [] sortArray =this.sortArray ;
		//noinspection ForLoopReplaceableByForEach
		for(int i=0 ; i<sortArray.length ; i++){
			String [] headerArray = sortArray[i].headerArray;
			//noinspection ForLoopReplaceableByForEach
			for(int j=0 ; j<headerArray.length ; j++){
				if(columnName.endsWith("_" + headerArray[j]) || columnName.equals(headerArray[j])){
					return sortArray[i].dataType;
				}
			}
		}
		
		return null;
	}

	/**
	 * front flag
	 * @return boolean
	 */
	public boolean isFront() {
		return isFront;
	}

	/**
	 * back flag
	 * @return boolean
	 */
	public boolean isBack() {
		return isBack;
	}

	/**
	 * 앞 뒤 사용일때 front 우선순위 여부
	 * falst 일경우 back 이 우선
	 * @return boolean
	 */
	public boolean isFrontPriority() {
		return isFrontPriority;
	}
}
