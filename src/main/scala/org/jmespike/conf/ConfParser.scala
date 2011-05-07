package org.jmespike.conf

import org.scalaprops.parser.{JsonBeanParser}
import org.scalaprops.Bean
import org.jmespike.conf.Conf
import org.jmespike.shape.{SphereConf}
import org.jmespike.appearance.{MaterialConf, AppearanceConf}
import org.jmespike.lighting.{LightingConf, AmbientLightConf, DirectionalLightConf}
import org.jmespike.scene.TestScene

/**
 * Something that can parse configuration files.
 */
class ConfParser extends JsonBeanParser {

  def registerBeanType[T <: Bean](kind: Class[T]) {
    val name = Symbol(kind.getSimpleName)
    beanFactory.registerBeanType(name, {() => kind.newInstance})
  }

  registerBeanType(classOf[TestScene])
  registerBeanType(classOf[SphereConf])
  registerBeanType(classOf[ColorConf])
  registerBeanType(classOf[RandomColorConf])
  registerBeanType(classOf[MaterialConf])
  registerBeanType(classOf[AppearanceConf])
  registerBeanType(classOf[DirectionalLightConf])
  registerBeanType(classOf[AmbientLightConf])
  registerBeanType(classOf[LightingConf])

}