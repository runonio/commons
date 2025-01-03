package io.runon.jdbc.sync;

import io.runon.commons.config.Config;
import io.runon.commons.exception.ConfigException;
import io.runon.commons.crypto.CharMap;
import io.runon.commons.crypto.CharMapManager;
import io.runon.commons.crypto.LoginCrypto;
import io.runon.jdbc.connection.ConnectionFactory;

import java.sql.Connection;

/**
 * @author macle
 */
public class JdbcSync {
    public static Connection newSyncServerConnection(){

        String jdbcType = Config.getConfig("application.jdbc.sync.server.type");

        String driverClass = Config.getConfig("application.jdbc.sync.server.driver.class");

        String databaseTypeOrFullPackage = driverClass;
        if(driverClass == null){
            databaseTypeOrFullPackage = jdbcType;
        }

        final String urlKey = "application.jdbc.sync.server.url";
        String url =  Config.getConfig(urlKey);
        if(!isConfig(url, urlKey)){
            throw new ConfigException("not config: " + urlKey);
        }

        final String encryptFlagKey = "application.jdbc.sync.server.login.encrypt";
        String encryptFlag =  Config.getConfig(encryptFlagKey,"N");


        final String userIdKey = "application.jdbc.sync.server.user.id";
        String userId =  Config.getConfig(userIdKey);
        if(!isConfig(userId, userIdKey)){
            throw new ConfigException("not config: " + userIdKey);
        }

        final String userPasswordKey = "application.jdbc.sync.server.user.password";
        String password =  Config.getConfig(userPasswordKey);
        if(!isConfig(password, userPasswordKey)){
            throw new ConfigException("not config: " + userPasswordKey);
        }

        url = url.trim();

        encryptFlag = encryptFlag.trim();
        userId = userId.trim();
        password = password.trim();

        encryptFlag = encryptFlag.toUpperCase();
        if("Y".equals(encryptFlag)){
            String [] loginInfos = LoginCrypto.decryption(userId,password);
            userId = loginInfos[0];
            password = loginInfos[1];
        } else if (encryptFlag.startsWith("SCM,")) {
            String mapKey = encryptFlag.substring(4);
            CharMapManager charMapManager = CharMapManager.getInstance();
            CharMap charMap = charMapManager.getCharMap(mapKey);
            String [] loginInfos = LoginCrypto.decryption(userId,password, 32, charMap);
            userId = loginInfos[0];
            password = loginInfos[1];
        }

        if(databaseTypeOrFullPackage == null){
            throw new ConfigException("not config: application.jdbc.sync.server.type, application.jdbc.sync.server.driver.class" );
        }

        Connection connection;
        try {
            connection = ConnectionFactory.newConnection(databaseTypeOrFullPackage, url, userId, password);
        }catch (Exception e){
            throw new ConfigException(e);
        }

        return connection;
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean isConfig(String value, String keyMessage){
        //noinspection RedundantIfStatement
        if(value == null || "".equals(value.trim())){
            return false;
        }

        return true;
    }

}
