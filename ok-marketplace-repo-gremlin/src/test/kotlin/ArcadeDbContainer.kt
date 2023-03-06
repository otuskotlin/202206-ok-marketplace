package ru.otus.otuskotlin.marketplace.backend.repository.gremlin

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.output.OutputFrame
import org.testcontainers.containers.output.Slf4jLogConsumer
import org.testcontainers.containers.wait.strategy.Wait
import org.testcontainers.utility.DockerImageName


object ArcadeDbContainer {
     var logger: Logger = LoggerFactory.getLogger(this::class.java)
    val container by lazy {
        GenericContainer(DockerImageName.parse("arcadedata/arcadedb:latest")).apply {
            withExposedPorts(2480, 2424, 8182)
            withEnv("JAVA_OPTS","""
       -Darcadedb.server.rootPassword=root_root
       -Darcadedb.server.plugins.GremlinServer=com.arcadedb.server.gremlin.GremlinServerPlugin""")
    //        withEnv("-Darcadedb.server.plugins", "GremlinServer:com.arcadedb.server.gremlin.GremlinServerPlugin")
//            withEnv("arcadedb.server.defaultDatabases", "OpenBeer[root]{import:https://github.com/ArcadeData/arcadedb-datasets/raw/main/orientdb/OpenBeer.gz}")
            waitingFor(Wait.forLogMessage(".*ArcadeDB Server started.*\\n", 1))
            start()


            println("ARCADE: http://${host}:${getMappedPort(2480)}")
            println("ARCADE: http://${host}:${getMappedPort(2424)}")
            Thread.sleep(10000)
            println(this.logs)
            println("RUNNING?: ${this.isRunning}")
        }
    }
    init {
        val logConsumer = Slf4jLogConsumer(logger)
        container.followOutput(logConsumer,OutputFrame.OutputType.STDOUT)
    }
}
