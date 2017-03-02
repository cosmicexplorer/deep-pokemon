package DeepPokemon

import java.net._
import java.io._
import java.nio.file._
import javax.net.ssl._

import com.twitter.concurrent.AsyncStream
import com.twitter.conversions.time._
import com.twitter.finagle._
import com.twitter.finagle.util._
import com.twitter.finagle.websocket._
import com.twitter.util._
// import org.java_websocket.client._
// import org.java_websocket.handshake.ServerHandshake
// import resource._
// import scalaj.http._
import spray.json._
import fommil.sjs.FamilyFormats._

import ShowdownParser._

case class Config(
  action_address: String,
  action_path: String,
  sim_address: String,
  http_protocol: String,
  ws_protocol: String,
  ws_path: String
) {
  def socketHost: String = s"$ws_protocol://$sim_address"
  def socketUri: String = s"${socketHost}$ws_path"
  def challengeUri: String = s"$http_protocol://$action_address$action_path"
}

object AuthResponse {
  val regex = """](.*)""".r
}

case class AuthResponse(assertion: Option[String])
case class AuthAssertion(assertion: String)

// class WSClient(cfg: Config)
//     extends WebSocketClient(new URI(cfg.socketUri)) {
//   if (cfg.ws_protocol == "wss") {
//     val ctx = SSLContext.getInstance("TLS")
//     ctx.init(null, null, null)
//     setWebSocketFactory(new DefaultSSLWebSocketClientFactory(ctx))
//   }

//   val parser = new ShowdownParser

//   def trySession(ch: ChallStr): Option[AuthAssertion]

//   // NOTE: BLOCKS!!
//   def makeAuthRequest(
//     username: String, password: String, ch: ChallStr
//   ): Option[AuthAssertion] = {
//     // first check if stored in auth server's session cookie
//     val trySession = Http(cfg.challengeUri)
//       .param("act", "upkeep")
//       .param("challstr", ch.challStr)
//       .asString.body
//     val trySession = Http(cfg.challengeUri)
//       .param("act", "getassertion")
//       .param("userid", "qwejqweqwrqwrqrq")
//       .param("challstr", ch.challStr)
//       .asString.body
//     parseAuthResponse(trySession).orElse {
//       println(s"trying POST: ${cfg.challengeUri}")
//       val encoded = URLEncoder.encode(ch.challStr, "UTF-8")
//       // val encoded = ch.challStr
//       val dataStr = s"act=login&name=$username&pass=$password&challstr=$encoded"
//       // s"&challengekeyid=${ch.keyid}&challenge=${ch.chall}"
//       println(s"dataStr: '$dataStr'")
//       val tryMakeUser = Http(cfg.challengeUri)
//         .postData(dataStr).asString.body
//       // val tryMakeUser = Http(cfg.challengeUri)
//       //   .postForm(Seq(
//       //     ("act", "login"),
//       //     ("name", username),
//       //     ("pass", password),
//       //     // ("challstr", challStr),
//       //     ("challengekeyid", ch.keyid),
//       //     ("challenge", ch.chall)))
//       //     .asString.body
//       parseAuthResponse(tryMakeUser)
//     }.orElse {
//       println("trying GET again")
//       val trySessionAgain = Http(cfg.challengeUri)
//         .param("act", "login")
//         .param("challstr", ch.challStr)
//         .asString.body
//       parseAuthResponse(trySessionAgain)
//     }
//   }

//   def parseAuthResponse(body: String): Option[AuthAssertion] = {
//     println(s"body: '$body'")
//     body match {
//       case AuthResponse.regex(o) =>
//         o.parseJson.convertTo[AuthResponse].assertion.map(AuthAssertion(_))
//       case _ => None
//     }
//   }

//   def formatCommand(cmd: String, rest: String): String = s"/$cmd $rest"

//   def doLogin(username: String, assertion: String): Unit = {
//     val fmt = formatCommand("trn", s"$username,0,$assertion")
//     println(s"fmt: $fmt")
//     send(fmt)
//   }

//   override def onMessage(message: String): Unit = {
//     println(s"msg: '$message'")

//     val username = "wehrlwkhwerew"
//     parser.parse(message) match {
//       case ch @ ChallStr(_, _) =>
//         // val sEnc = URLEncoder.encode(s, "UTF-8")
//         // println(s"challstr: '$s'")
//         // println(s"sEnc: '$sEnc'")
//         makeAuthRequest(username, "mypassword", ch) match {
//           case Some(AuthAssertion(a)) =>
//             println(s"assertion: $a")
//             doLogin(username, a)
//           case None => println("FAILED")
//         }
//       case UpdateUser(name, named, avvy) =>
//         println(s"name='$name', named='$named', avvy='$avvy'")
//       case NotParsed(s) => println(s"'$s' was not parsed!")
//     }
//   }

//   override def onOpen(handshake: ServerHandshake): Unit = {
//     println("opened connection")
//   }

//   override def onClose(code: Int, reason: String, remote: Boolean): Unit = {
//     println(s"closed connection; code: '$code', " +
//       s"reason: '$reason', remote: '$remote'")
//   }

//   override def onError(ex: Exception): Unit = {
//     ex.printStackTrace()
//   }
// }

object WSClient extends App {
  implicit val timer = DefaultTimer.twitter

  def handle(msgs: AsyncStream[Frame]): Future[Unit] =
    msgs.foreach {
      case Frame.Text(msg) => println(msg)
      case _ => println("non text frame?")
    }

  val Array(configFile) = args
  val cfg = new String(Files.readAllBytes(Paths.get(configFile)))
    .parseJson.convertTo[Config]

  val client = Websocket.client.newService(cfg.socketHost)
  val req = Request(
    new URI(cfg.ws_path),
    Map.empty,
    new SocketAddress{},
    AsyncStream.empty
  )
  val receive = client(req)
    .map(r => handle(r.messages))
  Await.result(receive)
}
