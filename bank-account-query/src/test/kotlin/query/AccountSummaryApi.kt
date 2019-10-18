//package ch.keepcalm.account
//
//import ch.keepcalm.account.query.AccountSummary
//import ch.keepcalm.account.query.AccountSummaryQuery
//import org.axonframework.messaging.responsetypes.ResponseTypes
//import org.axonframework.queryhandling.QueryGateway
//import org.slf4j.LoggerFactory
//import org.springframework.stereotype.Service
//import org.springframework.web.bind.annotation.GetMapping
//import org.springframework.web.bind.annotation.PathVariable
//import org.springframework.web.bind.annotation.RequestMapping
//import org.springframework.web.bind.annotation.RestController
//import java.util.*
//
////      _                             _      _          _
////     / \   ___ ___ ___  _   _ _ __ | |_   / \   _ __ (_)
////    / _ \ / __/ __/ _ \| | | | '_ \| __| / _ \ | '_ \| |
////   / ___ \ (__ (__ (_) | |_| | | | | |_ / ___ \| |_) | |
////  /_/   \_\___\___\___/ \__,_|_| |_|\__/_/   \_\ .__/|_|
////                                               |_|
//@RequestMapping(path = ["/api/accounts/command"])
//@RestController
//class AccountSummaryApi(private val accountSummaryService: AccountSummaryService) {
//
//    @GetMapping(path = ["/readAccountSummary/{id}"])
//    fun readAccountSummary(@PathVariable id: String) = accountSummaryService.queryAccountSummary(id)
//}
//
//@Service
//class AccountSummaryService(private val queryGateway: QueryGateway) {
//
//    private val log = LoggerFactory.getLogger(AccountSummaryService::class.java)
//
//    fun queryAccountSummary(id: String): AccountSummary? {
//        log.debug("quering")
//        val accountSummary= queryGateway.query(AccountSummaryQuery(UUID.fromString(id)),
//                ResponseTypes.instanceOf(AccountSummary::class.java)).join()
//        log.debug("summary queried {} ", accountSummary)
//
//        return accountSummary
//    }
//}
//
