package org.jmespike

import com.jme3.asset.AssetManager
import com.jme3.scene.Spatial
import java.util.ArrayList
import com.jme3.bounding.BoundingBox
import scala.collection.JavaConversions._
import org.scalaprops.parser.{BeanParser, JsonBeanParser}
import org.scalaprops.Bean

/**
 * Singleton for accessing services.
 */
object Context {

  val beanParser = createBeanParser

  var settings = new GameConf

  def reloadConf() {
    Main.reload = true
  }
 

  def assetManager: AssetManager = Main.getAssetManager



  private def createBeanParser: BeanParser = {

    val beanParser = new JsonBeanParser()

    def registerBeanType[T <: Bean](kind: Class[T]) {
      val name = Symbol(kind.getSimpleName)
      beanParser.beanFactory.registerBeanType(name, {() => kind.newInstance})
    }
/*
    registerBeanType(classOf[EdgeSettings])
    registerBeanType(classOf[MaterialSettings])
    registerBeanType(classOf[SurfaceSettings])
    registerBeanType(classOf[PhysicsSettings])
    registerBeanType(classOf[SpeedSettings])
    registerBeanType(classOf[ColorSettings])
    registerBeanType(classOf[CreatureSettings])
    registerBeanType(classOf[PlatformType])
    registerBeanType(classOf[Level])
    registerBeanType(classOf[GameSettings])
    registerBeanType(classOf[DirectionalLightSettings])
    registerBeanType(classOf[AmbientLightSettings])
*/
    beanParser
  }

}