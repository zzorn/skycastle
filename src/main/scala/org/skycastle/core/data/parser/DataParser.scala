package org.skycastle.core.data.parser

import _root_.org.skycastle.core.data._
import scala.util.parsing.combinator.syntactical.StdTokenParsers
import util.parsing.combinator.ImplicitConversions


/**
 * A second try at a simpler language parser and compiler.
 *
 * @author Hans Haggstrom
 */
object DataParser extends StdTokenParsers  {

  def parse(s: String): ParseResult[Value] = {
    val tokens = new lexical.Scanner(s)
    value(tokens)
  }


  // Setup lexer
  type Tokens = DataLexer
  val lexical = new DataLexer

  // Parsing data structure
  def value: Parser[Value] = bool | /* ref, measure | */ num | text | data | arr | call | fun | failure( "Value expected" )

  // TODO: Add id for longs?

  def data: Parser[Value] = "{" ~ repsep(attribute, opt( ",")) ~ "}" ^^ {case lb ~ entries ~ rb => Data(Map() ++ entries)}
  def arr: Parser[Value] = "[" ~ repsep(value, ",") ~ "]" ^^ {case lb ~ entries ~ rb => Arr(entries)}
  def bool: Parser[Value] = trueValue | falseValue
  def call: Parser[Value] = link ~ "(" ~ repsep(  parameter, ",") ~ ")" ^^ {case l ~ lp ~ params ~ rp => Call(l, params)}
  def fun: Parser[Value] = "fun" ~ "(" ~ repsep(  parameterDeclaration, ",") ~ ")" ~ value ^^ { case c1 ~ c2 ~ params ~ c3 ~ body => Fun(params, body) }
  def num: Parser[Value] = numericLit ^^ { s => Num(s.toDouble) } // TODO: Cast to long/int if integer
  def text: Parser[Value] = stringLit ^^ {case s => Text(s)}

  def link: Parser[Link] = rep1sep(identifier, ".") ^^ {case path => Link(path)}

/*
  def measure: Parser[Measure] = measure ^^ {case m  => new Measure(m)}
*/

  def identifier: Parser[Symbol] = ident ^^ {case id => Symbol(id)}

  private def attribute: Parser[Tuple2[Symbol, Value]] = identifier ~ ":" ~ value ^^ {case name ~ c ~ value => (name, value) }

  private def parameterDeclaration: Parser[Tuple2[Symbol, Option[Value]]] = identifier ~ opt( ":" ~> value) ^^ { case name ~ value => Tuple2(name, value) }
  private def parameter: Parser[Tuple2[Option[Symbol], Value]] = opt(identifier <~ ":") ~ value ^^ { case name ~ value => Tuple2(name, value)}

  def trueValue : Parser[Bool] = "true" ^^ { x => True }
  def falseValue : Parser[Bool] = "false" ^^  { x => False}

}

