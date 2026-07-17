package com.lobox.challenge.lobxchallenge.helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class JdbcHelper {

    public static void setInteger(PreparedStatement ps , int parameter , String value) throws SQLException {
        Integer normalized = TsvFileNormalizerHelper.normalizeInteger(value);
        if (normalized == null) {
            ps.setNull(parameter , Types.INTEGER);
        } else {
            ps.setInt(parameter , normalized);
        }
    }

    public static Integer getNullableInteger(ResultSet resultSet, String column) throws java.sql.SQLException {
        int value = resultSet.getInt(column);
        return resultSet.wasNull() ? null : value;
    }
}
