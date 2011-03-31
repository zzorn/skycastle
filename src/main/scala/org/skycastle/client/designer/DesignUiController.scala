package org.skycastle.client.designer

import de.lessvoid.nifty.controls.Controller
import de.lessvoid.nifty.Nifty
import de.lessvoid.nifty.screen.Screen
import java.util.Properties
import de.lessvoid.nifty.elements.{ControllerEventListener, Element}
import de.lessvoid.xml.xpp3.Attributes
import de.lessvoid.nifty.input.NiftyInputEvent

/**
 * 
 */
class DesignUiController extends Controller {
  def inputEvent(p1: NiftyInputEvent) = false
  def onFocus(p1: Boolean) = {}
  def onStartScreen = {}
  def bind(p1: Nifty, p2: Screen, p3: Element, p4: Properties, p5: ControllerEventListener, p6: Attributes) = {
    
  }
}