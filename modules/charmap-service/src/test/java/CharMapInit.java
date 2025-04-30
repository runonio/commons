import io.runon.commons.crypto.CharMapDataManagement;

/**
 * @author macle
 */
public class CharMapInit {

    public static void main(String[] args) {
        CharMapDataManagement charMapDataManagement = CharMapDataManagement.getInstance();
        charMapDataManagement.addRandomCharMap(0,4);

    }

}