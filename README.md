# Umbra Client

Cliente de Minecraft con módulos de combate, render, utilidad y HUD personalizable.

## Características

- **ClickGUI moderno** — menú de módulos por categorías con buscador, edición de valores, binds y macros
- **Módulos por categoría** — Combat, Render, Movement, Player, World y Utilidad
- **HUD personalizable** — mueve y configura tus overlays: watermark, keystrokes, coordinates, armor, potions, arraylist y más
- **Perfiles locales** — guarda y alterna configuraciones completas (PvP, PvE, etc.) sin conexión
- **Amigos y enemigos** — gestión local para ESP y target selection
- **Sin telemetría** — standalone: sin conexiones a servidores externos, sin launcher, sin cuentas

## Requisitos

- Windows x64
- Java 64-bit (el mismo que use tu launcher)
- Una instancia de Minecraft soportada:
  - Forge / Vanilla 1.7.10, 1.8.9, 1.12.2, 1.21.11 y 26.2
  - Fabric (1.21.11 y 26.2 con Fabric Loader 0.19.3)
  - Lunar Client (Forge)
- Para compilar: JDK 17, Gradle Wrapper incluido, Visual Studio 2022 C++ + CMake (solo bundle nativo)

## Instalación y uso

1. Descomprime el bundle (o usa la carpeta `build/injection`).
2. Abre Minecraft con tu instancia y entra a un mundo.
3. Inyecta el cliente:
   - **Automático:** doble clic en `inyectar.bat` (busca el proceso de Minecraft solo), o
   - **Manual:** ejecuta `UmbraInjector.exe`, elige el proceso con Up/Down y Enter.
4. Abre el menú con la tecla configurada (por defecto **RSHIFT**).

Si algo falla, revisa `umbra-native.log` junto al inyector.

## Compilar

```powershell
.\gradlew.bat clean build verifyInjectionPayload
```

El payload de inyección queda en `build/libs/` y el bundle completo en `build/injection/`:

```powershell
.\gradlew.bat prepareInjectionBundle
```

## Añadir un módulo propio

1. Crea una clase que extienda `gg.umbra.module.Mod` (o `UtilityMod`).
2. Usa `BooleanValue.create(this, "Nombre", default, "desc")` para valores.
3. Registra handlers con `@EventHandler`.
4. Regístrala en `gg.umbra.manager.ModManager.init()`:

```java
ModRegistrationBuilder.create().setModule(new MiModulo()).registerWith(this);
```

## Aviso

Software de trampas para Minecraft. Úsalo bajo tu propia responsabilidad: riesgo de baneos y violación del EULA de Mojang. Se recomienda usarlo únicamente en servidores privados, mundos singleplayer o entornos aislados, y con cuentas secundarias.
