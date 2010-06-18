package org.skycastle.client.facets

import _root_.org.skycastle.core.entity.Facet
import _root_.org.skycastle.core.messaging.MessageReceiver
import org.skycastle.core.data.Data

/**
 * 
 */

class Controls  extends Facet with MessageReceiver {
  def onMessage(message: Data) = null
}

