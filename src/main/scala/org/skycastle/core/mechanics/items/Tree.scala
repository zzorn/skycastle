package org.skycastle.core.mechanics.items

import org.skycastle.core.mechanics.ability.AbilityRequest
import org.skycastle.core.mechanics.ability.invasive.Cut
import org.skycastle.core.entity.{Facet, Entity}
import org.skycastle.util.{Below, Above}
import org.skycastle.core.mechanics.project.{SimpleProject, Project}

/**
 * 
 */
class Tree extends Facet {

  private val axeCut = AbilityRequest(Cut, Above(10), 0.1, Above(0.1))
  // 10J = the energy of an impact of a 1kg axe swung over 1 m and impacting 5 cm into the tree
  // Accuracy and tool size are both in the 10cm magnitude

  private lazy val cuttingWork: SimpleProject = createCuttingWork()

  /**
   * Starts a cutting work project for this tree, or returns the existing one if it already exists.
   */
  def cut(): SimpleProject = cuttingWork


  private def createCuttingWork(): SimpleProject = new SimpleProject(axeCut, 300.0, 5.0, {
    // Spawn a trunk and a stump (optionally play falling animation for trunk) and replace this entity with them
    val trunk: Entity = Entity() // TODO
    val stump: Entity = Entity() // TODO
    entity.get.replaceWith(stump, trunk)
  })

}

