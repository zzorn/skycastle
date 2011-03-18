package org.skycastle.client.appearance

import org.skycastle.client.wrappers.{Vec3, ColorBean}

/**
 * 
 */

trait BasicAppearance extends Appearance {

  val pos = p('pos, new Vec3())
  val scale = p('scale, new Vec3(1, 1, 1))

  val material = p('material, "Common/MatDefs/Misc/SolidColor.j3md")
  val textureMap = p('textureMap, "textures/placeholder.png")
  val normalMap = p('normalMap, "textures/placeholder.png")
  val specularMap = p('specularMap, "textures/placeholder.png")
  val color = p('color, new ColorBean(1, 0, 0))


}