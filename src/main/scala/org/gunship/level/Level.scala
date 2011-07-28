package org.gunship.level

import org.jmespike.scene.Scene
import org.jmespike.activity.Activity
import org.gunship.entities.Dust
import java.util.Random
import org.jmespike.conf.Conf
import com.jme3.scene.{Spatial, Node}

/**
 * 
 */
class Level extends Activity with Conf {

  val dustAmount = p('dustAmount, 100)
  val dustDistribution = p('dustDistribution, 1000)
  val dustType = p[Dust]('dustType, new Dust())
  val seed = p('seed, 2134)

  protected def createScene: Spatial = {
    val node = new Node()

    val random = new Random(seed())

    val dt: Dust = dustType()
    0 until dustAmount() foreach{i =>
      val dust = dt.createEntity()

      dust.setLocalTranslation(random.nextFloat * dustDistribution(),
                               random.nextFloat * dustDistribution(),
                               random.nextFloat * dustDistribution())

      node.attachChild(dust)
    }

    node
  }

  override protected def onUpdate(timePerFrame: Float) {

    // Move players ship towards next level waypoint

    // Apply player steering

    // Update active entities

    // Spawn new entities depending on level progress

    // Check for level end conditions


  }

  
}
