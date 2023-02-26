package io.runon.commons.keyword;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;
import com.seomse.jdbc.annotation.*;
import lombok.Data;

/**
 * @author macle
 */
@Data
@Table(name="keyword")
public class Keyword {
    @PrimaryKey(seq = 1)@Sequence(name ="seq_keyword")
    @Column(name = "keyword_no")
    Long number;

    @Column(name = "keyword")
    String keyword;

    @Column(name = "data_value")
    String dataValue;

    @Column(name = "checksum")
    String checksum;

    @DateTime
    @Column(name = "updated_at")
    Long time;

    public boolean equals(String keyword, String dataValue){
        if(!keyword.equals(this.keyword)){
            return false;
        }

        if(this.dataValue == null && dataValue == null){
            return true;
        }

        //noinspection PointlessNullCheck
        return this.dataValue != null && dataValue != null && this.dataValue.equals(dataValue);

    }

    @Override
    public String toString(){
        return new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).setPrettyPrinting().create().toJson(this);
    }
}
