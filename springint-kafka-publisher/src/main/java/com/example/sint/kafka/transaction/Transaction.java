package com.example.sint.kafka.transaction;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;


@NoArgsConstructor
@ToString
public class Transaction {

    public enum Type {Credit, Refund, Debit, Void};

    private long transactionId;
    private String transactionDesc;
    private Date transactionDate;
    private Type transactionType;

    public long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionDesc() {
        return transactionDesc;
    }

    public void setTransactionDesc(String transactionDesc) {
        this.transactionDesc = transactionDesc;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Type getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(Type transactionType) {
        this.transactionType = transactionType;
    }
}
