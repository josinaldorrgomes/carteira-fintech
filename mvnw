#!/bin/sh
# ----------------------------------------------------------------------------
# Apache Maven Wrapper startup script (distributionType=only-script).
# Baixa o Maven automaticamente na primeira execucao. Nao precisa de .jar.
# ----------------------------------------------------------------------------
set -euf
[ "${MVNW_VERBOSE:-}" != debug ] || set -x

die() { printf '%s\n' "mvnw: $*" >&2; exit 1; }

# Localiza este script e o diretorio do projeto
SCRIPT_DIR=$(CDPATH= cd -- "$(dirname -- "$0")" && pwd -P)
PROPERTIES="$SCRIPT_DIR/.mvn/wrapper/maven-wrapper.properties"
[ -r "$PROPERTIES" ] || die "nao encontrei $PROPERTIES"

# Le distributionUrl do properties
distributionUrl=$(sed -n 's/^distributionUrl=//p' "$PROPERTIES" | tr -d '\r')
[ -n "$distributionUrl" ] || die "distributionUrl ausente em maven-wrapper.properties"
case "$distributionUrl" in
  *-bin.zip|*maven-mvnd-*.zip) ;;
  *) die "distributionUrl precisa terminar em -bin.zip" ;;
esac

distributionUrlName="${distributionUrl##*/}"
distributionName="${distributionUrlName%-bin.zip}"
MAVEN_HOME="${MAVEN_USER_HOME:-$HOME/.m2}/wrapper/dists/$distributionName"

# Se ja esta instalado, apenas executa
if [ -d "$MAVEN_HOME" ] && [ -x "$MAVEN_HOME/bin/mvn" ]; then
  exec "$MAVEN_HOME/bin/mvn" "$@"
fi

printf '%s\n' "Baixando o Apache Maven de: $distributionUrl" >&2
TMP_DOWNLOAD_DIR=$(mktemp -d) || die "nao consegui criar diretorio temporario"
trap 'rm -rf -- "$TMP_DOWNLOAD_DIR"' EXIT

ZIP_FILE="$TMP_DOWNLOAD_DIR/maven.zip"
if command -v curl >/dev/null 2>&1; then
  curl -fsSL -o "$ZIP_FILE" "$distributionUrl" || die "falha no download (curl)"
elif command -v wget >/dev/null 2>&1; then
  wget -q -O "$ZIP_FILE" "$distributionUrl" || die "falha no download (wget)"
else
  die "preciso de curl ou wget para baixar o Maven"
fi

command -v unzip >/dev/null 2>&1 || die "preciso do 'unzip' para extrair o Maven"
unzip -q "$ZIP_FILE" -d "$TMP_DOWNLOAD_DIR" || die "falha ao extrair o Maven"

mkdir -p "$(dirname -- "$MAVEN_HOME")"
rm -rf -- "$MAVEN_HOME"
mv -- "$TMP_DOWNLOAD_DIR/$distributionName" "$MAVEN_HOME" || die "falha ao instalar o Maven"

exec "$MAVEN_HOME/bin/mvn" "$@"
