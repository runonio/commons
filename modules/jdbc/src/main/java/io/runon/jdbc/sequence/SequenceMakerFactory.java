
package io.runon.jdbc.sequence;

import lombok.extern.slf4j.Slf4j;

/**
 * 시퀀스 생성기 factory
 * @author macle
 */
@Slf4j
public class SequenceMakerFactory {

	/**
	 * SequenceMaker 생성
	 * @param dbType database type (oracle, tibero, mysql, mariadb, postgresql, msssql ...)
	 * @return SequenceMaker
	 */
	public static SequenceMaker make(String dbType){
		dbType = dbType.toLowerCase();
		if(dbType.equals("oracle") || dbType.equals("tibero") ){
			return new OracleSequenceMaker();
		}else if(dbType.startsWith("maria") || dbType.startsWith("mysql")){
			return new MariaSequenceMaker();
		}else if(dbType.startsWith("postgre")){
			return new PostgresqlSequenceMaker();
		}else if(dbType.startsWith("mssql") || dbType.startsWith("ms_sql") || dbType.startsWith("sqlserver")){
			return new MssqlSequenceMaker();
		} else{
			log.error("Not supported SequenceMaker DB type.");
			return new EmptySequenceMaker();
		}


		
	}
}