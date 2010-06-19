package org.skycastle.core.mechanics.items

import org.skycastle.core.mechanics.project.{Project}
import org.skycastle.core.mechanics.ability.AbilityRequest
import org.skycastle.core.mechanics.ability.invasive.Cut

/**
 * 
 */
class Tree {

  private val axeCut = AbilityRequest(Cut, 20.0, 0.1, 0.1)
  // 20J = the energy of an impact of a 2kg axe swung over 1 m and impacting 5 cm into the tree
  // Accuracy and tool size are both in the 10cm magnitude

  private lazy val cuttingWork: Project = new Project(Map((axeCut -> 30))) // 30 axe cuts to fell the tree

  /**
   * Starts a cutting work project for this tree, or returns the existing one if it already exists.
   */
  def cut(): Project = cuttingWork


}

