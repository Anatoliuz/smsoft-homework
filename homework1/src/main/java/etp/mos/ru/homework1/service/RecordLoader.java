package etp.mos.ru.homework1.service;

import etp.mos.ru.homework1.exceptions.CustomException;
import etp.mos.ru.homework1.model.CsvRecord;
import etp.mos.ru.homework1.props.Props;
import org.apache.commons.csv.CSVFormat;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class RecordLoader {

    private final Props props;

    public RecordLoader(Props props) {
        this.props = props;
    }

    public List<CsvRecord> getRecordsFromCsv() throws CustomException {
        List<CsvRecord> records = new ArrayList<>();
        try (
                var br = new BufferedReader(new FileReader(props.getFilePath()));
                var parser = CSVFormat.DEFAULT.withDelimiter(';').withHeader().parse(br);
        ) {
            var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");
            for (var record : parser) {
                var CsvRecord = new CsvRecord(
                        Integer.parseInt(record.get("id")),
                        record.get("servicenumber"),
                        Integer.parseInt(record.get("statusCode")),
                        LocalDateTime.parse(record.get("created"), formatter),
                        record.get("msgId"),
                        LocalDateTime.parse(record.get("put"), formatter),
                        record.get("reasonCode").isEmpty() ? null : Integer.parseInt(record.get("reasonCode")),
                        Boolean.parseBoolean(record.get("route")),
                        record.get("direction")
                );
                records.add(CsvRecord);
            }
        } catch (Exception e) {
            throw new CustomException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return records;
    }

}
