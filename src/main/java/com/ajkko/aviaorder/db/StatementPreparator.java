package com.ajkko.aviaorder.db;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface StatementPreparator {
    void prepare(PreparedStatement statement) throws SQLException;
}
