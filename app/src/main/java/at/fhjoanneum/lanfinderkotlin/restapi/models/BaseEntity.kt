package at.fhjoanneum.lanfinderkotlin.restapi.service.api.model

import java.io.Serializable

abstract class BaseEntity : Comparable<BaseEntity>, Serializable {
    var id: String = ""

    override fun compareTo(other: BaseEntity): Int {
        return id.compareTo(other.id)
    }
}