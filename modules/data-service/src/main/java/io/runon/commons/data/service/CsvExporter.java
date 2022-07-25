package io.runon.commons.data.service;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SequenceWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.seomse.commons.utils.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * CSV OUTPUT
 * @author ccsweets
 */
@Slf4j
public class CsvExporter {
    /**
     * csv파일에 데이터를 추가 한다.
     * @param clazz
     * @param csvFile
     * @param dataList
     * @return
     */
    public static void appendToFile(Class<?> clazz, File csvFile, List<?> dataList) {
        appendToFile(clazz, csvFile, dataList, ',', true);
    }

    /**
     * csv파일에 데이터를 추가 한다.
     * @param clazz
     * @param csvFile
     * @param dataList
     * @return
     */
    public static void appendToFile(Class<?> clazz, File csvFile, List<?> dataList, boolean withHeader) {
        appendToFile(clazz, csvFile, dataList, ',', withHeader);
    }

    /**
     * csv파일에 데이터를 추가 한다.
     * @param clazz
     * @param csvFile
     * @param dataList
     * @param separator
     * @return
     */
    public static void appendToFile(Class<?> clazz, File csvFile, List<?> dataList, char separator, boolean withHeader) {
        CsvMapper mapper = new CsvMapper();
        mapper.configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true);
        try {
            if (!csvFile.exists()) {
                writeToFile(clazz, csvFile, dataList);
                return;
            }

            CsvSchema csvSchema = mapper
                    .schemaFor(clazz).withHeader().withColumnSeparator(separator).withLineSeparator("\n").withoutHeader();
            ObjectWriter writer = mapper.writer(csvSchema);

            for (Object data : dataList) {
                OutputStream outputStream = new FileOutputStream(csvFile , true);
                writer.writeValue(outputStream,data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * csv 파일을 새로 쓴다. (파일 내용 교체)
     * @param clazz
     * @param csvFile
     * @param dataList
     */
    public static void writeToFile(Class<?> clazz, File csvFile, List<?> dataList) {
        writeToFile(clazz, csvFile, dataList, ',', true);
    }

    public static void writeToFile(Class<?> clazz, File csvFile, List<?> dataList, boolean withHeader) {
        writeToFile(clazz, csvFile, dataList, ',', withHeader);
    }

    /**
     * csv 파일을 새로 쓴다. (파일 내용 교체)
     * @param clazz
     * @param csvFile
     * @param dataList
     * @param separator
     */
    public static void writeToFile(Class<?> clazz, File csvFile, List<?> dataList, char separator, boolean withHeader) {
        try {
            CsvMapper csvMapper = new CsvMapper();
            CsvSchema csvSchema = csvMapper
                    .schemaFor(clazz).withHeader().withColumnSeparator(separator).withLineSeparator("\n");
            if(!withHeader){
                csvSchema.withoutHeader();
            }
            ObjectWriter writer = csvMapper.writerFor(clazz).with(csvSchema);
            SequenceWriter sequenceWriter = writer.writeValues(csvFile);
            sequenceWriter.writeAll(dataList);
            sequenceWriter.close();
        } catch (Exception e) {
            log.error(ExceptionUtil.getStackTrace(e));
        }
    }
}
