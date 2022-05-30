package nspsolutionspaylogic.paylogic.controller

import nspsolutionspaylogic.paylogic.Entity.ApiResponse
import nspsolutionspaylogic.paylogic.Entity.Payment
import nspsolutionspaylogic.paylogic.Entity.Subscriber
import org.springframework.web.bind.annotation.*
import java.util.ArrayList
import kotlin.math.roundToInt

@RestController
@RequestMapping("/api")
class PayLogicController {
    private val subscribers: MutableList<Subscriber> = ArrayList()

    init {
        subscribers.addAll(
            listOf(
                Subscriber(
                    "111", "Aysu", "Sərkərova", 32.45
                ),
                Subscriber(
                    "222", "Əbdülrəhman", "Sadıxlı", 23.54
                ),
                Subscriber(
                    "233", "Abdullah", "Mahmudov", 45.32
                ),
            )
        )
    }

    @PostMapping("/subscriber")
    fun create(@RequestBody subscriber: Subscriber?): ApiResponse? {

        var response: ApiResponse
        try {
            subscribers.add(subscriber!!)
            response = ApiResponse(200, "Success", subscriber)
        } catch (e: Exception) {
            e.printStackTrace()
            response = ApiResponse(
                408, "Error: " + e.message, null
            )
        }
        return response
    }

    @GetMapping("subscribers")
    fun read(): Iterable<Subscriber> {
        return subscribers
    }

    @DeleteMapping("/subscriber/{code}")
    fun delete(@PathVariable code: String?) {
        subscribers.removeIf { (c): Subscriber -> c.equals(code) }
    }

    @GetMapping("debt/{code}")
    fun debt(@PathVariable code: String): ApiResponse {
        //subscribers.takeWhile { it.code == code }
        val subscriber = subscribers.find { it.code == code }

        val response = ApiResponse(
            if (subscriber == null) 404 else 200, if (subscriber == null) "NOT FOUND" else "Success", subscriber
        )

        return response
    }

    @PostMapping("pay")
    fun pay(@RequestBody payment: Payment): ApiResponse {
        val subscriber = subscribers.find { it.code == payment.code }
        if (subscriber == null) {
            val response = ApiResponse(
                404, "NOT FOUND", subscriber
            )
            return response
        }

        subscriber.debt -= payment.amount
        subscriber.debt = (subscriber.debt * 100).roundToInt() / 100.00

        val response = ApiResponse(
            200, "Success", subscriber
        )

        return response
    }
}