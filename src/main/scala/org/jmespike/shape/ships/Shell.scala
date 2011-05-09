package org.jmespike.shape.ships

import org.scalaprops.ui.editors.SelectionEditorFactory
import simplex3d.math.float.functions._
import simplex3d.math.float._
import simplex3d.math.floatx.functions._

/**
 * A shell is a surface connected to one side of a core or hull.
 * A shell can be divided into panels, some of which can act as bases for other ship components.
 */
class Shell extends ShipComponent {

  // TODO: Some bean enum for these
//  private val profileEditor = new SelectionEditorFactory[ShellProfile](List(FlatProfile, TriangularProfile, SmoothProfile))
//  val horizontalProfile = p[ShellProfile]('horizontalProfile, FlatProfile).editor(profileEditor)
//  val verticalProfile = p[ShellProfile]('verticalProfile, FlatProfile).editor(profileEditor)

  // TODO: go fancy with detail generation
  val detailAmount = p('detailAmount, 0.5f)

  val bevelSize = p('bevelSize, 0.1f).editor(makeSlider(0f, 1f))

  // TODO: Also specify any ship components connected to panels on the shell.
  
  def buildMesh(style: ShipConf, base: ComponentBase) {

    val meshBuilder = base.meshBuilder

    // Simple solid hull with sharp corners

    val tr = meshBuilder.addVertex(mix(base.topRightVertex, base.baseCenter, bevelSize()))
    val tl = meshBuilder.addVertex(mix(base.topLeftVertex, base.baseCenter, bevelSize()))
    val bl = meshBuilder.addVertex(mix(base.bottomLeftVertex, base.baseCenter, bevelSize()))
    val br = meshBuilder.addVertex(mix(base.bottomRightVertex, base.baseCenter, bevelSize()))

    meshBuilder.addQuad(tl, tr, br, bl)
    meshBuilder.addQuad(base.topLeft, base.topRight, tr, tl)
    meshBuilder.addQuad(base.topRight, base.bottomRight, br, tr)
    meshBuilder.addQuad(base.bottomLeft, base.topLeft, tl, bl)
    meshBuilder.addQuad(base.bottomRight, base.bottomLeft, bl, br)

  }
}