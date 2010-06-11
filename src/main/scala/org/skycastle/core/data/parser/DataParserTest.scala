package org.skycastle.core.data.parser

import _root_.org.scalatest.matchers.ShouldMatchers
import _root_.org.scalatest.Spec
import _root_.org.skycastle.core.data.Num

/**
 * 
 */
object DataParserTest  {
  def main(args: Array[String]) {
    print(DataParser.parse(" 1 ").get)

    assert(new Num(1.0) == DataParser.parse(" 1 ").get)
    print( DataParser.parse(" { foo: \"bar\" } ").get)

  }
}

