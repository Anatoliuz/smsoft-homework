package ru.smsoft.loader.model;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String ssoid;
    private String ts;
    private String grp;
    private String type;
    private String subtype;
    private String url;
    private String orgid;
    private String formid;
    private String code;
    private String ltpa;
    private String sudirresponse;
    @Temporal(TemporalType.TIMESTAMP)
    private Date ymdh;


    public Record() {
    }

    public Record(String recordData) {
        /*
        ;           // Split on semicolon
        (?=         // Followed by
           (?:      // Start a non-capture group
             [^"]*  // 0 or more non-quote characters
             "      // 1 quote
             [^"]*  // 0 or more non-quote characters
             "      // 1 quote
           )*       // 0 or more repetition of non-capture group (multiple of 2 quotes will be even)
           [^"]*    // Finally 0 or more non-quotes
           $        // Till the end (This is necessary, else every comma will satisfy the condition)
        )
         */
        String[] splittedData = recordData.split(";(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
        this.ssoid = splittedData[0];
        this.ts = splittedData[1];
        this.grp = splittedData[2];
        this.type = splittedData[3];
        this.subtype = splittedData[4];
        this.url = splittedData[5];
        this.orgid = splittedData[6];
        this.formid = splittedData[7];
        this.code = splittedData[8];
        this.ltpa = splittedData[9];
        this.sudirresponse = splittedData[10];
        this.ymdh = converDateString(splittedData[11]);
    }

    private Date converDateString(String date) {
        final String DATE_FORMAT = "yyyy-MM-dd-hh";

        try {
            var format = new SimpleDateFormat(DATE_FORMAT);
            return format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

}
