package ru.otus.otuskotlin.marketplace.common.permissions

import ru.otus.otuskotlin.marketplace.common.models.MkplUserId

data class MkplPrincipalModel(
    val id: MkplUserId = MkplUserId.NONE,
    val fname: String = "",
    val mname: String = "",
    val lname: String = "",
    val groups: Set<MkplUserGroups> = emptySet()
) {
    companion object {
        val NONE = MkplPrincipalModel()
    }
}
