#!/usr/bin/env bash

set -o errexit
set -o pipefail
set -o nounset

readonly BASE_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

(docker-compose --file ${BASE_DIR}/docker-compose.yml down)
