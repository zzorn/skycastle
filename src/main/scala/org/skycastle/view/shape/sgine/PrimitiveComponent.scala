package org.skycastle.view.shape.sgine

import org.sgine.ui.ext.AdvancedComponent
import org.sgine.render.primitive.Primitive
import org.sgine.event.{Event, ProcessingMode, EventHandler}
import org.sgine.render.Image
import org.sgine.property.AdvancedProperty

/**
 * Common trait for primitive 3D shapes.
 */
trait PrimitiveComponent extends AdvancedComponent {

  private var primitive: Primitive = null


  /**
   * Create the primitive using current property values.
   */
  protected def createPrimitive(): Primitive

  /**
   * Forces a re-creation of the primitive the next time it is to be drawn.
   * Use e.g. if you change properies that affect the shape of the primitive.
   */
  protected def invalidatePrimitive() {
    primitive = null;
  }

  /**
   * Convenience event handler that can be added as a listener to a property that should cause the primitive to be updated.
   */
  protected val invalidationHandler: EventHandler = EventHandler((e:Event) => invalidatePrimitive, ProcessingMode.Blocking)

  protected def drawComponent() {
    if (primitive == null) primitive = createPrimitive()

    // Draw primitive
    primitive()
  }

}