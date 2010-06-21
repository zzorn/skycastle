package org.skycastle.view.shape.sgine

import _root_.org.sgine.core.Color
import _root_.org.sgine.math.mutable.Vector2
import _root_.org.sgine.math.mutable.Vector3
import _root_.org.sgine.property.AdvancedProperty
import _root_.org.sgine.render.primitive.{Mesh, Primitive}
import _root_.org.sgine.render.Image

/**
 * A parametric cylinder shape.
 */
class Lathe extends PrimitiveComponent {

  val thickness = new AdvancedProperty[(Double)=>Double](t=>10, this)
  val outline = new AdvancedProperty[(Double)=>Double](t=>1, this)
  val twist = new AdvancedProperty[(Double)=>Double](t=>0, this)
  val path = new AdvancedProperty[(Double)=>Vector3](t=>Vector(0,t*100,0), this)
  val closeStart = new AdvancedProperty[Boolean](true, this)
  val closeEnd = new AdvancedProperty[Boolean](true, this)
  val segments = new AdvancedProperty[Int](12, this)
  val rings = new AdvancedProperty[Int](12, this)


  val radius = new AdvancedProperty[Double](10, this)
  val length = new AdvancedProperty[Double](100, this)
  val profile = new AdvancedProperty[Profile]((t,r,v)=>Vector(t,t,r), this)



  private var indexes: Seq[Int] = Nil
  private var vertices: Seq[Double] = Nil
  private var normals: Seq[Double] = Nil
  private var textureCoords: Seq[Double] = Nil

  val texture = new AdvancedProperty[Image](null, this)

  texture.listeners += invalidationHandler
  color.listeners += invalidationHandler

  type Profile = (Double, Double, Vector3) => Vector3


  protected def createPrimitive(): Primitive = {
    val mesh: Mesh = new Mesh(color())
    mesh.indexes = indexes
    mesh.vertices = vertices
    mesh
  }

  private val EMPTY_NORMAL: Vector3 = Vector3.Origo;


  private def updateModel() {
      val rings = 6
      val spokes = 6

      // TODO: Generalize
      val ringSeams = calculateNumberOfRingSeams( topTextureRing, bottomTextureRing, rings )
      val spokeSeams = 1 // One seam for the mid texture
      val topTextureRing = 2
      val bottomTextureRing = 5

      val numberOfVertexes = (spokes +spokeSeams)* ( rings + ringSeams )
      val numberOfTriangles = 2 * ( spokes  + spokeSeams- 1 ) * ( rings + ringSeams - 1 )

      // TODO: Only recreate the mesh data if the topology changed

      generateVertexes( spokes, rings, profile(), topTextureRing, bottomTextureRing, spokeSeams );
      generateFaces( spokes, rings, ringSeams, spokeSeams );
      generateNormals( rings, spokes, topTextureRing, bottomTextureRing, ringSeams, spokeSeams );

  }

  private def generateNormals( rings: Int,
                               spokes: Int,
                               topTextureRing: Int,
                               bottomTextureRing: Int,
                               ringSeams: Int,
                               spokeSeams: Int )
  {
      // Generate vertex normals based on face normals
      // TODO myMeshData.calculateNormals();

      // Fix normals along seams
      smoothNormalsAlongSpokeSeam( rings, spokes, ringSeams, spokeSeams );
      smoothNormalsAlongRing( rings, spokes, topTextureRing, ringSeams, spokeSeams );

      if ( topTextureRing != bottomTextureRing )
      {
          smoothNormalsAlongRing( rings, spokes, bottomTextureRing + 1, ringSeams, spokeSeams );
      }

      // Fix normals at top and bottom
      smoothNormalsAtTip( 0, rings, spokes, ringSeams,spokeSeams);
      smoothNormalsAtTip( rings + ringSeams - 1, rings, spokes, ringSeams, spokeSeams );
  }


  private def smoothNormalsAlongSpokeSeam( rings: Int, spokes: Int, ringSeams: Int, spokeSeams: Int)
  {
      var ring = 0
      while (ring < rings + ringSeams) {
        val v1 = getVertexIndex( 0, ring, spokes, rings, ringSeams, spokeSeams );
        val v2 = getVertexIndex( spokes + spokeSeams - 1, ring, spokes, rings, ringSeams, spokeSeams );
        myMeshData.smoothNormals( v1, v2 );

        ring += 1
      }
  }

  private def smoothNormalsAlongRing( rings: Int, spokes: Int, ring: Int, ringSeams: Int, spokeSeams: Int )
  {
      if ( ring > 0 &&
           ring < rings + ringSeams - 1 )
      {
        var spoke = 0
        while (spoke < spokes + spokeSeams) {

          val v1 = getVertexIndex( spoke, ring, spokes, rings, ringSeams, spokeSeams );
          val v2 = getVertexIndex( spoke, ring + 1, spokes, rings, ringSeams, spokeSeams );
          myMeshData.smoothNormals( v1, v2 );

          spoke += 1
        }
      }
  }

