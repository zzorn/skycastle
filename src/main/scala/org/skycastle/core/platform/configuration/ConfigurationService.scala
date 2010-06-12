package org.skycastle.core.platform.configuration

import org.skycastle.core.data.Data

/**
 * A service that provides configuration.
 */
trait ConfigurationService {

  private lazy val conf: Data = loadConfiguration()

  def get: Data = conf

  protected def loadConfiguration(): Data

}