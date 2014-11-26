# get current parent directory
# resolve links - $0 may be a softlink
os400=false
darwin=false
case "`uname`" in
CYGWIN*) cygwin=true;;
OS400*) os400=true;;
Darwin*) darwin=true;;
esac

# resolve links - $0 may be a softlink
PRG="$0"

while [ -h "$PRG" ] ; do
  ls=`ls -ld "$PRG"`
  link=`expr "$ls" : '.*-> \(.*\)$'`
  if expr "$link" : '.*/.*' > /dev/null; then
    PRG="$link"
  else
    PRG=`dirname "$PRG"`/"$link"
  fi
done

PRGDIR=`dirname "$PRG"`

[ -z "$CURRENT_DIR" ] && CURRENT_DIR=`cd "$PRGDIR" ; pwd`

java -Djava.ext.dirs="${CURRENT_DIR}/WebContent/WEB-INF/lib" -cp "${CURRENT_DIR}/WebContent/WEB-INF/classes" com.baidu.rigel.platform.dao.tools.SqlMappingExporter