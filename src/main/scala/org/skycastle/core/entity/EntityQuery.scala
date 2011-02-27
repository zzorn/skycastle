package org.skycastle.core.entity

/**
 * 
 */
@Deprecated
trait EntityQuery {

  def describe: String

  def matches(entity: Entity): Boolean

}