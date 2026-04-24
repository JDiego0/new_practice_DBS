package com.app.dao.capabilities;

import java.util.List;

/**
 * Interfaz genérica para entidades que pueden ser buscadas por nombre.
 * Sigue el principio DRY al evitar duplicación en múltiples DAOs.
 */
public interface SearchableByName<T> {
    List<T> findByNombre(String nombre);
}
