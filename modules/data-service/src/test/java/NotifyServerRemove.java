import io.runon.commons.data.service.notify.NotifyCommons;

/**
 * redis 데이터가 잘 안보여서 가져다 보기위한 유틸성
 * @author macle
 */
public class NotifyServerRemove {
    public static void main(String[] args) {
        NotifyCommons.removeNotifyServer("macle");
    }
}
