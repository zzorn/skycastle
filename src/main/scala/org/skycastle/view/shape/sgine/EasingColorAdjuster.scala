package org.skycastle.view.shape.sgine

import org.sgine.core.Color
import org.sgine.core.mutable.{Color => MutableColor}
import org.sgine.property.adjust.PropertyAdjuster
import org.sgine.easing.Easing
import scala.math._


/**
 * Adjuster for color values, using the specified easing function.
 */
class EasingColorAdjuster (easing: Easing.EasingFunction, multiplier: Double, dynamic: Boolean = true) extends PropertyAdjuster[Color] {
	private var target: Color = _
	private var start: Color = _
	private var timeToTarget: Double = _
	private var timeElapsed: Double = _

	def apply(current: Color, target: Color, elapsed: Double): Color = {
		if (this.target != target) {		// Target changed
			this.target = target
			start = current
			timeElapsed = 0.0
			if (dynamic) {
				timeToTarget = multiplier
			} else {
				timeToTarget = (multiplier * difference(start, target))
			}
		} else {
			timeElapsed += elapsed
		}

		if (timeElapsed >= timeToTarget) {
			target
		} else {
      val r = easing(timeElapsed, start.red, target.red - start.red, timeToTarget)
      val g = easing(timeElapsed, start.green, target.green- start.green, timeToTarget)
      val b = easing(timeElapsed, start.blue, target.blue - start.blue, timeToTarget)
      val a = easing(timeElapsed, start.alpha, target.alpha - start.alpha, timeToTarget)

      Color(r,g,b,a)
		}
	}

  private def difference(a: Color, b: Color): Double = {
    ( abs(a.red - b.red) +
      abs(a.green - b.green) +
      abs(a.blue - b.blue) +
      abs(a.alpha - b.alpha) ) / 4.0
  }
}