package org.skycastle.shared.persistence

import _root_.org.skycastle.shared.tasks.TaskManager

/**
 * 
 */

object SkycastleContext {
  var persistenceManager: PersistenceManager = InMemoryPersistenceManager
  var taskManager: TaskManager
}

