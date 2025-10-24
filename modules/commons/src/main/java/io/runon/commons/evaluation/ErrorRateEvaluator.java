package io.runon.commons.evaluation;

import io.runon.commons.data.StringArray;

import java.util.Objects;
/**
 * 오류율 계산
 * wer, cer 등에 활용
 * @author macle
 */
public class ErrorRateEvaluator {

    // 계산된 오류 메트릭을 저장하기 위한 내부 클래스
    public static String cleanText(String text) {
        // 각종 구두점 및 특수 기호를 제거
        String cleaned = text.replaceAll("[.,?!~\"'`():;]", "");
        // 여러 개의 공백을 하나의 공백으로 변경하고 양 끝 공백 제거
        return cleaned.replaceAll("\\s+", " ").trim();
    }

    public static ErrorRateMetrics wer(String ref, String pred){
        return calculateDetailedMetrics(cleanText(ref).split(" "), cleanText(pred).split(" "));
    }

    public static ErrorRateMetrics cer(String ref, String pred){
        return calculateDetailedMetrics(cleanText(ref).replace(" ","").toCharArray(), cleanText(pred).replace(" ","").toCharArray());
    }

    public static ErrorRateMetrics wer(StringArray [] array){
        StringArray first = array[0];

        ErrorRateMetrics errorRateMetrics = wer(first.get(0), first.get(1));

        for (int i = 1; i < array.length; i++) {
            StringArray refPred =  array[i];
            errorRateMetrics.add(wer(refPred.get(0), refPred.get(1)));
        }
        return errorRateMetrics;
    }

    public static ErrorRateMetrics cer(StringArray [] array){
        StringArray first = array[0];

        ErrorRateMetrics errorRateMetrics = cer(first.get(0), first.get(1));

        for (int i = 1; i < array.length; i++) {
            StringArray refPred =  array[i];
            errorRateMetrics.add(cer(refPred.get(0), refPred.get(1)));
        }
        return errorRateMetrics;
    }

    /**
     * CER 계산을 위해 char[] 배열을 받아 S, D, I, C를 계산합니다.
     */
    public static ErrorRateMetrics calculateDetailedMetrics(char[] ref, char[] pred) {
        int[][] dp = new int[ref.length + 1][pred.length + 1];
        Op[][] op = new Op[ref.length + 1][pred.length + 1];

        for (int i = 0; i <= ref.length; i++) {
            dp[i][0] = i;
            op[i][0] = Op.DEL;
        }
        for (int j = 0; j <= pred.length; j++) {
            dp[0][j] = j;
            op[0][j] = Op.INS;
        }

        for (int i = 1; i <= ref.length; i++) {
            for (int j = 1; j <= pred.length; j++) {
                int cost = (ref[i - 1] == pred[j - 1]) ? 0 : 1; // char 직접 비교
                int delCost = dp[i - 1][j] + 1;
                int insCost = dp[i][j - 1] + 1;
                int subCost = dp[i - 1][j - 1] + cost;

                dp[i][j] = Math.min(Math.min(delCost, insCost), subCost);

                if (dp[i][j] == subCost) {
                    op[i][j] = (cost == 0) ? Op.HIT : Op.SUB;
                } else if (dp[i][j] == delCost) {
                    op[i][j] = Op.DEL;
                } else {
                    op[i][j] = Op.INS;
                }
            }
        }

        int s = 0, d = 0, i = 0, h = 0;
        int r_idx = ref.length, p_idx = pred.length;
        while (r_idx > 0 || p_idx > 0) {
            if (op[r_idx][p_idx] == null || op[r_idx][p_idx] == Op.NONE) break;
            switch (op[r_idx][p_idx]) {
                case HIT: h++; r_idx--; p_idx--; break;
                case SUB: s++; r_idx--; p_idx--; break;
                case DEL: d++; r_idx--; break;
                case INS: i++; p_idx--; break;
            }
        }
        return new ErrorRateMetrics(s, d, i, h);
    }

    // Levenshtein 알고리즘(Wagner-Fischer)을 사용하여 S, D, I, C 상세 계산
    public static ErrorRateMetrics calculateDetailedMetrics(String[] ref, String[] pred) {
        int[][] dp = new int[ref.length + 1][pred.length + 1];
        Op[][] op = new Op[ref.length + 1][pred.length + 1];

        for (int i = 0; i <= ref.length; i++) {
            dp[i][0] = i;
            op[i][0] = Op.DEL;
        }
        for (int j = 0; j <= pred.length; j++) {
            dp[0][j] = j;
            op[0][j] = Op.INS;
        }

        for (int i = 1; i <= ref.length; i++) {
            for (int j = 1; j <= pred.length; j++) {
                int cost = Objects.equals(ref[i - 1], pred[j - 1]) ? 0 : 1;
                int delCost = dp[i - 1][j] + 1;
                int insCost = dp[i][j - 1] + 1;
                int subCost = dp[i - 1][j - 1] + cost;

                dp[i][j] = Math.min(Math.min(delCost, insCost), subCost);

                if (dp[i][j] == subCost) {
                    op[i][j] = (cost == 0) ? Op.HIT : Op.SUB;
                } else if (dp[i][j] == delCost) {
                    op[i][j] = Op.DEL;
                } else {
                    op[i][j] = Op.INS;
                }
            }
        }

        int s = 0, d = 0, i = 0, h = 0;
        int r_idx = ref.length, p_idx = pred.length;
        while (r_idx > 0 || p_idx > 0) {
            if (op[r_idx][p_idx] == null || op[r_idx][p_idx] == Op.NONE) break;
            switch (op[r_idx][p_idx]) {
                case HIT: h++; r_idx--; p_idx--; break;
                case SUB: s++; r_idx--; p_idx--; break;
                case DEL: d++; r_idx--; break;
                case INS: i++; p_idx--; break;
            }
        }
        return new ErrorRateMetrics(s, d, i, h);
    }

    public enum Op { NONE, HIT, SUB, DEL, INS }

    public static void main(String[] args) {
        ErrorRateMetrics metrics = cer("김용수 김용 수 용수 1 2 1 313 1 131313", "김용수 용 수 용수 1 1 1 313 1 131313");
        System.out.println(metrics.getErrorPercent(2).toPlainString());
    }
}