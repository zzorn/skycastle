package org.skycastle.core.mechanics.project

import org.skycastle.core.entity.{EntityQuery, Entity}
import org.skycastle.core.mechanics.ability.{AbilityRequest, AbilityUsage}

/**
 * Describes some planned work, and its progress.
 * The work needs some input of work units, resources and catalysts / tools.
 * It can also specify environment conditions needed (e.g. dry surroundings, no vacum, etc :P)
 *
 * A work item may need several phases of different kinds of work
 * (e.g. forging something will first need heating, then hammering, then cooling)
 */
// TODO: Add parameters for what will be done when the work completes, and some kind of progress listener
// TODO: Extract trait and implement basic implementation?
class Project(work: Map[AbilityRequest, Int] = Map(),
              components: Map[EntityQuery, Int] = Map(),
              catalysts: Map[EntityQuery, Int] = Map(),
              onCompleted: Unit => Unit = {},
              onCanceled: Unit => Unit = {}) extends AbilityTask {



  /**
   * The amount of work still needed for this work phase / activity / process.
   */
  def completed = false

  def target = None

  def workNeeded: Iterable[AbilityRequest] = work.keys

  /**
   * The resources still needed.
   * Basically an entity query defining some shape and material properties, or functionality, or machine node, etc, and the number of them.
   */
  def componentsNeeded: Map[EntityQuery, Int] = components

  /**
   * Items needed during the work that get freed up after the work completes.
   */
  def requiredCatalysts: Map[EntityQuery, Int] = catalysts

/* TODO: Add later if needed
  def requiredEnvironmentConditions: List[EnvironmentCondition]
*/

  def addCatalyst(entity: Entity)
  def addComponent(entity: Entity)
  def addWork(workType: AbilityUsage)
  def removeProduct(): Entity

  // TODO: The project acts like a container with the catalysts, provided components, and created
  // products and waste are visible, and each have some maximum container size.  They need to be removed/added when they
  // fill up / go empty
  // -> a kind of one way container that only accepts or produces items 
  


  /**
   * Overall progress of this work item, from 0 (nothing) to 1 (completed)
   */
  def progress: Double


  /**
   * If work is not ready, cancels the work and frees up any free resources and catalysts.
   * If the work is ready, frees up any free resources and catalysts.
   */
  def stop()

}