package io.runon.commons.classification;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Prediction {
    String classificationId;
    String label;
    String modelId;
    BigDecimal score;
    int modelsIndex;
}
