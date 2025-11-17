package io.runon.system.update;

import io.runon.jdbc.annotation.*;
import io.runon.jdbc.objects.JdbcObjects;
import lombok.Data;

/**
 * @author macle
 */
@Data
@Table(name="file")
public class FileMetaUpdate {
    @PrimaryKey(seq = 1)
    @Column(name = "file_id")
    String fileId;

    @Column(name = "meta_data")
    String metaData;

    @DateTime
    @Column(name = "updated_at")
    long time = System.currentTimeMillis();

    public static void update(String fileId, String metaData) {
        FileMetaUpdate update = new FileMetaUpdate();
        update.fileId = fileId;
        update.metaData = metaData;
        update.time = System.currentTimeMillis();
        JdbcObjects.update(update);
    }

}
