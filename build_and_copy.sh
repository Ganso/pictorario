#!/bin/bash

# Compila la aplicación y, salvo que se pida un dry run, la instala en un
# emulador: usa el que ya esté corriendo o arranca uno si hay alguno definido.

set -euo pipefail

DRY_RUN=false
HEADLESS=false
AVD_PREFERIDO="pictorario_test"

uso() {
    cat <<'FIN'
Uso: ./build_and_copy.sh [opciones]

Compila el APK de depuración y el AAB de publicación, y los copia a la raíz
del proyecto con la fecha en el nombre. Después instala el APK en un emulador,
arrancando uno si hiciera falta.

Opciones:
  -n, --dry-run    Sólo compilar. No toca ningún emulador ni instala nada.
      --headless   Si hay que arrancar el emulador, hacerlo sin ventana.
      --avd NOMBRE Usar ese AVD en lugar de "pictorario_test".
  -h, --help       Mostrar esta ayuda.
FIN
}

while [ $# -gt 0 ]; do
    case "$1" in
        -n|--dry-run) DRY_RUN=true ;;
        --headless)   HEADLESS=true ;;
        --avd)        AVD_PREFERIDO="${2:-}"; shift ;;
        -h|--help)    uso; exit 0 ;;
        *) echo "Opción desconocida: $1" >&2; uso >&2; exit 2 ;;
    esac
    shift
done

# --- Compilación -------------------------------------------------------------

TIMESTAMP=$(date +"%Y-%m-%d_%H-%M-%S")

echo "Compilando APK debug..."
./gradlew assembleDebug

APK_NAME="pictorario_${TIMESTAMP}.apk"
cp app/build/outputs/apk/debug/app-debug.apk "./${APK_NAME}"
echo "APK debug guardado como: ${APK_NAME}"

echo "Generando bundle release (AAB)..."
./gradlew bundleRelease

AAB_NAME="pictorario_${TIMESTAMP}.aab"
cp app/build/outputs/bundle/release/app-release.aab "./${AAB_NAME}"
echo "Bundle release guardado como: ${AAB_NAME}"

echo ""
echo "Ficheros generados:"
echo "  - ${APK_NAME}  (debug, para pruebas)"
echo "  - ${AAB_NAME}  (release, para Google Play)"

if [ "$DRY_RUN" = true ]; then
    echo ""
    echo "Dry run: no se toca ningún emulador."
    exit 0
fi

# --- Emulador ----------------------------------------------------------------

# Un fallo a partir de aquí no debe invalidar una compilación correcta.
set +e

localizar_sdk() {
    for ruta in "${ANDROID_HOME:-}" "${ANDROID_SDK_ROOT:-}" "$HOME/Android/Sdk"; do
        [ -n "$ruta" ] && [ -d "$ruta" ] && { echo "$ruta"; return 0; }
    done
    return 1
}

SDK=$(localizar_sdk)
if [ -z "$SDK" ]; then
    echo ""
    echo "No se ha encontrado el SDK de Android. Se omite la instalación."
    echo "Define ANDROID_HOME o instala el SDK en ~/Android/Sdk."
    exit 0
fi

ADB="$SDK/platform-tools/adb"
EMULATOR="$SDK/emulator/emulator"

if [ ! -x "$ADB" ]; then
    echo ""
    echo "No se ha encontrado adb en $ADB. Se omite la instalación."
    exit 0
fi

hay_dispositivo() {
    "$ADB" devices | awk 'NR>1 && $2=="device" {encontrado=1} END {exit !encontrado}'
}

esperar_arranque() {
    local limite=$((SECONDS + 300))
    while [ $SECONDS -lt $limite ]; do
        if [ "$("$ADB" shell getprop sys.boot_completed 2>/dev/null | tr -d '\r')" = "1" ]; then
            return 0
        fi
        sleep 3
    done
    return 1
}

echo ""
if hay_dispositivo; then
    echo "Hay un dispositivo o emulador conectado; se usa ese."
else
    if [ ! -x "$EMULATOR" ]; then
        echo "No hay ningún dispositivo conectado y no está instalado el emulador."
        echo "Instálalo con: sdkmanager emulator"
        exit 0
    fi

    AVDS=$("$EMULATOR" -list-avds 2>/dev/null)
    if [ -z "$AVDS" ]; then
        echo "No hay ningún dispositivo conectado ni AVD definido."
        echo "Crea uno con: avdmanager create avd -n pictorario_test -k 'system-images;android-36;google_apis;x86_64'"
        exit 0
    fi

    # El AVD preferido si existe; si no, el primero de la lista.
    if echo "$AVDS" | grep -qx "$AVD_PREFERIDO"; then
        AVD="$AVD_PREFERIDO"
    else
        AVD=$(echo "$AVDS" | head -1)
        echo "No existe el AVD '$AVD_PREFERIDO'; se usa '$AVD'."
    fi

    OPCIONES=(-avd "$AVD" -no-audio -no-boot-anim -no-snapshot)
    [ "$HEADLESS" = true ] && OPCIONES+=(-no-window -gpu swiftshader_indirect)

    echo "Arrancando el emulador '$AVD'..."
    nohup "$EMULATOR" "${OPCIONES[@]}" >/tmp/pictorario-emulador.log 2>&1 &

    if ! esperar_arranque; then
        echo "El emulador no ha terminado de arrancar a tiempo."
        echo "Revisa /tmp/pictorario-emulador.log"
        exit 0
    fi
    echo "Emulador listo."
fi

# El APK de depuración lleva sufijo .debug para poder convivir con la versión
# publicada; hay que arrancarlo por su propio nombre de paquete.
PAQUETE="javi.prieto.pictorario.debug"

echo "Instalando ${APK_NAME}..."
if ! "$ADB" install -r "./${APK_NAME}"; then
    echo "No se ha podido instalar el APK."
    exit 0
fi

"$ADB" shell monkey -p "$PAQUETE" -c android.intent.category.LAUNCHER 1 >/dev/null 2>&1

echo ""
echo "¡Listo! Pictorario instalado y en marcha."
