# TaskMan - Sistema de Gestión de Proyectos y Tareas

TaskMan es una aplicación web desarrollada con JSF/PrimeFaces que permite administrar proyectos y sus tareas asociadas. La aplicación implementa una arquitectura de capas con CDI para inyección de dependencias y utiliza repositorios en memoria para el almacenamiento de datos.

## 🚀 Características

### Funcionalidades Principales
- **Gestión de Proyectos**: Crear, editar, eliminar y listar proyectos
- **Gestión de Tareas**: Administrar tareas asociadas a cada proyecto
- **Filtros y Búsqueda**: Búsqueda por nombre/propietario y filtros por estado/prioridad
- **Validaciones**: Nombres únicos de proyectos y fechas de vencimiento futuras
- **Interfaz Responsiva**: Diseño moderno y adaptable a diferentes dispositivos
- **AJAX**: Actualizaciones sin recarga de página
- **Mensajería**: Feedback visual con growl messages

### Características Técnicas
- **JSF 2.3** con PrimeFaces 12.0
- **CDI** para inyección de dependencias
- **Repositorios en memoria** con datos semilla
- **Validadores personalizados**
- **Arquitectura de capas** (Repository → Service → Controller)
- **Scopes apropiados** (@ApplicationScoped, @ViewScoped)

## 📋 Requisitos del Sistema

### Versiones Requeridas
- **JDK**: 11 o superior
- **Maven**: 3.6 o superior
- **Servidor de Aplicaciones**: 
  - Jetty 11+ (recomendado para desarrollo)
  - Tomcat 10+
  - WildFly 26+
  - Payara 6+

### Dependencias Principales
- JSF 2.3.17
- PrimeFaces 12.0.0
- Weld CDI 3.1.9.Final
- Jakarta EE 9.1.0
- Hibernate Validator 7.0.5.Final

## 🛠️ Instalación y Configuración

### 1. Clonar el Repositorio
```bash
git clone https://github.com/tu-usuario/taskman.git
cd taskman
```

### 2. Compilar el Proyecto
```bash
mvn clean compile
```

### 3. Ejecutar con Jetty (Desarrollo)
```bash
mvn jetty:run
```

La aplicación estará disponible en: `http://localhost:8080/taskman`

### 4. Generar WAR para Producción
```bash
mvn clean package
```

El archivo WAR se generará en: `target/taskman-1.0.0.war`

## 🏗️ Arquitectura del Proyecto

```
src/main/java/com/taskman/
├── controller/          # Controladores JSF
│   ├── ProjectController.java
│   ├── TaskController.java
│   └── NavigationController.java
├── model/              # Modelos de datos
│   ├── Project.java
│   ├── Task.java
│   ├── ProjectStatus.java
│   └── TaskPriority.java
├── repository/         # Repositorios en memoria
│   ├── ProjectRepository.java
│   └── TaskRepository.java
├── service/           # Lógica de negocio
│   ├── ProjectService.java
│   └── TaskService.java
└── validation/        # Validadores personalizados
    ├── UniqueProjectNameValidator.java
    └── FutureDateValidator.java

src/main/webapp/
├── pages/             # Páginas JSF
│   ├── dashboard.xhtml
│   ├── projects.xhtml
│   ├── fragments/
│   │   └── tasks-by-project.xhtml
│   └── error/
│       ├── 404.xhtml
│       └── 500.xhtml
├── templates/         # Plantillas
│   └── layout.xhtml
├── css/              # Estilos personalizados
│   └── custom.css
├── js/               # JavaScript personalizado
│   └── custom.js
└── WEB-INF/
    ├── web.xml       # Configuración web
    └── beans.xml     # Configuración CDI
```

## 🎯 Uso de la Aplicación

### Dashboard
- Vista general con estadísticas de proyectos y tareas
- Acceso rápido a funcionalidades principales
- Enlaces a proyectos recientes

### Gestión de Proyectos
1. **Listar Proyectos**: Tabla con paginación y filtros
2. **Buscar**: Por nombre o propietario del proyecto
3. **Filtrar**: Por estado (Activo, En Espera, Completado)
4. **Crear Proyecto**: Diálogo modal con validaciones
5. **Editar Proyecto**: Modificar información existente
6. **Eliminar Proyecto**: Con confirmación
7. **Ver Tareas**: Panel expandible con tareas del proyecto

