package com.gwtent.client.pagebus;

public interface SubscriptionCallback {

  void execute(String subject, Object message);
}
