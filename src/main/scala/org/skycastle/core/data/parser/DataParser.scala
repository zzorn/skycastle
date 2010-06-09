package org.skycastle.core.data.parser

import _root_.org.skycastle.core.data._
import scala.util.parsing.combinator.syntactical.StdTokenParsers
import util.parsing.combinator.ImplicitConversions


/**
 * A second try at a simpler language parser and compiler.
 *
 * @author Hans Haggstrom
 */
class DataParser extends StdTokenParsers  {

  // Setup lexer
  type Tokens = DataLexer
  val lexical = new DataLexer

  // Parsing data structure
  def value: Parser[Value] = bool | measure | num | text | data | arr | call | fun | link

  def data: Parser[Data] = "{" ~ repsep(attribute, opt( ",")) ~ "}"
  def arr: Parser[Arr] = "[" ~ repsep(value, ",") ~ "]"
  def bool: Parser[Bool] = trueValue | falseValue
  def call: Parser[Call] = link ~ "(" ~ repsep(  parameter, ",") ~ ")"
  def fun: Parser[Fun] = "fun" ~ "(" ~ repsep(  parameterDeclaration, ",") ~ ")" ~ value ^^ { case c1 ~ c2 ~ params ~ body => new Fun(params, body) }
  def num: Parser[Num] = numericLit ^^ { s => new Num(s.toDouble) } // TODO: Cast to long/int if integer
  def measure: Parser[Measure] = numericLit ~ rep1(scaledUnit) opt( "/" ~ rep1(scaledUnit) )
  def text: Parser[Text]
  def link: Parser[Link]

  def scaledUnit: Parser[String] = opt(scale) ~ unit ~ opt(exp)
  def scale: Parser[String] = "n" | "u" | "m" | "c" | "d" | "k" | "M" | "G" | "T"
  def unit: Parser[String] = "m" | "g" | "s" | "N" | "V" | "A"
  def exp: Parser[Int] = numericLit

  private def parameterDeclaration: Parser[Tuple3[Symbol, Option[Value]]] = ident ~ opt( ":" ~> value) ^^ { case name ~ value => Tuple2(name, value) }
  private def parameter: Parser[Tuple2[Option[Symbol], Value]] = opt(ident <~ ":") ~ value ^^ { case name ~ value => Tuple2(name, value)}

  private def attribute: Parser[Tuple2[Symbol, Value]] = ident ~ ":" ~ value ^^ {case name ~ c ~ value => (name, value) }

  def invoke : Parser[Expression] =
   (
           primitive ~ rep(
             ("(" ~! repsep( parameter, opt( ",") )  <~ ")" ^^ { case lp ~ params => {leftSide:Expression => Call(leftSide,params)} } ) |
             ("." ~! ident                                  ^^ { case dt ~ id => {leftSide:Expression =>Dot(leftSide,id)} }  )
           )
    ) ^^ { case first ~ rest => rest.foldLeft( first ) { (leftSide, next) => next(leftSide)  } }

  def primitive : Parser[Expression] = boolean | number | text | block | list | parens | reference | failure( "Expression expected" )

  /*
  def parameter : Parser[Param] = namedParameter | unnamedParameter | failure("parameter expected")
  def namedParameter : Parser[Param] = ident ~ "=" ~! expression ^^ {case name ~ eq ~ value => Param(name, value)}
  def unnamedParameter : Parser[Param] = expression ^^ {case value => new Param(value)}

  def reference: Parser[Ref] = ident ^^ { case r => Ref( r ) }

  def definition : Parser[Definition] = rep(metadata) ~ ident ~ ("?" | ":") ~! expression ^^
          { case md ~ name ~ definitionType ~ value => Definition( name, value, makeMetadataMap( md ), definitionType.equals("?")) }
  def metadata : Parser[Metadata] = "@" ~> ident ~ opt( "=" ~! expression  ) ^^
          { case name ~ expr => expr match {
              case Some( c ~ x ) => Metadata(name, x)
              case None => Metadata(name, True) }
          }  |
          "@" ~> err( "metadata expected" )
  private def makeMetadataMap( metadata : List[Metadata] ) = metadata.foldLeft(Map[String, Metadata]()) { (m, d) => m(d.identifier) = d }

  def block : Parser[Block] = "{" ~! importClause ~ repsep( definition, opt(",") ) ~ opt(",") ~ blockResult <~ "}" ^^
          { case lb ~ imports ~ props ~ comma ~ result => Block( imports, props ::: result) } |
          failure("block expected")

  def importClause : Parser[Imports] = rep( "import" ~> rep1sep( importPath, "," )) ^^ { case listOfImports  => Imports( List.flatten( listOfImports ) ) }
  def importPath : Parser[Import] = rep1sep( ident, ".")  ^^ { case path  => Import(path) }

  def blockResult : Parser[List[Definition]] = opt(expression) ^^
          { x => x match { case Some(e) => List( Definition( "result", e, Map.empty, false ) ) ; case None => Nil }}
 */

  def parens : Parser[Expression] =  "(" ~! expression ~ ")" ^^ { case lp ~ e ~ rp => Parens(e) }

  def number : Parser[Num] = numericLit ^^ { s => new Num(s.toDouble) }

  def text : Parser[Text] =  stringLit ^^ { s => new Text(s.toString) }

  def list : Parser[Arr] = "[" ~! repsep( expression, opt(",") ) <~ opt(",") <~ "]" ^^ { case lb ~ l  => new Arr(l) }

  def trueValue : Parser[Bool] = "true" ^^ { x => new Bool(true) }
  def falseValue : Parser[Bool] = "false" ^^  { x => new Bool(false)}



  private def binaryExpression( part : Parser[Expression], op : Parser[String], resultFunc : (Expression, String, Expression) => Expression  ) : Parser[Expression] =
    part ~ rep( op ~! part ) ^^ { case first ~ rest =>
      rest.foldLeft( first ) {
        (ready, next) => next match { case o ~ e => resultFunc( ready, o, e ) }
      }
    }

  private def numToNumExpression( part : Parser[Expression], op : Parser[String], resultFunc : (Expression, String, Expression) => Expression  ) : Parser[Expression] =
    part ~ rep( op ~! part ) ^^ { case first ~ rest =>
      rest.foldLeft( first ) {
        (ready, next) => next match { case o ~ e => ready.operation(o, e)}
      }
    }




}

