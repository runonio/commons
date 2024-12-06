package io.runon.jdbc.example.objects;

import io.runon.jdbc.annotation.Column;
import io.runon.jdbc.annotation.PrimaryKey;
import io.runon.jdbc.annotation.Sequence;
import io.runon.jdbc.annotation.Table;


/**
 * @author macle
 */
@Table(name="file")
public class AttachFile {

    @PrimaryKey(seq = 1)
    @Column(name = "file_no") @Sequence(name = "seq_attach_file")
    Long fileNo;

    @Column(name = "file_name")
    String fileName;

    @Column(name = "attach_file")
    byte[] bytes;

}
