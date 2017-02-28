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
    mainClass in assembly := Some("DeepPokemon.WSClient"),
    assemblyOutputPath in assembly := file(System.getProperty("build.location"))
  )
