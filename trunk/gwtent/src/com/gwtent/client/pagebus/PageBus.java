package com.gwtent.client.pagebus;

import com.google.gwt.core.client.JavaScriptObject;

public class PageBus {


  public static native void publish(String subject, Object message)/*-{
        $wnd.PageBus.publish(subject, message);
    }-*/;

  public static native Subscription subscribe(String subject, SubscriptionCallback callback)/*-{
          var sub = $wnd.PageBus.subscribe(subject, this, function(subject, message, subscriberData){
        callback.@com.gwtent.client.pagebus.SubscriptionCallback::execute(Ljava/lang/String;Ljava/lang/Object;)(subject, message);
      }, null);
      return  @com.gwtent.client.pagebus.PageBus::createSubscription(Lcom/google/gwt/core/client/JavaScriptObject;)(sub);
  }-*/;

  private static Subscription createSubscription(JavaScriptObject sub) {
    return new Subscription(sub);
  }

  public static native void unsubscribe(Subscription subscription)/*-{
        var sub = subscription.@com.gwtent.client.pagebus.JsObject::jsObj;
    $wnd.PageBus.unsubscribe(sub);
  }-*/;
}