package DeepPokemon

import org.parboiled.scala._

object ShowdownParser {
  sealed trait ShowdownMessage

  case class ChallStr(keyid: String, chall: String) extends ShowdownMessage {
    def challStr: String = keyid + "|" + chall
  }
  case class UpdateUser(username: String, named: Boolean, avatar: Int)
      extends ShowdownMessage
  case class NotParsed(str: String) extends ShowdownMessage
}

class ShowdownParser extends Parser {
  import ShowdownParser._

  def String: Rule1[String] = rule { zeroOrMore(ANY) ~> (s => s) }
  def NonPipe: Rule1[String] = rule {
    oneOrMore(noneOf(Array('|'))) ~> (s => s)
  }
  def BoolAsNum: Rule1[Boolean] = rule { (str("0") | str("1")) ~> {
    (b: String) => Integer.parseInt(b) == 1
  }}
  def NatNum: Rule1[Int] = rule { oneOrMore("0" - "9") ~> { (n: String) =>
    Integer.parseInt(n)
  }}

  def makeMessageRule[T](name: String, r: Rule1[T]): Rule1[T] = rule {
    str(s"|$name|") ~ r
  }

  def ChallStrMsg: Rule1[ChallStr] = makeMessageRule(
    "challstr", rule { NonPipe ~ str("|") ~ String ~~> ChallStr })

  def UpdateUserMsg: Rule1[UpdateUser] = makeMessageRule(
    "updateuser", rule {
      String ~ str("|") ~ BoolAsNum ~ str("|") ~ NatNum ~~> UpdateUser
    }
  )

  def Message: Rule1[ShowdownMessage] = ChallStrMsg | UpdateUserMsg

  def parse(str: String): ShowdownMessage = {
    ReportingParseRunner(Message).run(str).result match {
      case Some(s) => s
      case None => NotParsed(str)
    }
  }
}
