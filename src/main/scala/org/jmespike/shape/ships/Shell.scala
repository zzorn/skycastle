package org.jmespike.shape.ships

import org.scalaprops.ui.editors.SelectionEditorFactory

/**
 * A shell is a surface connected to one side of a core or hull.
 * A shell can be divided into panels, some of which can act as bases for other ship components.
 */
class Shell extends ShipComponent {

  private val profileEditor = new SelectionEditorFactory[ShellProfile](List(FlatProfile, TriangularProfile, SmoothProfile))


  val horizontalProfile = p[ShellProfile]('horizontalProfile, FlatProfile).editor(profileEditor)
  val verticalProfile = p[ShellProfile]('verticalProfile, FlatProfile).editor(profileEditor)

  // TODO: go fancy with detail generation
  val detailAmount = p('detailAmount, 0.5f)

  // TODO: Also specify any ship components connected to panels on the shell.
  
  def buildMesh(style: ShipConf, base: ComponentBase) {

    val meshBuilder = base.meshBuilder

    // Simple solid hull with sharp corners

    val tr = meshBuilder.addVertex(base.topRightVertex)
    val tl = meshBuilder.addVertex(base.topLeftVertex)
    val bl = meshBuilder.addVertex(base.bottomLeftVertex)
    val br = meshBuilder.addVertex(base.bottomRightVertex)

    meshBuilder.addQuad(tl, tr, br, bl)
    meshBuilder.addQuad(base.topLeft, base.topRight, tr, tl)
    meshBuilder.addQuad(base.bottomRight, base.topRight, tr, br)
    meshBuilder.addQuad(base.bottomLeft, base.topLeft, tl, bl)
    meshBuilder.addQuad(base.bottomRight, base.bottomLeft, bl, br)

  }
}