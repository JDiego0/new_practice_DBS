package com.app.dao.capabilities;

/**
 * Interfaz genérica para entidades que pueden ser validadas por nombre único.
 * Sigue el principio DRY al evitar duplicación en múltiples DAOs.
 * Aplicable a entidades con nombres que deben ser únicos (clans, usuarios, etc.).
 */
public interface ValidableByName<T> {
    boolean existsByNombre(String nombre);
}
