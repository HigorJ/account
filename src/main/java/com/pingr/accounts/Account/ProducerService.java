package com.pingr.accounts.Account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProducerService {
    @Value(value = "${topic.accounts.account-created}")
    private String topicAccountCreated;

    @Value(value = "${topic.accounts.account-updated}")
    private String topicAccountUpdated;

    @Value(value = "${topic.accounts.account-deleted}")
    private String topicAccountDeleted;

    @Autowired
    private KafkaTemplate<String, Object> template;

    public void accountCreated(Account account) {
        this.template.send(this.topicAccountCreated, account);
    }

    public void accountUpdated(Account account) {
        this.template.send(this.topicAccountUpdated, account);
    }

    public void accountDeleted(Account account) {
        this.template.send(this.topicAccountDeleted, account);
    }
}

