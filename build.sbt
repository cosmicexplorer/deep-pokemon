import Dependencies._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "DeepPokemon",
      scalaVersion := "2.12.1",
      version      := "0.1"
    )),
    name := "deep-pokemon",
    libraryDependencies += scalaTest % Test,
    libraryDependencies += "org.java-websocket" % "Java-WebSocket" % "1.3.0",
    libraryDependencies += "org.scalaj" %% "scalaj-http" % "2.3.0",
    libraryDependencies += "org.parboiled" %% "parboiled-scala" % "1.1.8",
    mainClass in assembly := Some("DeepPokemon.WSClient"),
    assemblyOutputPath in assembly := file(System.getProperty("build.location"))
  )
