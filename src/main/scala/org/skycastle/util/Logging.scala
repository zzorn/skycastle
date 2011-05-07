package org.skycastle.util

import java.util.Date
import java.util.logging._
import java.io.{File, StringWriter, PrintWriter}
import collection.JavaConversions._
/**
 * A mixin that provides various logging methods that use the standard java logger to log the message.
 */
trait Logging {

  def setupConsoleLogging(path: String = Logger.GLOBAL_LOGGER_NAME) {
    Logger.getLogger(path).addHandler(new ConsoleHandler())
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

  def loggingPath = getClass.getName

  def log: Logger = Logger.getLogger(loggingPath)

  def log(level: Level, message: => String, exception: => Throwable)  {
    if (log.isLoggable(level)) log.log(level, message, exception)
  }

  final def log( level : Level, message : => String ): Unit = { log( level, message, null ) }

  final def logError( message : => String ) { log( Level.SEVERE, message ) }
  final def logWarning( message : => String ) { log( Level.WARNING, message ) }
  final def logInfo( message : => String ) { log( Level.INFO, message ) }
  final def logDebug( message : => String ) { log( Level.FINE, message ) }
  final def logTrace( message : => String ) { log( Level.FINER, message ) }

  final def logError( message : => String, exception : => Throwable ) { log( Level.SEVERE, message, exception ) }
  final def logWarning( message : => String, exception : => Throwable ) { log( Level.WARNING, message, exception ) }
  final def logInfo( message : => String, exception : => Throwable ) { log( Level.INFO, message, exception ) }
  final def logDebug( message : => String, exception : => Throwable ) { log( Level.FINE, message, exception ) }
  final def logTrace( message : => String , exception : => Throwable ) { log( Level.FINER, message, exception ) }

}

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
