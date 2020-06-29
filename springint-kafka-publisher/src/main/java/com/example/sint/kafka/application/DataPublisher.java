package com.example.sint.kafka.application;

import com.example.sint.kafka.transaction.Transaction;
import com.example.sint.kafka.transaction.Transaction.Type;
import org.springframework.stereotype.Component;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class DataPublisher {
    private long nextId;

    public DataPublisher(){
        this.nextId = 1001l;
    }

    public List<Transaction>  getTransactions(){
        List<Transaction> transactions = new ArrayList<Transaction> ();
        transactions.add(createCreditTransaction());
        transactions.add(createCreditTransaction());
        transactions.add(createCreditTransaction());
        transactions.add(createCreditTransaction());
        transactions.add(createCreditTransaction());
        transactions.add(createDebitTransaction());
        transactions.add(createDebitTransaction());
        transactions.add(createDebitTransaction());
        transactions.add(createDebitTransaction());
        transactions.add(createDebitTransaction());
        transactions.add(createRefundTransaction());
        transactions.add(createRefundTransaction());
        transactions.add(createRefundTransaction());
        transactions.add(createRefundTransaction());
        transactions.add(createRefundTransaction());
        transactions.add(createVoidTransaction());
        transactions.add(createVoidTransaction());
        transactions.add(createVoidTransaction());
        transactions.add(createVoidTransaction());
        transactions.add(createVoidTransaction());

        return transactions;
    }

    public Transaction createCreditTransaction(){
        return createTransaction(Type.Credit);
    }

    public Transaction createDebitTransaction(){
        return createTransaction(Type.Debit);
    }

    public Transaction createRefundTransaction(){
        return createTransaction(Type.Refund);
    }

    public Transaction createVoidTransaction(){
        return createTransaction(Type.Void);
    }

    Transaction createTransaction(Type type) {
        Transaction transaction = new Transaction();
        transaction.setTransactionId(nextId++);
        transaction.setTransactionDate(new Date());
        transaction.setTransactionDesc("Transaction is "+type.name());
        transaction.setTransactionType(type);

        return transaction;
    }
}
