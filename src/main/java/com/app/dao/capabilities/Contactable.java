package com.app.dao.capabilities;

/**
 * Interfaz genérica para entidades que pueden ser validadas por email.
 * Aplica el principio ISP (Interface Segregation Principle).
 */
public interface Contactable<T> {
    boolean existsByEmail(String email);
}
