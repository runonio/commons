
package io.runon.jdbc;

import io.runon.commons.config.Config;
import io.runon.jdbc.exception.DaoClassNotFoundException;

/**
 * JdbcDaoFactory
 * @author macle
 */
public class JdbcDaoFactory {

	private static final String TYPE_KEY = "application.jdbc.type";


	/**
	 * dao implement get
	 * @param daoClass  daoClass
	 * @param <T> ImplDaoClass
	 * @return T ImplDaoClass
	 */
	public static <T> T getDao(Class<T> daoClass){
		return getDao(daoClass, Config.getConfig(TYPE_KEY));
	}

	/**
	 * dao implement get
	 * @param daoClass Class  daoClass
	 * @param dbType string dataBaseType
	 * @param <T> ImplDaoClass
	 * @return T ImplDaoClass
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getDao(Class<T> daoClass, String dbType){
	
		dbType = dbType.toLowerCase();
		dbType = Character.toUpperCase(dbType.charAt(0)) + dbType.substring(1);
	
		String className = daoClass.getName().substring(0,daoClass.getName().lastIndexOf(".dao.")) + ".jdbc." 
						+ daoClass.getSimpleName() +"Impl" + dbType;
		
		
		Class<?> makeDaoClass ;
		try {
			makeDaoClass =Class.forName(className);			
		} catch (ClassNotFoundException e) {
			className = daoClass.getName().substring(0,daoClass.getName().lastIndexOf(".dao.")) + ".jdbc." 
					+ daoClass.getSimpleName() +"Impl";
			try{
				makeDaoClass =Class.forName(className);
			}catch(ClassNotFoundException makeE){
			
				throw new DaoClassNotFoundException(	 "Class Not Found  "
						+ daoClass.getName().substring(0,daoClass.getName().lastIndexOf(".dao.")) + ".jdbc." 
						+ daoClass.getSimpleName() +"Impl" + dbType + "  OR  "  + className);
			}
		}
		T t ;
		try{

			t = (T) makeDaoClass.newInstance();
		}catch(Exception e){
			throw new DaoClassNotFoundException(	 "Class Not Found  " 
					+ daoClass.getName().substring(0,daoClass.getName().lastIndexOf(".dao.")) + ".jdbc." 
					+ daoClass.getSimpleName() +"Impl" + dbType + "  OR  "  + className);
		}
		return t;
	
	}
	
}