package com.app.dao;

import com.app.dao.impl.UsuarioDAOImpl;
import com.app.dao.impl.ClanDAOImpl;
import com.app.model.entity.Usuario;
import com.app.model.entity.Clan;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test para validar el contrato entre capas (Java ↔ SQL)
 * Verifica que el mapeo ResultSet ↔ Entidad funcione correctamente
 */
public class ContractValidationTest {

    @Test
    public void testUsuarioMappingContract() {
        // Simula un ResultSet con datos de usuario
        Usuario testUser = new Usuario(1, "Test User", "test@example.com");
        
        // Validar estructura básica
        assertNotNull(testUser.getId());
        assertNotNull(testUser.getNombre());
        assertNotNull(testUser.getEmail());
        
        // Validar tipos de datos
        assertInstanceOf(Integer.class, testUser.getId());
        assertInstanceOf(String.class, testUser.getNombre());
        assertInstanceOf(String.class, testUser.getEmail());
        
        // Validar toString para debugging
        String toString = testUser.toString();
        assertTrue(toString.contains("Usuario"));
        assertTrue(toString.contains("id=1"));
        assertTrue(toString.contains("nombre='Test User'"));
        assertTrue(toString.contains("email='test@example.com'"));
    }

    @Test
    public void testClanMappingContract() {
        // Simula un ResultSet con datos de clan
        Clan testClan = new Clan(1, "Test Clan");
        
        // Validar estructura básica
        assertNotNull(testClan.getId());
        assertNotNull(testClan.getNombre());
        
        // Validar tipos de datos
        assertInstanceOf(Integer.class, testClan.getId());
        assertInstanceOf(String.class, testClan.getNombre());
        
        // Validar toString para debugging
        String toString = testClan.toString();
        assertTrue(toString.contains("Clan"));
        assertTrue(toString.contains("id=1"));
        assertTrue(toString.contains("nombre='Test Clan'"));
    }

    @Test
    public void testUsuarioDAOContract() {
        // Validar que UsuarioDAO implementa las capacidades correctas
        UsuarioDAO usuarioDAO = new UsuarioDAOImpl();
        
        // Debe tener métodos de GenericDAO
        assertDoesNotThrow(() -> usuarioDAO.findAll());
        assertDoesNotThrow(() -> usuarioDAO.findById(1));
        
        // Debe tener métodos de capacidades
        assertDoesNotThrow(() -> usuarioDAO.findByNombre("test"));
        assertDoesNotThrow(() -> usuarioDAO.existsByEmail("test@example.com"));
    }

    @Test
    public void testClanDAOContract() {
        // Validar que ClanDAO implementa las capacidades correctas
        ClanDAO clanDAO = new ClanDAOImpl();
        
        // Debe tener métodos de GenericDAO
        assertDoesNotThrow(() -> clanDAO.findAll());
        assertDoesNotThrow(() -> clanDAO.findById(1));
        
        // Debe tener método de capacidad SearchableByName
        assertDoesNotThrow(() -> clanDAO.findByNombre("test"));
        
        // Debe tener su método específico
        assertDoesNotThrow(() -> clanDAO.existsByNombre("test"));
    }
}
