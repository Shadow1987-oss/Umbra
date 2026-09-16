# Umbra Client

A Minecraft client with combat, render, utility and customizable HUD modules.

## Features

- **Modern ClickGUI** — module menu organized by categories with search, value editing, keybinds and macros
- **Categorized modules** — Combat, Render, Movement, Player, World and Utility
- **Customizable HUD** — move and configure your overlays: watermark, keystrokes, coordinates, armor, potions, arraylist and more
- **Local profiles** — save and switch between complete configurations (PvP, PvE, etc.) offline
- **Friends & enemies** — local management for ESP and target selection
- **No telemetry** — standalone: no external server connections, no launcher, no accounts

## Requirements

- Windows x64
- 64-bit Java (the same one your launcher uses)
- A supported Minecraft instance:
  - Forge / Vanilla 1.7.10, 1.8.9, 1.12.2, 1.21.11 and 26.2
  - Fabric (1.21.11 and 26.2 with Fabric Loader 0.19.3)
  - Lunar Client (Forge)
- To build: JDK 17, bundled Gradle Wrapper, Visual Studio 2022 C++ + CMake (native bundle only)

## Installation & usage

1. Extract the bundle (or use the `build/injection` folder).
2. Launch Minecraft with your instance and join a world.
3. Inject the client:
   - **Automatic:** double-click `inyectar.bat` (finds the Minecraft process by itself), or
   - **Manual:** run `UmbraInjector.exe`, pick the process with Up/Down and press Enter.
4. Open the menu with the bound key (default **RSHIFT**).

If something fails, check `umbra-native.log` next to the injector.

## Build

```powershell
.\gradlew.bat clean build verifyInjectionPayload
```

The injection payload lands in `build/libs/` and the full bundle in `build/injection/`:

```powershell
.\gradlew.bat prepareInjectionBundle
```

## Adding your own module

1. Create a class extending `gg.umbra.module.Mod` (or `UtilityMod`).
2. Use `BooleanValue.create(this, "Name", default, "desc")` for values.
3. Register handlers with `@EventHandler`.
4. Register it in `gg.umbra.manager.ModManager.init()`:

```java
ModRegistrationBuilder.create().setModule(new MyModule()).registerWith(this);
```

## Disclaimer

Minecraft cheating software. Use at your own risk: possible account bans and violation of the Mojang EULA. Recommended for private servers, singleplayer worlds or isolated environments only, and with secondary accounts.
