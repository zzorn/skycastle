package org.jmespike.utils

/**
 * Provides user readable information for some application concept¸ property, action, or entity.
 *
 * The description or icon path may be null.
 */
case class Desc(name: String, description: String = null, iconPath: String = null)
