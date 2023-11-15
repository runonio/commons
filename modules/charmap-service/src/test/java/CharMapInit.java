import io.runon.commons.charmap.service.CharMapDataManagement;

/**
 * @author macle
 */
public class CharMapInit {

    public static void main(String[] args) {
        CharMapDataManagement charMapDataManagement = CharMapDataManagement.getInstance();
        charMapDataManagement.addRandomCharMap(1,5);

    }

}