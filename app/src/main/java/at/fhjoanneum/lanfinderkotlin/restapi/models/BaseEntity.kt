package at.fhjoanneum.lanfinderkotlin.restapi.service.api.model

abstract class BaseEntity : Comparable<BaseEntity> {
    var id: String = ""

    override fun compareTo(other: BaseEntity): Int {
        return id.compareTo(other.id)
    }
}