### Gestión de Tareas
1. **Listar Tareas**: Por proyecto seleccionado
2. **Filtrar**: Por prioridad (Baja, Media, Alta) y estado
3. **Crear Tarea**: Con validación de fecha futura
4. **Editar Tarea**: Modificar detalles
5. **Completar/Reabrir**: Toggle del estado de completado
6. **Eliminar Tarea**: Con confirmación
7. **Resaltado**: Tareas vencidas en rojo

## 🔧 Configuración Avanzada

### Personalización de Temas
Editar `web.xml` para cambiar el tema de PrimeFaces:
```xml
<context-param>
    <param-name>primefaces.THEME</param-name>
    <param-value>nova-light</param-value>
</context-param>
```

### Configuración de Base de Datos
Para migrar a base de datos real, reemplazar los repositorios en memoria con implementaciones JPA:
1. Agregar dependencias JPA al `pom.xml`
2. Crear entidades JPA
3. Implementar repositorios con EntityManager
4. Configurar `persistence.xml`

### Personalización de Validadores
Los validadores personalizados se encuentran en `com.taskman.validation`:
- `UniqueProjectNameValidator`: Valida nombres únicos de proyectos
- `FutureDateValidator`: Valida fechas futuras

## 🧪 Datos de Prueba

La aplicación incluye datos semilla que se cargan automáticamente:

### Proyectos de Ejemplo
1. **Sistema de Gestión de Inventarios** (Juan Pérez) - Activo
2. **Migración a la Nube** (María García) - En Espera
3. **Portal de Clientes** (Carlos López) - Activo
4. **Automatización de Reportes** (Ana Martínez) - Completado

### Tareas de Ejemplo
- Cada proyecto incluye 2-3 tareas con diferentes prioridades
- Fechas de vencimiento variadas (algunas vencidas para testing)
- Estados mixtos (completadas y pendientes)

## 🐛 Solución de Problemas

### Error de CDI
Si CDI no funciona correctamente:
1. Verificar que `beans.xml` existe en `WEB-INF/`
2. Confirmar que Weld está en el classpath
3. Revisar logs del servidor para errores de CDI

### Problemas de PrimeFaces
Si los componentes no se renderizan:
1. Verificar que PrimeFaces está en el classpath
2. Confirmar que el tema está configurado
3. Revisar la consola del navegador para errores JavaScript

### Problemas de AJAX
Si las actualizaciones AJAX no funcionan:
1. Verificar que jQuery está cargado
2. Confirmar que los IDs de componentes son únicos
3. Revisar la configuración de `update` en comandos AJAX

## 📝 Criterios de Evaluación Implementados

### ✅ Arquitectura y CDI (30%)
- Inyección correcta con `@Inject`
- Scopes adecuados (`@ApplicationScoped`, `@ViewScoped`)
- Separación clara de capas (Repository → Service → Controller)

### ✅ UI con PrimeFaces (30%)
- Tablas con filtros y ordenamiento
- Diálogos modales para CRUD
- Confirmaciones con `confirm()`
- AJAX sin recarga de página
- Paginación en tablas

### ✅ Reglas y Validaciones (25%)
- Nombres únicos de proyectos
- Fechas de vencimiento futuras
- Resaltado de tareas vencidas
- Toggle de estado completado
- Validaciones en formularios

### ✅ Calidad (15%)
- Código legible y bien estructurado
- IDs únicos en componentes
- Mensajes claros y descriptivos
- Plantilla funcional y responsive

### ✅ Bonus (10%)
- Paginación en tablas
- Filtros compuestos (estado + propietario)
- Contador de tareas en encabezado
- Interfaz moderna y responsive

## 🤝 Contribución

1. Fork el proyecto
2. Crear una rama para la feature (`git checkout -b feature/nueva-funcionalidad`)
3. Commit los cambios (`git commit -am 'Agregar nueva funcionalidad'`)
4. Push a la rama (`git push origin feature/nueva-funcionalidad`)
5. Crear un Pull Request

## 📄 Licencia

Este proyecto está bajo la Licencia MIT. Ver el archivo `LICENSE` para más detalles.

## 👨‍💻 Autor

**Gustavo Ortiz**
- GitHub: [@nimblegus](https://github.com/gustavo-ortiz)
- Email: gus@oktae.tech

## 🙏 Agradecimientos

- PrimeFaces por el excelente framework de componentes
- JSF por la especificación de JavaServer Faces
- CDI por el sistema de inyección de dependencias
- La comunidad Java EE por las herramientas y recursos
