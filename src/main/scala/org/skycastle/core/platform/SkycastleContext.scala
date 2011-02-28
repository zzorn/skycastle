package org.skycastle.core.platform

import persistence.memory.InMemoryPersistenceService
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
  var persistenceService: PersistenceService = new InMemoryPersistenceService()
  var schedulerService: SchedulerService = new LocalScheduler()
  var timeService : TimeService = RealTimeService
}

