package com.app.controller;

import com.app.dao.ClanDAO;
import com.app.model.entity.Clan;
import com.app.view.View;

import java.util.List;
import java.util.Optional;

/**
 * Controlador de clanes.
 * Recibe eventos de la Vista, orquesta el DAO y devuelve resultados a la Vista.
 * No sabe si la Vista es consola o Swing -> depende de la interfaz View.
 */
public class ClanController {

    private final View view;
    private final ClanDAO clanDAO;

    // Inyección de dependencias por constructor
    public ClanController(View view, ClanDAO clanDAO) {
        this.view = view;
        this.clanDAO = clanDAO;
    }

    // ── Menú principal ──
    public void run() {
        String[] menuOptions = {
                "Listar todos", "Buscar por ID", "Crear clan",
                "Actualizar clan", "Eliminar clan", "Salir"
        };

        boolean running = true;
        while (running) {
            view.showMenu(menuOptions, "Gestión de Clanes");
            int choice = view.getMenuChoice();

            switch (choice) {
                case 1 -> listarTodos();
                case 2 -> buscarPorId();
                case 3 -> crearClan();
                case 4 -> actualizarClan();
                case 5 -> eliminarClan();
                case 6 -> running = false;
                default -> view.showError("Opción no válida");
            }
        }
        view.showMessage("¡Hasta luego!");
    }

    // ── Operaciones CRUD ──

    public void listarTodos() {
        List<Clan> clanes = clanDAO.findAll();
        if (clanes.isEmpty()) {
            view.showMessage("No hay clanes registrados.");
        } else {
            view.showClanes(clanes);
        }
    }

    public void buscarPorId() {
        String input = view.askInput("ID del clan");
        try {
            int id = Integer.parseInt(input);
            Optional<Clan> clan = clanDAO.findById(id);
            if (clan.isPresent()) {
                view.showClan(clan.get());
            } else {
                view.showError("No se encontró clan con ID " + id);
            }
        } catch (NumberFormatException e) {
            view.showError("ID inválido: " + input);
        }
    }

    public void crearClan() {
        String nombre = view.askInput("Nombre del clan");

        if (nombre.isBlank()) {
            view.showError("El nombre es requerido.");
            return;
        }
        if (clanDAO.existsByNombre(nombre)) {
            view.showError("Ya existe un clan con ese nombre.");
            return;
        }

        Clan nuevo = new Clan(0, nombre);
        clanDAO.save(nuevo);
        view.showMessage("Clan creado con ID: " + nuevo.getId());
    }

    public void actualizarClan() {
        String input = view.askInput("ID del clan a actualizar");
        try {
            int id = Integer.parseInt(input);
            Optional<Clan> opt = clanDAO.findById(id);
            if (opt.isEmpty()) {
                view.showError("No se encontró clan con ID " + id);
                return;
            }
            Clan c = opt.get();
            String nombre = view.askInput("Nuevo nombre [" + c.getNombre() + "]");

            if (!nombre.isBlank()) c.setNombre(nombre);

            boolean ok = clanDAO.update(c);
            view.showMessage(ok ? "Clan actualizado." : "No se pudo actualizar.");
        } catch (NumberFormatException e) {
            view.showError("ID inválido.");
        }
    }

    public void eliminarClan() {
        String input = view.askInput("ID del clan a eliminar");
        try {
            int id = Integer.parseInt(input);
            if (view.confirm("¿Confirmar eliminación del clan " + id + "?")) {
                boolean ok = clanDAO.deleteById(id);
                view.showMessage(ok ? "Clan eliminado." : "No se encontró el clan.");
            }
        } catch (NumberFormatException e) {
            view.showError("ID inválido.");
        }
    }
}