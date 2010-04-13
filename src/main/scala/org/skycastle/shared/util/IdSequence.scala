package org.skycastle.server.util

/**
 * Provides a sequence of long id:s, starting from 1.
 */
final class IdSequence {

  private var id: Long = 0

  def nextId(): Long = {
    id += 1
    id
  }
}

