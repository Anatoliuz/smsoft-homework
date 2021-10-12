package etp.mos.ru.homework1.service;

import etp.mos.ru.homework1.model.CsvRecord;
import etp.mos.ru.homework1.props.Props;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecordFinder {

    private final Props props;

    public RecordFinder(Props props) {
        this.props = props;
    }

    public List<CsvRecord> getCountByDelay(long delay, List<CsvRecord> records){

    }

}
