package com.cardManagement.cardmanagementapp.service;

import java.util.List;

import com.cardManagement.cardmanagementapp.entities.Statement;
import com.cardManagement.cardmanagementapp.exceptions.PaymentTransactionException;
import com.cardManagement.cardmanagementapp.exceptions.StatementException;

public interface StatementService {

	Statement getStatementbyStatementId(Integer statementId) throws StatementException;

	Statement addStatement(Long creditcardNumber) throws StatementException, PaymentTransactionException;

	Statement updateStatement(Statement newStatement) throws StatementException;

	Statement interestCalculation(Integer statementId, Double amount) throws StatementException;

	List<Statement> getAllStatements();

	List<Statement> getStatementsByCard(Long creditcardNumber) throws StatementException, PaymentTransactionException;

}
