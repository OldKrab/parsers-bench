package org.parser

import org.meerkat.input.Input
import org.meerkat.parsers.executeQuery
import org.openjdk.jmh.annotations.{Benchmark, Param, Scope, Setup, State}
import org.parser.Neo4jUtils


@State(Scope.Benchmark)
class RDFMeerkatBench {
  @Param(Array(
    "atom-primitive.owl",
    "biomedical-mesure-primitive.owl",
    "foaf.rdf",
    //  "funding.rdf",
    "generations.owl",
    "people_pets.rdf",
    "pizza.owl",
    "skos.rdf",
    "travel.owl",
    "univ-bench.owl",
    "wine.rdf"))
  var file = ""

  var graph: Input[String, String] = _

  @Setup
  def setup(): Unit = {

    graph = Neo4jUtils.getGraph(file, Meerkat.edgesToNeo4jGraph)
  }

  @Benchmark
  def firstGrammar(): Unit = {
    Meerkat.parse(graph, Meerkat.grammar.G1)
  }

  @Benchmark
  def secondGrammar(): Unit = {
    Meerkat.parse(graph, Meerkat.grammar.G2)
  }

  @Benchmark
  def yagoGrammar(): Unit = {
    val cnt = executeQuery(Meerkat.grammar.yagoG, graph).size
    print(cnt)
  }
}