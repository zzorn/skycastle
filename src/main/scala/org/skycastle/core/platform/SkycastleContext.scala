package org.skycastle.core.platform

import _root_.org.skycastle.core.entity.{FacetService, FacetServiceImpl}
import configuration.ConfigurationService
import configuration.directory.DirectoryConfigurationService
import persistence.memory.InMemoryPlatformService
import scheduler.local.LocalScheduler
import scheduler.SchedulerService
import time.{TimeService, RealTimeService}
import persistence.PersistenceService
import org.skycastle.core.data.Data

/**
 * A singleton for accessing various services.
 *
 * Set up with local services by default.
 */
object SkycastleContext {
  var platformServices: PersistenceService = InMemoryPlatformService
  var schedulerService: SchedulerService = new LocalScheduler()
  var timeService : TimeService = RealTimeService
  var configuration: ConfigurationService = new DirectoryConfigurationService()
  var facetService: FacetService = new FacetServiceImpl()
}

