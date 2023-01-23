package com.maveric.transactionservice.model;

import com.maveric.transactionservice.constant.Type;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collation = "transaction")
public class Transaction {

    @Id
    private String _id;

    private String accountId;

    private Number amount;

    @Enumerated(EnumType.STRING)
    private Type type;

    private Date createdAt = new Date();
}
