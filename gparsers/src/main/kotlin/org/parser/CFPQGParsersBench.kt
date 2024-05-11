package org.parser

import org.neo4j.harness.Neo4j
import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Param
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.Setup
import org.openjdk.jmh.annotations.State
import org.openjdk.jmh.annotations.TearDown
import org.parser.neo4j.DefaultNeo4jGraph

@State(Scope.Benchmark)
open class CFPQGParsersBench {
    @Param("")
    var file = ""


    lateinit var graph: DefaultNeo4jGraph
    lateinit var neo4j: Neo4j
    @Setup
    fun setup(): Unit {
        if(file == "") throw RuntimeException("No file")

        neo4j = GParsers.openInProcessNeo4jDb()
        graph = CFPQCsvGraph.getGraph(file, neo4j.defaultDatabaseService())
    }

    @TearDown
    fun tearDown() {
        neo4j.close()
    }

    @Benchmark
    fun firstQuery() {
        val cnt = GParsers.parse(graph, CFPQCsvGraph.firstGrammar())
        println(cnt)
    }

}
