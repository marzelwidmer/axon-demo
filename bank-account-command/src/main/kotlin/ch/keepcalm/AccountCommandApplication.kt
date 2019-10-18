package ch.keepcalm

import ch.keepcalm.account.api.BankAccountCommandController
import ch.keepcalm.account.api.Money
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.hateoas.Link
import org.springframework.hateoas.MediaTypes
import org.springframework.hateoas.RepresentationModel
import org.springframework.hateoas.UriTemplate
import org.springframework.hateoas.server.mvc.BasicLinkBuilder
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn
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
class IndexController : RepresentationModel<IndexController>() {

    @GetMapping
    fun api(): RepresentationModel<IndexController> = RepresentationModel<IndexController>()
            .apply {
                add(linkTo(methodOn(BankAccountCommandController::class.java).createBankAccount(money = Money())).withRel("bank-account"))
                add(Link(UriTemplate.of("${BasicLinkBuilder.linkToCurrentMapping()}/accounts/{id}/withdrawal"), "withdraw-money"))
                add(Link(UriTemplate.of("${BasicLinkBuilder.linkToCurrentMapping()}/accounts/{id}/deposit"), "deposit-money"))
            }
}


