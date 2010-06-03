package org.skycastle.shared.persistence

import _root_.org.skycastle.shared.time.{RealTimeService, TimeService}

/**
 * A singleton for accessing various services.
 */
object SkycastleContext {
  var platformServices: PlatformServices = InMemoryPlatformServices
  var timeService : TimeService = new RealTimeService()
}

