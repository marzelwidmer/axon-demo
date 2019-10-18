package ch.keepcalm.account.api

import org.springframework.data.jpa.repository.JpaRepository
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class BankAccountView(@Id val id: String, val balance: Int? = 0)

interface BankAccountViewRepository : JpaRepository<BankAccountView, String>
