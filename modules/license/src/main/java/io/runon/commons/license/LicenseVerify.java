package io.runon.commons.license;
/**
 * 라이센스 로컬 인증 관련(서버연결 X)
 * 라이선스 체크에 필요한항목
 *  OS 이름
 * 맥주소
 * 기간
 * @author macle
 */
public enum LicenseVerify {


    OS_UUID_ERROR("os uuid error: os change check")
    , HOST_NAME_ERROR("host name error")
    , MAC_ADDRESS_ERROR("mac address error")
    , EXPIRED_ERROR("It's expired")
    , DATA_VALIDATION_ERROR("Data validation error")
    , ADD_DATA_VALIDATION_ERROR("Data validation error(add data)")
    , DECRYPTION_ERROR("decryption error: password(key) check")
    , EXCEPTION("exception: error log check")

    , SUCCESS( "success")
    ;


    private final String message;
    LicenseVerify(String message){
        this.message = message;

    }

    public String getMessage(){
        return message;
    }


}