  private def smoothNormalsAtTip( ring: Int, rings: Int, spokes: Int, ringSeams: Int, spokeSeams: Int )
  {
      val sum = new Vector3()
      val normal = new Vector3()

    var spoke = 0
    while (spoke < spokes + spokeSeams) {
      val v = getVertexIndex( spoke, ring, spokes, rings, ringSeams, spokeSeams )
      myMeshData.getNormal( v, normal )
      sum += normal

      spoke += 1
    }

      sum = sum.normalize;

    spoke = 0
    while (spoke < spokes + spokeSeams) {
      val v = getVertexIndex( spoke, ring, spokes, rings, ringSeams, spokeSeams )
      myMeshData.setNormal( v, sum )
      spoke += 1
    }
  }

  private def calculateNumberOfRingSeams( topTextureRing: Int, bottomTextureRing: Int, rings: Int ): Int =
  {
      var ringSeams = 0
      if ( topTextureRing > 0 &&
           topTextureRing < rings )
      {
          ringSeams++;
      }

      if ( bottomTextureRing > 0 &&
           bottomTextureRing < rings &&
           topTextureRing != bottomTextureRing )
      {
          ringSeams++;
      }

      return ringSeams;
  }

  private def clamp(v: Int, min: Int, max: Int): Int = if (v < min) min else if (v > max) max else v

  private def calculateTopTextureRing( rings: Int ): Int =
  {
      val topTextureRing = (int) ( rings * getFloat( "topTextureRing", 0 ) );

      clamp( topTextureRing, 0, rings );
  }

  private def calculateBottomTextureRing( rings: Int, topTextureRing: Int ): Int =
  {
      val bottomTextureRing = (int) ( rings * ( 1.0f - getFloat( "bottomTextureRing", 0 ) ) );

      clamp( bottomTextureRing, topTextureRing, rings );
  }

  private def generateVertexes( spokes: Int,
                                 rings: Int,
                                 profile: Profile,
                                 topTextureRing: Int,
                                 bottomTextureRing: Int,
                                 spokeSeams: Int )
  {
      var position = new Vector3()
      val textureCoordinates = new Vector2()

/*
      final RectangularTextureSection topTextureSection = getParameter( "topTextureSection", DEFAULT_TEXTURE_SECTION );
      final RectangularTextureSection bottomTextureSection = getParameter( "bottomTextureSection", DEFAULT_TEXTURE_SECTION );
      final RectangularTextureSection midTextureSection = getParameter( "midTextureSection", DEFAULT_TEXTURE_SECTION );
*/

      var index = 0
      var spoke = 0
      while(spoke < spokes + spokeSeams)
      {
          var ring = 0
          while (  ring < rings)
          {
              val spokePosition = calculateRelativePosition( spoke, 0, spokes + spokeSeams - 1 );
              val ringPosition = calculateRelativePosition( ring, 0, rings - 1 );
              val midTexturePos = calculateMidTexturePosition( topTextureRing, bottomTextureRing, ring );

              position = profile(spokePosition, ringPosition, position)

              val x = position.x
              val z = position.z

              position.x *= radius()
              position.y *= length() - length() * position.y
              position.z *= radius()

              index = generateVertex( topTextureRing,
                                      bottomTextureRing,
                                      position,
                                      textureCoordinates,
                                      index,
                                      ring,
                                      spokePosition,
                                      x,
                                      z,
                                      midTexturePos /*,
                                      topTextureSection,
                                      midTextureSection,
                                      bottomTextureSection*/);
            ring += 1
          }

          spoke += 1
      }
  }

  private def calculateRelativePosition( position: Double,
                                               startPosition: Double,
                                               endPosition: Double ):Double =
{
    // Check for special case where start and end positions are the same, and the position is on them.
    // In this case return 0.5.
    if ( position == startPosition &&
         position == endPosition )
    {
        return 0.5
    }

    // Check invalid position.
    if ( startPosition == endPosition )
    {
        throw new IllegalArgumentException( "The start position and end positions are the same ('" + startPosition + "' ), but the position is '" + position + "'.  Can not interpolate." )
    }

    return ( position - startPosition ) / ( endPosition - startPosition )
}

  private def calculateMidTexturePosition( topTextureRing: Int, bottomTextureRing: Int, ring: Int ): Double =
  {
      if ( topTextureRing == bottomTextureRing ) 0
      else calculateRelativePosition( ring, topTextureRing, bottomTextureRing )
  }

