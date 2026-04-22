package com.app.dao;

import com.app.model.entity.Clan;
import java.util.List;

public interface ClanDAO extends GenericDAO<Clan, Integer>{
    List<Clan> findByNombre(String nombre);
    boolean existsByNombre(String nombre);
}
