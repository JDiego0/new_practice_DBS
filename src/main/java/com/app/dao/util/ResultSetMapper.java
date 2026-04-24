package com.app.dao.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Function;

/**
 * Utilidad para mejorar la abstracción JDBC y validar el contrato entre capas.
 * Proporciona métodos seguros para mapear ResultSet a entidades.
 */
public class ResultSetMapper {

    /**
     * Mapea de forma segura un ResultSet a una entidad.
     * Valida que los campos existan antes de acceder.
     */
    public static <T> T mapRow(ResultSet rs, ThrowingFunction<ResultSet, T, SQLException> mapper, String entityName) throws SQLException {
        try {
            return mapper.apply(rs);
        } catch (SQLException e) {
            throw new SQLException(String.format(
                "Error mapeando ResultSet a %s. " +
                "Verifique que el esquema SQL coincida con la entidad Java. " +
                "Detalles: %s", entityName, e.getMessage()), e);
        }
    }

    /**
     * Interface funcional que puede lanzar SQLException.
     */
    @FunctionalInterface
    public interface ThrowingFunction<T, R, E extends Exception> {
        R apply(T t) throws E;
    }

    /**
     * Valida que todos los campos esperados existan en el ResultSet.
     * Útil para detectar desacople entre SQL y Java.
     */
    public static void validateFields(ResultSet rs, String[] requiredFields, String entityName) throws SQLException {
        for (String field : requiredFields) {
            try {
                rs.findColumn(field);
            } catch (SQLException e) {
                throw new SQLException(String.format(
                    "Campo '%s' no encontrado en ResultSet para %s. " +
                    "Posible desacople entre esquema SQL y entidad Java.", 
                    field, entityName), e);
            }
        }
    }

    /**
     * Obtiene un valor de forma segura con manejo de null.
     */
    public static String getStringSafely(ResultSet rs, String columnName) throws SQLException {
        String value = rs.getString(columnName);
        return rs.wasNull() ? null : value;
    }

    /**
     * Obtiene un entero de forma segura con manejo de null.
     */
    public static Integer getIntSafely(ResultSet rs, String columnName) throws SQLException {
        int value = rs.getInt(columnName);
        return rs.wasNull() ? null : value;
    }
}
