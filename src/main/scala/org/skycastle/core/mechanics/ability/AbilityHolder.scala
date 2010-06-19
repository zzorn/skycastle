package org.skycastle.core.mechanics.ability

import org.skycastle.core.entity.Facet
import org.skycastle.core.mechanics.project.AbilityTask

/**
 * Someone or somethign that has abilities and can use them.
 */
class AbilityHolder extends Facet {

  def canDoAbility(request: AbilityRequest): Boolean

  /**
   * Requests some energy to do the ability and does it if it can.
   * Returns None if the ability could not be done, otherwise a response about how well it was done.
   */
  // TODO: Some kind of multi-use interface?  So that an ability can be repeated for some number of times,
  // or until there is no more energy, or indefinitely?
  // Needs listener interface to tell ability requester about the progress.
  def doWork(request: AbilityRequest): Option[AbilityUsage]

  /**
   * Work on the specified task until there is another work request or a request to stop workin.
   */
  def workOn(task: AbilityTask)

  def stopWorking()

}