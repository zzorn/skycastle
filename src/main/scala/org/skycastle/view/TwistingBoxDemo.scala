package org.skycastle.view

import org.sgine.property.adjust.{EasingNumericAdjuster}
import org.sgine.core.{Resource, Color}
import org.sgine.render._
import org.sgine.render.{Image => RenderImage}
import shape.sgine.{EasingColorAdjuster, Box}
import scala.math.random
import scala.math.Pi
import org.sgine.work.Updatable
import org.sgine.easing._

object TwistingBoxDemo extends StandardDisplay with Updatable {

  private val animationTime = 2.0;

  private var countdown = 0.0;

  private var box: Box = new Box()

  def setup() {
    // Add box to scene
    scene +=  box

    // Set texture for box
    Resource.addPath("")
    box.texture := RenderImage(TextureManager(Resource("textures/test_crate.png")))

    // Setup adjusters for the box size, rotation, and color
    val adjustTime = animationTime
    val easingMethod = Back.easeInOut _
    box.width.adjuster = new EasingNumericAdjuster(easingMethod, adjustTime)
    box.height.adjuster = new EasingNumericAdjuster(easingMethod, adjustTime)
    box.depth.adjuster = new EasingNumericAdjuster(easingMethod, adjustTime)

    box.rotation.x.adjuster = new EasingNumericAdjuster(easingMethod, adjustTime)
    box.rotation.y.adjuster = new EasingNumericAdjuster(easingMethod, adjustTime)
    box.rotation.z.adjuster = new EasingNumericAdjuster(easingMethod, adjustTime)

    box.color.adjuster = new EasingColorAdjuster(easingMethod, adjustTime)

    // Tell Updatable to start calling update
    initUpdatable()
  }

  // Provided by the Updatable trait, called regularly
  override def update(timeDelta: Double) {
    countdown -= timeDelta
    if (countdown <= 0) {
      countdown = animationTime

      setNewBoxTargets()
    }
  }


  def setNewBoxTargets() {
    // Set new values for box size, angle and color.
    // Because we specified adjusters, the values will be animated from their current value to the new value automatically.
    box.width := random * 500
    box.height := random * 500
    box.depth := random * 500

    box.rotation.x := random * 2 * Pi
    box.rotation.y := random * 2 * Pi
    box.rotation.z := random * 2 * Pi

    box.color := Color(random, random, random, 1)
  }


}