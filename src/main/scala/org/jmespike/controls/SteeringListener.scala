package org.jmespike.controls

import com.jme3.input.controls.ActionListener
import java.lang.String
import org.jmespike.input.InputNames
import java.util.HashSet
import collection.JavaConversions._
import simplex3d.math.float.functions._
import simplex3d.math.float._

/**
 * 
 */
class SteeringListener(steerable: Steerable) extends ActionListener {

  private val activeInputs = new HashSet[String]()

  def onAction(name: String, isPressed: Boolean, tpf: Float) {
    if (isPressed) activeInputs.add(name)
    else activeInputs.remove(name)

    steerable.steering := Vec3.Zero
    activeInputs foreach { input =>
      input match {
        case InputNames.Forward => steerable.steering.x += 1f
        case InputNames.Back => steerable.steering.x -= 1f
        case InputNames.Left => steerable.steering.z += 1f
        case InputNames.Right => steerable.steering.z -= 1f
        case InputNames.Up => steerable.steering.y += 1f
        case InputNames.Down => steerable.steering.y -= 1f
      }
    }

  }

}