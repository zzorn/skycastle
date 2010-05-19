package org.skycastle.server

/**
 * 
 */
trait Avatar {


  /**
   * Do some action available to the avatar, like as moving around, controlling wielded equipment or vehicles,
   * or changing settings for controlled guild.
   */
  def doAction(name: Symbol, parameters: Map[Symbol, Object])

  /**
   * All things that are perceived by this avatar, both internal state like hitpoints and attributes,
   * as well as outside entities like characters, items and terrain, and also 'known' things like guild properties.
   */
  def perceptions(): 

}

