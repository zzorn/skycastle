package org.skycastle.core.mechanics.project

import org.skycastle.core.mechanics.ability.{AbilityRequest, AbilityUsage}
import org.skycastle.core.space.{Item}
import org.skycastle.core.platform.persistence.Ref

/**
 * Something that can be worked on.
 */
trait AbilityTask {

  /**
   * The item that the work is done to, or None if there isn't any specific target for the work.
   * If specified, the worker needs to be within range of the item to do the work.
   */
  def target: Option[Ref[Item]]

  /**
   * The types of work needed for this work phase / activity / process.
   */
  def workNeeded: Iterable[AbilityRequest]

  def addWork(workDone: AbilityUsage)

  def completed: Boolean

}