package org.skycastle.client.designer

import de.lessvoid.nifty.controls.Controller
import de.lessvoid.nifty.Nifty
import java.util.Properties
import de.lessvoid.nifty.elements.{Element}
import de.lessvoid.xml.xpp3.Attributes
import de.lessvoid.nifty.input.NiftyInputEvent
import de.lessvoid.nifty.screen.{ScreenController, Screen}

/**
 * 
 */
class DesignUiController extends ScreenController {
  def onStartScreen = {}
  def onEndScreen = {}
  def bind(nifty: Nifty, screen: Screen) {


    
  }

  def invokeCommand(commandName: String) {
    print("User invoked "+commandName)
  }
}