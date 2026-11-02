# Jump Until Hell

Java Swing game. Original package: Project3_6713225.

## Play on Windows

Extract the entire JumpUntilHell-Windows.zip archive, then open JumpUntilHell/JumpUntilHell.exe. Keep the app, runtime and other folders beside the executable. Java is included; no separate Java installation is required.

Save data is stored in %LOCALAPPDATA%/JumpUntilHell.

## Build

Install JDK 21 (including jpackage), then run:

```powershell
powershell -ExecutionPolicy Bypass -File ./build.ps1
```

Use a clean dist directory before rebuilding. Output: dist/JumpUntilHell/JumpUntilHell.exe and dist/JumpUntilHell-Windows.zip.

The export adds portable resource lookup and writable per-user save files. Original source files in C:/paradigm were preserved.
