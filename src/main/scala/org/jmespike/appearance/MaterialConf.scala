package org.jmespike.appearance


import org.scalaprops.Bean
import simplex3d.math.float.functions._
import simplex3d.math.float._
import java.awt.Color
import com.jme3.material.Material
import org.jmespike.conf.Conf
import org.jmespike.{Context}
import org.jmespike.utils.VectorConversions._
import org.jmespike.conf.{RandomColorConf, ColorConf}
import java.util.Random
import org.jmespike.utils.XorShiftRandom
import com.jme3.asset.AssetManager

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


  def createMaterial(seed: Int, assetManager: AssetManager): Material =  {
    val random: Random = new XorShiftRandom(seed)

    val shader = "Common/MatDefs/Light/Lighting.j3md"
    val mat = new Material(assetManager, shader)
    val loadedTexture = assetManager.loadTexture("textures/" + texture)
    if (loadedTexture != null) mat.setTexture("DiffuseMap", loadedTexture);
    mat.setBoolean("UseMaterialColors", true)
    mat.setColor("Ambient", ambientColor().createColor(random))
    mat.setColor("Diffuse", color().createColor(random))
    mat.setColor("Specular", specularColor().createColor(random))
    mat.setFloat("Shininess", shininess())
    mat
  }
}