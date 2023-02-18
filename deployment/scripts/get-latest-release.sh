#!/bin/bash
parent_path=$( cd "$(dirname "${BASH_SOURCE[0]}")" ; pwd -P )
cd "$parent_path"

mkdir -p ../build
curl -L $(curl -s https://api.github.com/repos/rdbatch02/atis/releases/latest | grep browser_download_url | cut -d\" -f4 | egrep 'jar$') --output ../build/atis.jar