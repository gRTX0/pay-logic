package nspsolutionspaylogic.paylogic.Entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class Subscriber(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val code: String,
    val name:String,
    val surname: String,
    var debt:Double
)