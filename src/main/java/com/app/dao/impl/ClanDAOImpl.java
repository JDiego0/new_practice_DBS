package com.app.dao.impl;

import com.app.dao.ClanDAO;
import com.app.model.entity.Clan;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClanDAOImpl extends GenericDAOImpl<Clan, Integer>
        implements ClanDAO {

    // ── SQL ──
    private static final String INSERT     = "INSERT INTO clanes (nombre) VALUES (?)";
    private static final String UPDATE     = "UPDATE clanes SET nombre=? WHERE id=?";
    private static final String DELETE     = "DELETE FROM clanes WHERE id=?";
    private static final String FIND_BY_ID = "SELECT * FROM clanes WHERE id=?";
    private static final String FIND_ALL   = "SELECT * FROM clanes";
    private static final String FIND_BY_NAME= "SELECT * FROM clanes WHERE nombre LIKE ?";
    private static final String EXISTS_BY_NOMBRE = "SELECT COUNT(*) FROM clanes WHERE nombre=?";


    // ── Mapeo ResultSet → Entidad ──
    @Override
    protected Clan mapRow(ResultSet rs) throws SQLException {
        return new Clan(
                rs.getInt("id"),
                rs.getString("nombre")
        );
    }

    // ── Parámetros SQL ──
    @Override protected String getInsertSQL()   { return INSERT; }
    @Override protected String getUpdateSQL()   { return UPDATE; }
    @Override protected String getDeleteSQL()   { return DELETE; }
    @Override protected String getFindByIdSQL() { return FIND_BY_ID; }
    @Override protected String getFindAllSQL()  { return FIND_ALL; }

    @Override
    protected void setInsertParams(PreparedStatement ps, Clan clan) throws SQLException {
        ps.setString(1, clan.getNombre());
    }
    @Override
    protected void setUpdateParams(PreparedStatement ps, Clan clan) throws SQLException {
        ps.setString(1, clan.getNombre());
        ps.setInt(2, clan.getId());
    }
    @Override
    protected void setDeleteParam(PreparedStatement ps, Integer id) throws SQLException {
        ps.setInt(1, id);
    }
    @Override
    protected void setFindByIdParam(PreparedStatement ps, Integer id) throws SQLException {
        ps.setInt(1, id);
    }

    // ── save devuelve el objeto con el ID generado ──
    @Override
    public Clan save(Clan u) {
        try (Connection conn = cm.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     INSERT, Statement.RETURN_GENERATED_KEYS)) {

            setInsertParams(ps, u);
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) u.setId(keys.getInt(1));
            }
            return u;
        } catch (SQLException e) {
            throw new RuntimeException("Error en save(Clan)", e);
        }
    }

    // ── Métodos específicos de CLanDAO ──
    @Override
    public List<Clan> findByNombre(String nombre) {
        List<Clan> list = new ArrayList<>();
        try (Connection conn = cm.getConnection();
             PreparedStatement ps = conn.prepareStatement(FIND_BY_NAME)) {

            ps.setString(1, "%" + nombre + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error en findByNombre", e);
        }
        return list;
    }

    @Override
    public boolean existsByNombre(String nombre) {
        try (Connection conn = cm.getConnection();
             PreparedStatement ps = conn.prepareStatement(EXISTS_BY_NOMBRE)) {

            ps.setString(1, nombre);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error en existsByNombre", e);
        }
    }
}
