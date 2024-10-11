package org.edu.persistence

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.javatime.timestampWithTimeZone

object PetsTable : LongIdTable() {
    val name = text("name")
    val status = text("status")
    val photoUrl = text("photo_url")
    val createdAt = timestampWithTimeZone("created_at")
    val updatedAt = timestampWithTimeZone("updated_at")
}

class PetsEntity(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<PetsEntity>(PetsTable)

    var name by PetsTable.name
    var status by PetsTable.status
    var photoUrl by PetsTable.photoUrl
    var createdAt by PetsTable.createdAt
    var updatedAt by PetsTable.updatedAt
}
