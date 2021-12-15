package com.db.awmd.challenge.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Entity
	@Table(name = "TransactionInfo")
	public class TransactionInfo {
	    @Id
	    @GeneratedValue(strategy=GenerationType.AUTO)
	    private Long transactionId;

	    private String fromAccountNumber;
	    
	    private String toAccountNumber;

	    private BigDecimal transactionAmount;

	    private Timestamp transactionDateTime;
	    
	    
	    
}
