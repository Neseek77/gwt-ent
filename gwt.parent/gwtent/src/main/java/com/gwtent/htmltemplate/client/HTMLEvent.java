package com.gwtent.htmltemplate.client;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The HTMLEvent
 * The event handler should looks like the following:
 * public void onButton1ClickEvent(Event event);
 * public void onButton1ClickEvent();
 * 
 * @author James Luo (JamesLuo.au@gmail.com)
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface HTMLEvent {
  /**
   * The DOM ID list of this event service to
   * TODO: Guess DOM ID from event handle name
   */
  String[] value();
  
  
  /**
   * Event type, default ONCLICK
   */
  HTMLEventTypes eventType() default HTMLEventTypes.ONCLICK;
}
