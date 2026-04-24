# MVC-DAO-JDBC Project

Aplicación Java que implementa el patrón MVC con DAO y JDBC para la gestión de usuarios y clanes.

## 📋 Descripción

Proyecto académico que demuestra buenas prácticas en el diseño de arquitectura de software utilizando:
- **Patrón MVC** (Model-View-Controller)
- **Patrón DAO** (Data Access Object) con interfaces por capacidades
- **JDBC** para conexión a base de datos
- **Principios SOLID** (DRY, ISP, YAGNI)

## 🏗️ Arquitectura

```
src/main/java/com/app/
├── Main.java                    # Punto de entrada
├── config/
│   └── AppConfig.java          # Configuración de la aplicación
├── controller/
│   ├── UsuarioController.java  # Controlador de usuarios
│   └── ClanController.java     # Controlador de clanes
├── dao/
│   ├── GenericDAO.java         # Interfaz DAO genérica
│   ├── UsuarioDAO.java         # Interfaz DAO de usuarios
│   ├── ClanDAO.java            # Interfaz DAO de clanes
│   ├── capabilities/           # Interfaces por capacidades
│   │   ├── SearchableByName.java
│   │   ├── Contactable.java
│   │   └── ValidableByName.java
│   ├── impl/                   # Implementaciones concretas
│   │   ├── GenericDAOImpl.java
│   │   ├── UsuarioDAOImpl.java
│   │   └── ClanDAOImpl.java
│   └── util/
│       └── ResultSetMapper.java
├── model/
│   └── entity/
│       ├── Usuario.java        # Entidad Usuario
│       └── Clan.java           # Entidad Clan
└── view/
    ├── View.java               # Interfaz de vista
    ├── ConsoleView.java        # Implementación consola
    └── SwingView.java          # Implementación GUI
```

## 🚀 Características

### ✅ Implementaciones Destacadas

- **Interfaces por Capacidades**: Evitan duplicación de código siguiendo DRY
- **Validación de Datos**: 
  - Usuarios: validación por email único
  - Clanes: validación por nombre único
- **Sincronía Modelo-DB**: Entidades Java alineadas 1:1 con schema SQL
- **Principio YAGNI**: Solo funcionalidades necesarias, sin campos extra
- **Inyección de Dependencias**: Desacoplamiento entre componentes

### 🎯 Funcionalidades

#### Gestión de Usuarios
- Crear usuarios con validación de email único
- Listar todos los usuarios
- Buscar usuario por ID
- Actualizar datos de usuario
- Eliminar usuario

#### Gestión de Clanes
- Crear clanes con validación de nombre único
- Listar todos los clanes
- Buscar clan por ID
- Actualizar nombre de clan
- Eliminar clan

## 🛠️ Tecnologías

- **Java 17**
- **Maven** para gestión de dependencias
- **JDBC** para acceso a datos
- **PostgreSQL** (base de datos principal)
- **MySQL** (soporte alternativo)
- **JUnit 5** para pruebas

## 📦 Instalación y Configuración

### Prerrequisitos
- Java 17 o superior
- Maven 3.6+
- PostgreSQL o MySQL

### 1. Clonar el proyecto
```bash
git clone <repository-url>
cd new_practice
```

### 2. Configurar base de datos

#### Opción A: PostgreSQL
```sql
CREATE DATABASE appdb;
```

#### Opción B: MySQL
```sql
CREATE DATABASE appdb;
```

### 3. Configurar conexión

Editar `src/main/resources/database.properties`:

```properties
# Para PostgreSQL
db.url      = jdbc:postgresql://localhost:5432/appdb
db.user     = tu_usuario
db.password = tu_contraseña
db.driver   = org.postgresql.Driver

# Para MySQL (descomentar y comentar PostgreSQL)
#db.url      = jdbc:mysql://localhost:3306/appdb
#db.user     = root
#db.password = tu_contraseña
#db.driver   = com.mysql.cj.jdbc.Driver
```

### 4. Crear tablas

Ejecutar el script `src/main/resources/schema.sql`:

```bash
psql -d appdb -f src/main/resources/schema.sql
```

O ejecutar manualmente:

```sql
-- Tablas PostgreSQL
CREATE TABLE IF NOT EXISTS usuarios (
    id      SERIAL PRIMARY KEY,
    nombre  VARCHAR(100) NOT NULL,
    email   VARCHAR(150) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS clans (
    id      SERIAL PRIMARY KEY,
    nombre  VARCHAR(100) NOT NULL UNIQUE
);

CREATE INDEX IF NOT EXISTS idx_nombre ON usuarios(nombre);
CREATE INDEX IF NOT EXISTS idx_clan_nombre ON clans(nombre);
```

### 5. Compilar y ejecutar

```bash
# Compilar
mvn clean compile

# Ejecutar
mvn exec:java -Dexec.mainClass="com.app.Main"
```

## 🎮 Uso

### Menú Principal

Al ejecutar la aplicación, verás el menú principal:

```
Menú Principal
1. Gestión de Usuarios
2. Gestión de Clanes
3. Salir
```

### Gestión de Usuarios

```
Gestión de Usuarios
1. Listar todos
2. Buscar por ID
3. Crear usuario
4. Actualizar usuario
5. Eliminar usuario
6. Salir
```

### Gestión de Clanes

```
Gestión de Clanes
1. Listar todos
2. Buscar por ID
3. Crear clan
4. Actualizar clan
5. Eliminar clan
6. Salir
```

## 🔧 Configuración de Vista

La aplicación soporta dos tipos de vista:

Editar `src/main/resources/app.properties`:

```properties
view.type=console    # Para vista por consola
# view.type=swing    # Para vista GUI
```

## 📊 Modelo de Datos

### Entidad Usuario
```java
public class Usuario {
    private int id;
    private String nombre;
    private String email;
}
```

### Entidad Clan
```java
public class Clan {
    private int id;
    private String nombre;
}
```

## 🧪 Pruebas

Ejecutar pruebas unitarias:

```bash
mvn test
```

## 🏆 Buenas Prácticas Implementadas

### Principios SOLID
- **SRP**: Cada clase tiene una única responsabilidad
- **OCP**: Interfaces abiertas para extensión, cerradas para modificación
- **LSP**: Implementaciones pueden sustituir a sus interfaces
- **ISP**: Interfaces segregadas por capacidades específicas
- **DIP**: Dependencia de abstracciones, no de implementaciones

### Patrones de Diseño
- **DAO**: Separación entre lógica de negocio y acceso a datos
- **Factory**: Creación de vistas según configuración
- **Strategy**: Diferentes implementaciones de vista

### Calidad de Código
- **DRY**: Sin duplicación mediante interfaces por capacidades
- **YAGNI**: Solo funcionalidades necesarias
- **KISS**: Implementaciones simples y directas
- **Validación**: Verificación de integridad de datos

## 🐛 Troubleshooting

### Problemas Comunes

1. **Error de conexión**
   - Verificar que la base de datos esté corriendo
   - Confirmar credenciales en `database.properties`

2. **Error de compilación**
   - Asegurar Java 17 instalado
   - Limpiar y recompilar: `mvn clean compile`

3. **Tablas no encontradas**
   - Ejecutar script `schema.sql`
   - Verificar nombre correcto de la base de datos

## 📝 Licencia

Proyecto educativo para fines académicos.

## 👥 Autores

Desarrollado como parte de proyectos de formación en arquitectura de software.

---

**Versión**: 1.0.0  
**Última actualización**: 2025
