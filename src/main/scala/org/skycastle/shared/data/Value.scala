package org.skycastle.shared.data

import primitives.{EmptyPath, Path}

/**
 * Represents some data value.
 *
 * The data values may be primitive values or container values that allow  adding,
 * removing and updating the values and listening for value changes.
 */
// TODO: Should the primitive values actually be mutable too?  They could handle the listeners, no need for containers to dabble with that..
trait Value {

  def set(index: Index, value: Value)
  def add(value: Value): Index

  /**
   * The value itself, allowing thigns like DynamicValue to generate the value when requested.
   */
  protected def getValue: Value = this

  protected def getValue(index: Index): Option[Value]
  protected def removeValue(index: Index): Boolean

  def apply(path: Index*): Value = apply(Path(path))
  def get(path: Index*): Option[Value] = get(Path(path))
  def remove(path: Index*): Boolean = remove(Path(path))

  def update(path: Path, value: Value): Value = set(path, value)

  def apply(path: Path): Value = null // TODO
  def get(path: Path): Option[Value] = null // TODO
  def set(path: Path, value: Value) = null // TODO
  def add(path: Path, value: Value) = null // TODO
  def remove(path: Path): Boolean = false // TODO

  // TODO: Methods for managing listeners

  // TODO: Instead of passing the path around and creating subsets of it, jus retrieve it iteratively from here, 
  // using index based methods?  Faster, and we can get descriptive error message with the part of the path
  // that was invalid.

}

