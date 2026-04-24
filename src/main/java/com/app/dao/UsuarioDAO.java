package com.app.dao;

import com.app.dao.capabilities.Contactable;
import com.app.dao.capabilities.SearchableByName;
import com.app.model.entity.Usuario;

/**
 * Extiende GenericDAO añadiendo búsquedas específicas de Usuario.
 * Usa interfaces por capacidades para seguir DRY e ISP.
 */
public interface UsuarioDAO extends GenericDAO<Usuario, Integer>, 
                                       SearchableByName<Usuario>, 
                                       Contactable<Usuario> {
}