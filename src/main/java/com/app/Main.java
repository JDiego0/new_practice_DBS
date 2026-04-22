package com.app;

import com.app.config.AppConfig;
import com.app.controller.UsuarioController;
import com.app.controller.ClanController;
import com.app.dao.UsuarioDAO;
import com.app.dao.ClanDAO;
import com.app.dao.impl.UsuarioDAOImpl;
import com.app.dao.impl.ClanDAOImpl;
import com.app.view.ConsoleView;
import com.app.view.SwingView;
import com.app.view.View;

public class Main {

    public static void main(String[] args) {

        AppConfig config = AppConfig.getInstance();

        // Factory: elige la vista según app.properties
        View view = createView(config.getViewType());

        // Inyección de dependencias
        UsuarioDAO          usuarioDAO  = new UsuarioDAOImpl();
        ClanDAO             clanDAO     = new ClanDAOImpl();
        UsuarioController   usuarioController = new UsuarioController(view, usuarioDAO);
        ClanController      clanController    = new ClanController(view, clanDAO);

        view.showMessage("Bienvenido a " + config.getAppName());

        // Menú principal para elegir entre Usuario y Clan
        String[] mainMenuOptions = {
                "Gestión de Usuarios", "Gestión de Clanes", "Salir"
        };

        boolean running = true;
        while (running) {
            view.showMenu(mainMenuOptions, "Menú Principal");
            int choice = view.getMenuChoice();

            switch (choice) {
                case 1 -> usuarioController.run();
                case 2 -> clanController.run();
                case 3 -> running = false;
                default -> view.showError("Opción no válida");
            }
        }
        view.showMessage("¡Hasta luego!");
    }

    private static View createView(String type) {
        return switch (type.toLowerCase()) {
            case "swing" -> new SwingView();
            default       -> new ConsoleView();
        };
    }
}
