package io.runon.commons.classification;

import io.runon.commons.utils.GsonUtils;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class Prediction {
    String classificationId;
    String label;
    String modelId;
    BigDecimal score;
    int modelsIndex;

    @Override
    public String toString(){
        return GsonUtils.LOWER_CASE_WITH_UNDERSCORES_PRETTY.toJson(this);
    }

}
