package org.skycastle.client.scene

import org.skycastle.core.entity.Facet
import org.skycastle.core.messaging.MessageReceiver
import org.skycastle.core.shape.Shape

/**
 * 3D scene
 */
class Scene extends Facet with MessageReceiver {

  def add(shape: Shape)


}