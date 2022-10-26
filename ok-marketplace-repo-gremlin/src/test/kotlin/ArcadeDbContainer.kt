package ru.otus.otuskotlin.marketplace.backend.repository.gremlin

import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.wait.strategy.Wait
import org.testcontainers.utility.DockerImageName

object ArcadeDbContainer {
    val container by lazy {
        GenericContainer(DockerImageName.parse("arcadedata/arcadedb:latest")).apply {
            withExposedPorts(2480, 2424, 8182)
            withEnv("arcadedb.server.rootPassword", "1r2d3g4h")
            withEnv("arcadedb.server.plugins", "GremlinServer:com.arcadedb.server.gremlin.GremlinServerPlugin")
//            withEnv("arcadedb.server.defaultDatabases", "OpenBeer[root]{import:https://github.com/ArcadeData/arcadedb-datasets/raw/main/orientdb/OpenBeer.gz}")
            waitingFor(Wait.forLogMessage(".*ArcadeDB Server started.*\\n", 1))
            start()
            println("ARCADE: http://${host}:${getMappedPort(2480)}")
            println("ARCADE: http://${host}:${getMappedPort(2424)}")
//            Thread.sleep(5000)
            println(this.logs)
            println("RUNNING?: ${this.isRunning}")
        }
    }
}
