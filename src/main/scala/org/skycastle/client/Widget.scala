package org.skycastle.client

import _root_.org.skycastle.server.core.MessageHandler

/**
 * An UI component or some internal component on the client side, that can send and receive messages from
 * some activity on the server that the user is participating in.
 *
 * Can be either a visual component, or a key binding?, or some logic component.
 */
trait Widget extends MessageHandler {
  
}
