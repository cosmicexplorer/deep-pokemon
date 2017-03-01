package DeepPokemon

import org.parboiled.scala._

// object ShowdownParser {
//   val messageTypes = Set(
//     "challstr"
//   )
// }

class ShowdownParser extends Parser {
  def makeMessageType[T](name: String, r: Rule1[T]): Rule1[T] = rule {
    str(s"|$name|") ~ r
  }

  def Challstr: Rule1[String] = makeMessageType(
    "challstr", rule { zeroOrMore(ANY) ~> { (s: String) => s } })

  def Message: Rule1[String] = Challstr

  def parse(str: String): String = {
    ReportingParseRunner(Message).run(str).result match {
      case Some(s) => s
      case None => "not matched!"
    }
  }
}
