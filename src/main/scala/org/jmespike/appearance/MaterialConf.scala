package org.jmespike.appearance


import org.scalaprops.Bean
import simplex3d.math.float.functions._
import simplex3d.math.float._
import java.awt.Color
import com.jme3.material.Material
import org.jmespike.{Context, Conf}
import org.jmespike.utils.VectorConversions._
import org.jmespike.conf.{RandomColorConf, ColorConf}
import java.util.Random

/**
 * The surface appearance of a material.
 */
class MaterialConf() extends Conf {


  // Diffuse color
  val color = p('color, new RandomColorConf())

  // Ambient color = self illuminating color
  val ambientColor = p('ambientColor, new RandomColorConf())

  // Color for calculating specular highlight (colored for metals, otherwise white)
  val specularColor = p('specularColor, new RandomColorConf())

  // Size of specular highlight
  val shininess = p('shininess, 5f).editor(makeSlider(0, 20))

  val texture = p('texture, "placeholder.png")

  // Init colors
  specularColor().sat := 0f
  specularColor().lum := 1f
  ambientColor().sat := 0f
  ambientColor().lum := 0f
  color().sat := 0f
  color().lum := 0.5f


  def createMaterial(random: Random = new Random()): Material =  {
    val shader = "Common/MatDefs/Light/Lighting.j3md"
    val mat = new Material(Context.assetManager, shader)
    val loadedTexture = Context.assetManager.loadTexture("textures/" + texture)
    if (loadedTexture != null) mat.setTexture("DiffuseMap", loadedTexture);
    mat.setBoolean("UseMaterialColors", true)
    mat.setColor("Ambient", ambientColor().createColor(random))
    mat.setColor("Diffuse", color().createColor(random))
    mat.setColor("Specular", specularColor().createColor(random))
    mat.setFloat("Shininess", shininess())
    mat
  }
}