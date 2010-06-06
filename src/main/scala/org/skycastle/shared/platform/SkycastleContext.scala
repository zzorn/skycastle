package org.skycastle.shared.platform

import persistence.memory.InMemoryPlatformService
import scheduler.local.LocalScheduler
import scheduler.SchedulerService
import time.{TimeService, RealTimeService}
import persistence.PersistenceService

/**
 * A singleton for accessing various services.
 *
 * Set up with local services by default.
 */
object SkycastleContext {
  var platformServices: PersistenceService = InMemoryPlatformService
  var schedulerService: SchedulerService = new LocalScheduler()
  var timeService : TimeService = RealTimeService
}

