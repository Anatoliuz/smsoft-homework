package ru.smsoft.loader.loader.util;

import ru.smsoft.loader.loader.model.Record;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Parser {

    public static List<Record> convertCsvToRecords(String pathToCsv) throws IOException {
        try (BufferedReader file = new BufferedReader(new FileReader(pathToCsv))) {
            List<Record> records = new ArrayList<Record>();
            file.readLine();
            String line;
            while ((line = file.readLine()) != null) {
                records.add(new Record(line));
            }
            return records;
        }catch (FileNotFoundException ex){
            ex.printStackTrace();
        }
        return null;
    }

}

