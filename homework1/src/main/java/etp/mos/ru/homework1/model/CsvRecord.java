package etp.mos.ru.homework1.model;

import java.time.LocalDateTime;

public record CsvRecord(int id, String serviceNumber, Integer statusCode,
                        LocalDateTime created, String msgId, LocalDateTime put, Integer reasonCode,
                        boolean route, String direction) {

}
