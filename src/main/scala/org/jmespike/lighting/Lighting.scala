package org.jmespike.lighting

import org.jmespike.conf.Conf
import com.jme3.scene.Spatial
import com.jme3.light.{DirectionalLight, AmbientLight}
import org.scalaprops.{Property, Bean, BeanListener}

/**
 * Keeps track of the lighting of some scene.
 */
class Lighting(node: Spatial, initialLightConf: LightingConf = new LightingConf()) {
  require(node != null, "can't set up lighting for null node")

  private var _lightConf: LightingConf = null
  private val ambientLight = new AmbientLight()
  private val directionalLight1 = new DirectionalLight()
  private val directionalLight2 = new DirectionalLight()
  private val directionalLight3 = new DirectionalLight()

  private val confListener: BeanListener = new BeanListener {
    def onPropertyRemoved(bean: Bean, property: Property[ _ ]) {}
    def onPropertyAdded(bean: Bean, property: Property[ _ ]) {}
    def onPropertyChanged(bean: Bean, property: Property[ _ ]) {
      updateLights()
    }
  }

  node.addLight(ambientLight)
  node.addLight(directionalLight1)
  node.addLight(directionalLight2)
  node.addLight(directionalLight3)

  lightConf = initialLightConf

  def lightConf = _lightConf
  def lightConf_=(conf: LightingConf) {
    if (_lightConf != null) _lightConf.removeDeepListener(confListener)
    _lightConf = conf
    if (_lightConf != null) _lightConf.addDeepListener(confListener)

    updateLights()
  }


  def updateLights() {
    val conf = lightConf
    if (conf != null) {
      conf.ambient().configure(ambientLight)
      conf.directional1().configure(directionalLight1)
      conf.directional2().configure(directionalLight2)
      conf.directional3().configure(directionalLight3)
    }
  }


}