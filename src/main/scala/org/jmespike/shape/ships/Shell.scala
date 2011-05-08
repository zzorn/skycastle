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

    // Simple solid hull
    base.meshBuilder.addQuad(base.topRight, base.topLeft, base.bottomLeft, base.bottomRight)

  }
}