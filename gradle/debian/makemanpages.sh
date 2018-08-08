#!/usr/bin/env bash
set -e

PROJECTPATH=$(readlink -f "$(cd $(dirname $0) ; pwd -P)/../..")
MAKEMANPATH=$PROJECTPATH/gradle/man/makeman.pl

[ -f $MAKEMANPATH ] || (echo -e "ERROR: Cannot find 'makeman.pl' script.\nExpected at: $MAKEMANPATH." && exit 2)

mkdir -p $PROJECTPATH/build/debian/translation-recorder/debian/man

echo "Writing man page index to build/debian/translation-recorder/debian/translation-recorder.manpages ..."
cd $PROJECTPATH/build/debian/translation-recorder
ls debian/man/*.1 > $PROJECTPATH/build/debian/translation-recorder/debian/translation-recorder.manpages
