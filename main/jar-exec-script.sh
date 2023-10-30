#!/bin/sh

#region preamble

case "$-" in
	(*'i'*)
		\command printf 'JAR file was called interactively\n' >&2
		return 1
		;;
esac

set -o errexit
set -o nounset

# enabling POSIX-compliant behavior for GNU programs
export POSIXLY_CORRECT=yes POSIX_ME_HARDER=yes

if [ "${0#/}" = "$0" ]; then
	argv0="$0"
else
	argv0="$(basename -- "$0" && printf x)"
	argv0="${argv0%"$(printf '\nx')"}"
fi
readonly argv0

#endregion

#region determining Java command

java_command=''

if [ -n "${JAVA_HOME-}" ]; then
	# IBM's JDK on AIX uses strange locations for the executables
	java_command="$JAVA_HOME/jre/sh/java"

	if [ ! -x "$java_command" ]; then
		java_command="$JAVA_HOME/bin/java"
	fi

	if [ ! -x "$java_command" ]; then
		# shellcheck disable=2016
		printf '%s: the environment variable `JAVA_HOME` is set to an invalid directory (%s)\n' "$argv0" "$JAVA_HOME" >&2
		exit 1
	fi
fi

if [ -z "$java_command" ] && command -v java > '/dev/null'; then
	java_command='java'
fi

if [ -z "$java_command" ]; then
	# shellcheck disable=2016
	printf '%s: could not determine Java command (neither the environment variable `JAVA_HOME` is set nor the command `java` was found)\n' \
	       "$argv0" >&2
	exit 1
fi

readonly java_command

#endregion

#region check Java version

min_java_version=17
readonly min_java_version


detected_java_version="$("$java_command" -version 2>&1 | head -n1 | cut -d\" -f2)"
detected_java_version="${detected_java_version#"1."}"
detected_java_version="${detected_java_version%".${detected_java_version#*'.'}"}"

if ! { printf '%s' "$detected_java_version" | head -n1 | grep -Eq '^[1-9][0-9]*$'; }; then
	detected_java_version=''
fi

readonly detected_java_version


if [ -z "$detected_java_version" ]; then
	printf 'Could not detect Java runtime version\n' >&2
elif [ "$detected_java_version" -lt $min_java_version ]; then
	printf 'Detected Java runtime version (%s) is lower than required Java version (%s)\n' \
	       "$detected_java_version" "$min_java_version" >&2

	if [ -t 2 ]; then
		printf 'Continue? [y/N] ' >&2

		read -r ans

		case "$ans" in
			(['yY']*)
				# nothing
				;;
			(*)
				printf 'Aborted.\n' >&2
				exit 1
				;;
		esac

		unset -v ans
	fi
fi

#endregion

# shellcheck disable=2086
exec "$java_command" ${JAVA_OPTS-} -jar "$0" "$@"
