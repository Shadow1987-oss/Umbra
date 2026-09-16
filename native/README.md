# Umbra — injection bundle

This folder contains the ready-to-use Umbra injection bundle for Windows x64:

- `UmbraNative.dll` — the native JNI/JVMTI bridge with the Java payload embedded
- `UmbraInjector.exe` — the injector

## Supported Minecraft instances

- Forge / Vanilla 1.7.10, 1.8.9, 1.12.2, 1.21.11 and 26.2
- Fabric (1.21.11 and 26.2, Fabric Loader 0.19.3)
- Forge-enabled Lunar Client

Requires a 64-bit JVM. Minecraft 1.16.5 support is incomplete.

## How to inject

Start a supported Minecraft instance and join a world, then:

- **Auto (recommended):** double-click `UmbraInjector.exe`. It detects the largest
  visible `java.exe` / `javaw.exe` window (Minecraft) and injects automatically.
- **Manual picker:** run `UmbraInjector.exe --select` to list the visible Java game
  windows (refreshing every 750 ms). Select one with Up/Down and press Enter to
  inject; press Esc to quit.
- **Script / PID** (for scripts):

  ```
  UmbraInjector.exe <minecraft-pid> UmbraNative.dll
  ```

  `inyectar.bat` (next to the project's `build` folder) does the same automatically.

Once injected, the DLL waits for the JVM and the Minecraft client thread, extracts
the embedded payload, loads it through the game's class loader and starts it
automatically. No second command is required.

Open the menu with the bound key (default **RSHIFT**).

If something fails, check `umbra-native.log` beside the DLL.

## Building the bundle from source

From the project root (Gradle 8.8 + JDK 17 + Visual Studio 2022 C++ + CMake):

```powershell
.\gradlew.bat prepareInjectionBundle
```

Outputs are written to `build/injection`:

- `UmbraNative.dll`
- `UmbraInjector.exe`

## Disclaimer

Minecraft cheating software. Use at your own risk: possible account bans and
violation of the Mojang EULA. Recommended for private servers, singleplayer
worlds or isolated environments only, and with secondary accounts.
