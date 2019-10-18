package ch.keepcalm.account.api

import org.axonframework.messaging.responsetypes.ResponseTypes
import org.axonframework.queryhandling.QueryGateway
import org.springframework.hateoas.MediaTypes
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.CompletableFuture

@RestController
@RequestMapping("/query")
class BankAccountQueryController(private val queryGateway: QueryGateway) {

    @GetMapping("/accounts")
    fun accounts(): CompletableFuture<List<BankAccountView>> {
        return queryGateway.query(FindAllBankAccountsQuery(), ResponseTypes.multipleInstancesOf(BankAccountView::class.java))
    }
}
