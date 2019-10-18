package ch.keepcalm

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.hateoas.MediaTypes
import org.springframework.hateoas.RepresentationModel
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@SpringBootApplication
class AccountQueryApplication

fun main(args: Array<String>) {
    runApplication<AccountQueryApplication>(*args)
}


@RestController
@RequestMapping("/", produces = [MediaTypes.HAL_JSON_VALUE])
class IndexController : RepresentationModel<IndexController>() {

    @GetMapping
    fun api(): RepresentationModel<IndexController> {
        return RepresentationModel<IndexController>().apply {
            add(linkTo(methodOn(IndexController::class.java).api()).withSelfRel())
        }
    }
}

