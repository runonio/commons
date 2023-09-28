import io.runon.commons.data.service.notify.NotifyGmail;

/**
 * redis 데이터가 잘 안보여서 가져다 보기위한 유틸성
 * @author macle
 */
public class NotifyMailAdd {

    public static void main(String[] args) {
        NotifyGmail.addNotifyMail("ysys86a@gmail.com");
    }
}
