package ch.keepcalm.account

import ch.keepcalm.account.command.CreateDepositCommand
import ch.keepcalm.account.command.WithdrawCommand
import ch.keepcalm.account.event.DepositMadeEvent
import ch.keepcalm.account.event.WithdrawnEvent
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.spring.stereotype.Aggregate
import org.slf4j.LoggerFactory
import org.springframework.util.Assert
import java.io.Serializable
import java.util.*


@Aggregate
class AccountAggregate : Serializable {

    private val log = LoggerFactory.getLogger(AccountAggregate::class.java)

    @AggregateIdentifier
    private var id: UUID? = null

    private var remainingValue: Int = 0

    constructor() // Need for Axon

    //       _                      _ _   
    //    __| | ___ _ __   ___  ___(_) |_ 
    //   / _` |/ _ \ '_ \ / _ \/ __| | __|
    //  | (_| |  __/ |_) | (_) \__ \ | |_ 
    //   \__,_|\___| .__/ \___/|___/_|\__|
    //             |_|

    // Command Handler
    @CommandHandler
    constructor(command: CreateDepositCommand) {
        log.debug("handling {}")
        if (command.amount <= 0) throw IllegalArgumentException("The 'amount' <= 0 is an invalid value.")
        AggregateLifecycle.apply(DepositMadeEvent(id = command.id, amount = command.amount))
    }

    // EventSourcing Handler
    @EventSourcingHandler
    fun on(event: DepositMadeEvent) {
        log.debug("applying {}", event)
        id = event.id
        remainingValue = event.amount
    }


    //            _ _   _         _
    //  __      ___) |_| |__   __| |_ __ __ ___      __
    //  \ \ /\ / / | __| '_ \ / _` | '__/ _` \ \ /\ / /
    //   \ V  V /| | |_| | | | (_| | | | (_| |\ V  V /
    //    \_/\_/ |_|\__|_| |_|\__,_|_|  \__,_| \_/\_/
    //

    // Command Handler
    @CommandHandler
    fun handle(command: WithdrawCommand) {
        log.debug("handling {}")
        Assert.hasLength(id.toString(), "Missing id")
        if (command.amount <= 0) throw IllegalArgumentException("The 'amount' <= 0")
        if (command.amount > remainingValue) throw IllegalArgumentException("The 'amount' > 'remaining'")
        AggregateLifecycle.apply(WithdrawnEvent(id = command.id, amount = command.amount))
    }

    // EventSourcing Handler
    @EventSourcingHandler
    fun on(event: WithdrawnEvent) {
        log.debug("applying {}", event)
        remainingValue = remainingValue.minus(event.amount)
    }

}