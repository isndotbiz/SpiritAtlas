#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "$0")/.." && pwd)"
cd "$ROOT_DIR"

echo "Bootstrapping SpiritAtlas..."

if [ ! -f local.properties ]; then
  cat > local.properties <<'EOF'
# API keys and endpoints (do not commit real keys)
openrouter.api.key=
ollama.base.url=
EOF
  echo "Created local.properties (empty)."
fi

chmod +x gradlew || true

./gradlew :app:assembleDebug

echo "Bootstrap complete."


