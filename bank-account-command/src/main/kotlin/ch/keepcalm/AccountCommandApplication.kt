package ch.keepcalm

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.hateoas.IanaLinkRelations
import org.springframework.hateoas.MediaTypes
import org.springframework.hateoas.RepresentationModel
import org.springframework.hateoas.server.mvc.add
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@SpringBootApplication
class AccountCommandApplication

fun main(args: Array<String>) {
    runApplication<AccountCommandApplication>(*args)
}

@RestController
@RequestMapping("/", produces = [MediaTypes.HAL_JSON_VALUE])
class IndexController : RepresentationModel<IndexController>(){

    @GetMapping
    fun api(): IndexController = IndexController()
            .apply {
                add(IndexController::class) {
                    linkTo { api() } withRel IanaLinkRelations.SELF
                    linkTo { api() } withRel "commands" //TODO [marcelwidmer-16.10.19]: Def. commands links stuff
                }
            }
}


//@Component
//class AccountTestRunner(private val commandGateway: CommandGateway, private val queryGateway: QueryGateway) : CommandLineRunner {
//
//    private val log = LoggerFactory.getLogger(AccountTestRunner::class.java)
//
//    override fun run(vararg args: String?) {
//        val id = UUID.randomUUID()
//
//        log.debug("Sending loyaltyPoints command")
//        commandGateway.sendAndWait<Any>(DepositCommand(id = id, amount = 100))
//
//        log.debug("Sending redeem loyaltyPoints command")
//        commandGateway.sendAndWait<Any>(WithdrawCommand(id = id, amount = 40))
//
//        log.debug("Sending redeem command")
//        commandGateway.sendAndWait<Any>(WithdrawCommand(id = id, amount = 30))
//
//        Thread.sleep(500) // Wait a bit.
//        log.debug("quering")
//        log.debug("summary queried {} ", queryGateway.query(AccountSummaryQuery(id),
//                ResponseTypes.instanceOf(AccountSummary::class.java)).join())
//
//        log.debug("Sending redeem command")
//        commandGateway.sendAndWait<Any>(WithdrawCommand(id = id, amount = 30))
//
//        Thread.sleep(500) // Wait a bit.
//        log.debug("summary queried {} ", queryGateway.query(AccountSummaryQuery(id),
//                ResponseTypes.instanceOf(AccountSummary::class.java)).join())
//
//
//    }
//}