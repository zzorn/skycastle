package org.skycastle.server

import _root_.java.io.Serializable

/**
 * Internal server message from one entity to another.
 *
 * TODO: Do we need these, or can we just call methods?  Well, events would allow async handling.
 * TODO: Can we use Messages both for internal and external communication?  Well, internal can use actual specialized classes, and also it reduces security risk.
 */
trait Event extends Serializable {
  
}

