package org.skycastle.util

import java.util.Date
import java.io.{File, StringWriter, PrintWriter}
import collection.JavaConversions._
import org.slf4j.{Logger, LoggerFactory}

/**
 * A mixin that provides various logging methods that delegate to the SLF4J framework.
 */
trait Logging {
/*
  def setupConsoleLogging(path: String = Logger.GLOBAL_LOGGER_NAME) {
    val consoleHandler = new ConsoleHandler()

    // Override idiotic Java default of not logging any level smaller than info to the console, even if it is specified in a logger.
    // see http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4462908
    consoleHandler.setLevel(Level.ALL)

    Logger.getLogger(path).addHandler(consoleHandler)
    setupFormatter(path)
  }

  def setupFileLogging(logFileNamePattern: String, path: String = Logger.GLOBAL_LOGGER_NAME) {
    Logger.getLogger(path).addHandler(new FileHandler(logFileNamePattern))
    setupFormatter(path)
  }

  def setupFormatter(path: String = Logger.GLOBAL_LOGGER_NAME) {
    val logger = Logger.getLogger(path)
    logger.getHandlers foreach {h => h.setFormatter(new SingleLineFormatter)}
  }
*/
  def loggingPath = getClass

  val log: Logger = LoggerFactory.getLogger(loggingPath)

  /*
  def log(level: Level, message: => String, exception: => Throwable)  {
    if (log.isLoggable(level)) log.log(level, message, exception)
  }
  */
/*
  final def log( level : Level, message : => String ): Unit = { log( level, message, null ) }
  */

  final def logError( message : => String ) { log.error(message) }
  final def logWarn(  message : => String ) { log.warn(message)  }
  final def logInfo(  message : => String ) { log.info(message)  }
  final def logDebug( message : => String ) { log.debug(message) }
  final def logTrace( message : => String ) { log.trace(message) }

  final def logError( message : => String, exception : => Throwable ) { log.error(message, exception) }
  final def logWarn(  message : => String, exception : => Throwable ) { log.warn(message, exception)  }
  final def logInfo(  message : => String, exception : => Throwable ) { log.info(message, exception)  }
  final def logDebug( message : => String, exception : => Throwable ) { log.debug(message, exception) }
  final def logTrace( message : => String, exception : => Throwable ) { log.trace(message, exception) }

}
/*
final class SingleLineFormatter extends Formatter {

  private val LINE_SEPARATOR = System.getProperty("line.separator")

  override def format(record: LogRecord): String = {

    def simpleName(fullName: String): String = {
      val dotPos = fullName.lastIndexOf(".")
      fullName.substring(dotPos+1)
    }

    val sb = new StringBuilder()

    sb.append(new Date(record.getMillis).formatted("%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS"))
        .append(": ")
        .append(record.getLevel.getName)
        .append(": ")
        .append(simpleName(record.getSourceClassName))
        .append(".")
        .append(record.getSourceMethodName)
        .append(": ")
        .append(formatMessage(record))
        .append(LINE_SEPARATOR)

    // Append stack trace
    if (record.getThrown != null) {
      try {
        val sw = new StringWriter()
        val pw = new PrintWriter(sw)
        record.getThrown.printStackTrace(pw)
        pw.close()
        sb.append(sw.toString)
      } catch {
        case e:Exception => // Ignore
      }
    }

    sb.toString()
  }
}
*/