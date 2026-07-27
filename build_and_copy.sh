#!/bin/bash

# Abortar el script si algún comando falla
set -e

# Obtener fecha y hora actual en formato YYYY-MM-DD_HH-MM-SS
TIMESTAMP=$(date +"%Y-%m-%d_%H-%M-%S")

# --- APK debug ---
echo "Compilando APK debug..."
./gradlew assembleDebug

APK_NAME="pictorario_${TIMESTAMP}.apk"
cp app/build/outputs/apk/debug/app-debug.apk "./${APK_NAME}"
echo "APK debug guardado como: ${APK_NAME}"

# --- Bundle release (AAB) ---
echo "Generando bundle release (AAB)..."
./gradlew bundleRelease

AAB_NAME="pictorario_${TIMESTAMP}.aab"
cp app/build/outputs/bundle/release/app-release.aab "./${AAB_NAME}"
echo "Bundle release guardado como: ${AAB_NAME}"

echo ""
echo "¡Listo! Ficheros generados:"
echo "  - ${APK_NAME}  (debug, para pruebas)"
echo "  - ${AAB_NAME}  (release firmado, para Google Play)"
