package org.skycastle.util

import _root_.java.util.logging.{Logger, Level}

/**
 * A mixin that provides various logging methods that use the standard java logger to log the message.
 */
trait Logging {

  def loggingPath = getClass().getName

  def log(level: Level, message: => String, exception: => Throwable)  {
    Logger.getLogger(loggingPath).log(level, message, exception)
  }
  final def log( level : Level, message : => String ) : Unit = { log( level, message, null ) }

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

