#!/bin/bash

git clone https://github.com/susestudio/studio-help.git
cp studio-help/800_api/920_v2/*.xml ../src/test/resources/com/suse/studio/client/test/examples/
rm -Rf studio-help