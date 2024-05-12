package org.example.util.logger;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class CustomLogFormatter extends Formatter {
    @Override
    public String format(LogRecord record) {
        StringBuilder builder = new StringBuilder();
        builder.append(record.getMessage());
        builder.append(System.lineSeparator());
        return builder.toString();
    }
}
