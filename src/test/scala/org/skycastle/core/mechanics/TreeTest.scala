package org.skycastle.core.mechanics

import ability.AbilityUsage
import ability.invasive.Cut
import items.Tree
import org.skycastle.core.entity.Entity
import org.skycastle.core.platform.SkycastleContext
import org.skycastle.core.platform.persistence.memory.InMemoryPersistenceService

import org.scalatest.matchers.ShouldMatchers
import org.scalatest.{AbstractSuite, FlatSpec}

/**
 * 
 */
// TODO: Shows weird error about withFixture being undefined, change back to class when fixed
trait TreeTest extends FlatSpec with ShouldMatchers {

  "A tree" should "be cuttable" in {
    val persistence = new InMemoryPersistenceService()
    SkycastleContext.persistenceService = persistence
    persistence.numberOfObjects should equal (0)

    val tree = Entity(new Tree())
    persistence.numberOfObjects should equal (1)

    val cuttingProject = tree.facet[Tree].get.cut

    cuttingProject.completed should be (false)
    cuttingProject.progress should be (0.0)

    while (!cuttingProject.workNeeded.isEmpty) {
      cuttingProject.addWork(AbilityUsage(Cut, 50.0, 0.05, 0.2))
    }

    cuttingProject.progress should be (1.0)
    cuttingProject.completed should be (true)
    persistence.numberOfObjects should equal (2)
  }


}