  private def generateVertex( topTextureRing: Int,
                              bottomTextureRing: Int,
                              position: Vector3,
                              textureCoordinates: Vector2,
                              index: Int,
                              ring: Int,
                              spokePosition: Double,
                              x: Double,
                              z: Double,
                              midTexturePos: Double /*,
                              final RectangularTextureSection topTextureSection,
                              final RectangularTextureSection midTextureSection,
                              final RectangularTextureSection bottomTextureSection */): Int =
  {
/*
      if ( ring < topTextureRing )
      {
          calculateTopTextureCoordinates( textureCoordinates, x, z, topTextureSection );
          index = addPoint( position, textureCoordinates, index );
      }
      else
      {
          if ( ring == topTextureRing )
          {
              if ( ring > 0 )
              {
                  calculateTopTextureCoordinates( textureCoordinates, x, z, topTextureSection  );
                  index = addPoint( position, textureCoordinates, index );
              }

              if ( ring == bottomTextureRing )
              {
                  calculateBottomTextureCoordinates( textureCoordinates, x, z, bottomTextureSection );
                  index = addPoint( position, textureCoordinates, index );
              }
              else
              {
                  calculateMidTextureCoordinates( textureCoordinates, spokePosition, midTexturePos, midTextureSection);
                  index = addPoint( position, textureCoordinates, index );
              }
          }
          else if ( ring > topTextureRing &&
                    ring < bottomTextureRing )
          {
              calculateMidTextureCoordinates( textureCoordinates, spokePosition, midTexturePos, midTextureSection);
              index = addPoint( position, textureCoordinates, index );
          }
          else if ( ring == bottomTextureRing )
          {
              if ( ring > 0 )
              {
                  calculateMidTextureCoordinates( textureCoordinates, spokePosition, midTexturePos, midTextureSection);
                  index = addPoint( position, textureCoordinates, index );
              }

              calculateBottomTextureCoordinates( textureCoordinates, x, z, bottomTextureSection );
              index = addPoint( position, textureCoordinates, index );
          }
          else if ( ring > bottomTextureRing )
          {
              calculateBottomTextureCoordinates( textureCoordinates, x, z, bottomTextureSection );
              index = addPoint( position, textureCoordinates, index );
          }
          else
          {
              throw new IllegalStateException( "We shouldn't end up here.  Ring = '" + ring + "', top='" + topTextureRing + "', bottom='" + bottomTextureRing + "' " );
          }
      }
*/

     addPoint( position, textureCoordinates, index );
  }

  private def addPoint( position: Vector3, textureCoordinates: Vector2, index: Int ): Int =
  {
      myMeshData.setPoint( index++,
                           position,
                           Color.White,
                           EMPTY_NORMAL,
                           textureCoordinates );
      return index;
  }

  private def generateFaces( spokes: Int, rings: Int, ringSeams: Int, spokeSeams : Int)
  {
      var faceIndex = 0
      var spoke = 0
      while (  spoke < spokes + spokeSeams - 1 )
      {
        var ring = 0
          while ( ring < rings + ringSeams - 1)
          {
              // OPTIMIZE: Skip generating faces for the seams, to reduce number of faces a bit - can result in cracks though

              myMeshData.setQuadFace( faceIndex,
                                      getVertexIndex( spoke, ring, spokes, rings, ringSeams,spokeSeams ),
                                      getVertexIndex( spoke + 1, ring, spokes, rings, ringSeams, spokeSeams ),
                                      getVertexIndex( spoke + 1, ring + 1, spokes, rings, ringSeams, spokeSeams ),
                                      getVertexIndex( spoke, ring + 1, spokes, rings, ringSeams, spokeSeams ),
                                      false );
              faceIndex += 2;
            ring +=1
          }
        spoke +=1
      }
  }


  private def getVertexIndex( spoke: Int,
                              ring: Int,
                              spokes: Int,
                              rings: Int,
                              ringSeams: Int,
                              spokeSeams: Int ) : Int =
  {
      if ( spoke >= spokes +spokeSeams)
      {
          spoke = 0
      }

      spoke * ( rings + ringSeams ) + ring
  }
}






/*
   private void calculateMidTextureCoordinates( final Vector2f textureCoordinates,
                                                final float spokePosition,
                                                final float midTexturePos,
                                                final RectangularTextureSection textureSection )
   {
       textureCoordinates.x = spokePosition;
       textureCoordinates.y = midTexturePos;

       textureSection.getTextureCoordinates( textureCoordinates, textureCoordinates );
   }

   private void calculateBottomTextureCoordinates( final Vector2f textureCoordinates,
                                                   final float x,
                                                   final float z,
                                                   final RectangularTextureSection textureSection )
   {
       textureCoordinates.x =  0.5f * ( 1 - x );
       textureCoordinates.y =  0.5f * ( 1 - z );

       textureSection.getTextureCoordinates( textureCoordinates, textureCoordinates );
   }

   private void calculateTopTextureCoordinates( final Vector2f textureCoordinates,
                                                final float x,
                                                final float z,
                                                final RectangularTextureSection textureSection )
   {
       textureCoordinates.x = 0.5f * ( x + 1 );
       textureCoordinates.y = 0.5f * ( z + 1 );

       textureSection.getTextureCoordinates( textureCoordinates, textureCoordinates );
   }
*/




