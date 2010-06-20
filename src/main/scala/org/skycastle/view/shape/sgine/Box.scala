package org.skycastle.view.shape.sgine

import org.sgine.ui.ext.AdvancedComponent
import org.sgine.property.{AdvancedProperty, MutableProperty}
import org.sgine.property.event.PropertyChangeEvent
import org.sgine.event.{ProcessingMode, EventHandler}
import org.sgine.render.primitive.{Cuboid, Primitive, Mesh}
import org.sgine.core.Color
import org.sgine.render.Image

/**
 * A white box with size 100 along each axis.
 */
class Box() extends PrimitiveComponent {

  /**
   * A box with a specified width, height, depth, and optionally color and texture.
   */
  def this(width: Double, height: Double, depth: Double, color: Color = Color.White, texture: Image = null) {
    this()
    this.width := width
    this.height := height
    this.depth := depth
    this.color := color
    this.texture := texture
  }

  /**
   * A box with a specified size, color and texture.
   */
  def this(size: Double, color: Color, texture: Image) {
    this(size, size, size, color, texture)
  }

  /**
   * A box with a specified size and color.
   */
  def this(size: Double, color: Color) {
    this(size, color, null)
  }

  /**
   * A box with a specified size.
   */
  def this(size: Double) {
    this(size, Color.White)
  }

  val width = new AdvancedProperty[Double](100, this)
  val height = new AdvancedProperty[Double](100, this)
  val depth = new AdvancedProperty[Double](100, this)
  val texture = new AdvancedProperty[Image](null, this)

  // TODO: Add event handler parameter to AdvancedProperty constructor
  width.listeners += invalidationHandler
  height.listeners += invalidationHandler
  depth.listeners += invalidationHandler
  texture.listeners += invalidationHandler
  color.listeners += invalidationHandler

  protected def createPrimitive(): Primitive = {
    // TODO: Replace use of Image with Texture
    Cuboid(width(), height(), depth(), color(), texture())
  }

}