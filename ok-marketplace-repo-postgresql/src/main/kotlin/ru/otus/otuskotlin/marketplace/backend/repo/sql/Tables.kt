package ru.otus.otuskotlin.marketplace.backend.repo.sql

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.statements.InsertStatement
import ru.otus.otuskotlin.marketplace.common.models.MkplAd
import ru.otus.otuskotlin.marketplace.common.models.MkplAdId
import ru.otus.otuskotlin.marketplace.common.models.MkplAdLock
import ru.otus.otuskotlin.marketplace.common.models.MkplDealSide
import ru.otus.otuskotlin.marketplace.common.models.MkplUserId
import ru.otus.otuskotlin.marketplace.common.models.MkplVisibility
import java.util.*

object AdsTable : StringIdTable("Ads") {
    val title = varchar("title", 128)
    val description = varchar("description", 512)
    val ownerId = reference("owner_id", UsersTable.id).index()
    val visibility = enumeration("visibility", MkplVisibility::class)
    val adType = enumeration("deal_side", MkplDealSide::class).index()
    val lock = varchar("lock", 50)

    override val primaryKey = PrimaryKey(id)

    // Mapper functions from sql-like table to MkplAd
    fun from(res: InsertStatement<Number>) = MkplAd(
        id = MkplAdId(res[id].toString()),
        title = res[title],
        description = res[description],
        ownerId = MkplUserId(res[ownerId].toString()),
        visibility = res[visibility],
        adType = res[adType],
        lock = MkplAdLock(res[lock])
    )

    fun from(res: ResultRow) = MkplAd(
        id = MkplAdId(res[id].toString()),
        title = res[title],
        description = res[description],
        ownerId = MkplUserId(res[ownerId].toString()),
        visibility = res[visibility],
        adType = res[adType],
        lock = MkplAdLock(res[lock])
    )
}

object UsersTable : StringIdTable("Users") {
    override val primaryKey = PrimaryKey(AdsTable.id)
}

open class StringIdTable(name: String = "", columnName: String = "id", columnLength: Int = 50) : IdTable<String>(name) {
    override val id: Column<EntityID<String>> =
        varchar(columnName, columnLength).uniqueIndex().default(generateUuidAsStringFixedSize())
            .entityId()
    override val primaryKey by lazy { super.primaryKey ?: PrimaryKey(id) }
}

fun generateUuidAsStringFixedSize() = UUID.randomUUID().toString().replace("-", "").substring(0, 9)
