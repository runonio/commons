import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.seomse.commons.http.HttpApis;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

public class Test {
    public static void main(String[] args) throws IOException {

        File file = new File("D:\\업무\\test.jpg");
        byte[] bytes = Files.readAllBytes(file.toPath());
        String encodeByte = Base64.getEncoder().encodeToString(bytes);

        JsonObject obj = new JsonObject();
        obj.addProperty("file_name", file.getName());
        obj.addProperty("file_bytes", encodeByte);
        String result = HttpApis.POST_JSON_APT.getMessage("http://dev.runon.io:31335/text/filejson", new Gson().toJson(obj));

        System.out.println(result);
    }
}
