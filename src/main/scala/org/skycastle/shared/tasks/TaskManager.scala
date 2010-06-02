package org.skycastle.shared.tasks

import _root_.com.sun.sgs.app.Task
import _root_.org.skycastle.shared.entity.Facet
import _root_.org.skycastle.shared.model.Data
import _root_.org.skycastle.shared.Time

/**
 * 
 */
trait TaskManager {


  def scheduleCallback(time: Time, callback: Task)

}
