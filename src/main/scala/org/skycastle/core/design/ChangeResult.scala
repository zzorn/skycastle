package org.skycastle.core.design

/**
 * 
 */
trait ChangeResult {
  val isSuccess: Boolean
  def onSuccess(block: => Unit) = if(isSuccess) block
  def onFailure(block: => Unit) = if(!isSuccess) block
}

case object ChangeSuccess extends ChangeResult {
  override val isSuccess = true
}

trait ChangeFailure extends ChangeResult {
  override val isSuccess = false

  def message: String = this.getClass.getSimpleName
}

case class ChangeNotPossible(override val message: String) extends ChangeFailure
