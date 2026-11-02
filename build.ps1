param()
$ErrorActionPreference = 'Stop'
Set-Location $PSScriptRoot
$jdk = Split-Path (Get-Command jpackage.exe).Source
New-Item -ItemType Directory -Force build/classes, build/input | Out-Null
$sources = Get-ChildItem src/main/java -Filter *.java -Recurse | ForEach-Object FullName
& "$jdk/javac.exe" -encoding UTF-8 --release 17 -d build/classes $sources
if ($LASTEXITCODE -ne 0) { throw 'Java compilation failed' }
& "$jdk/jar.exe" "-J-Djava.io.tmpdir=$PSScriptRoot/build" --create --file build/input/JumpUntilHell.jar --main-class Project3_6713225.MainApplication -C build/classes .
if ($LASTEXITCODE -ne 0) { throw 'JAR creation failed' }
$assets = 'build/input/src/main/java/Project3_6713225'
New-Item -ItemType Directory -Force $assets | Out-Null
Copy-Item src/main/java/Project3_6713225/resources $assets -Recurse -Force
Copy-Item src/main/java/Project3_6713225/*.txt $assets -Force
& "$jdk/jpackage.exe" --type app-image --name JumpUntilHell --input build/input --main-jar JumpUntilHell.jar --temp build/package-temp --dest dist --app-version 1.0 --vendor JumpUntilHell --java-options '-Dfile.encoding=UTF-8'
if ($LASTEXITCODE -ne 0) { throw 'Windows packaging failed' }
Compress-Archive -Path dist/JumpUntilHell -DestinationPath dist/JumpUntilHell-Windows.zip -Force
