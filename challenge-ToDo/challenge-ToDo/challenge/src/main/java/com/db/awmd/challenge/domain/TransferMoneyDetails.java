package com.db.awmd.challenge.domain;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class TransferMoneyDetails {

	
	@NotNull
	@NotEmpty
	private final String fromAccountNumber;

	@NotNull
	@NotEmpty
    private final String toAccountNumber;

	@NotNull
	@Min(value = 0, message = "Initial balance must be positive.")
    private BigDecimal amount;
	
	@JsonCreator
	  public TransferMoneyDetails(@JsonProperty("fromAccountNumber") String fromAccountNumber,
	    @JsonProperty("toAccountNumber") String toAccountNumber, @JsonProperty("amount") BigDecimal amount ) {
	    this.fromAccountNumber = fromAccountNumber;
	    this.toAccountNumber = toAccountNumber;
	    this.amount = amount;
	  }

}
