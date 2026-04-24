package com.app.dao;

import com.app.dao.capabilities.SearchableByName;
import com.app.dao.capabilities.ValidableByName;
import com.app.model.entity.Clan;

/**
 * Extiende GenericDAO añadiendo búsquedas específicas de Clan.
 * Usa interfaces por capacidades para seguir DRY e ISP.
 */
public interface ClanDAO extends GenericDAO<Clan, Integer>, 
                                     SearchableByName<Clan>,
                                     ValidableByName<Clan> {
}
