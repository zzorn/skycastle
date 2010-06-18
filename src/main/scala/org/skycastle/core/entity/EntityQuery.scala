package org.skycastle.core.entity

/**
 * 
 */
trait EntityQuery {

  def describe: String

  def matches(entity: Entity): Boolean

}