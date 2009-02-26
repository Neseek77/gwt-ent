package com.gwtent.client.template;

/**
 * Event Types
 * Copy from com.google.gwt.user.client.Event
 * @author James Luo (JamesLuo.au@gmail.com)
 *
 */
public enum HTMLEventTypes {
  
  /**
   * Fired when an element loses keyboard focus.
   */
  ONBLUR(0x01000),

  /**
   * Fired when the value of an input element changes.
   */
  ONCHANGE(0x00400),

  /**
   * Fired when the user clicks on an element.
   */
  ONCLICK(0x00001),

  /**
   * Fired when the user double-clicks on an element.
   */
  ONDBLCLICK(0x00002),

  /**
   * Fired when an image encounters an error.
   */
  ONERROR(0x10000),

  /**
   * Fired when an element receives keyboard focus.
   */
  ONFOCUS(0x00800),

  /**
   * Fired when the user depresses a key.
   */
  ONKEYDOWN(0x00080),

  /**
   * Fired when the a character is generated from a keypress (either directly or
   * through auto-repeat).
   */
  ONKEYPRESS(0x00100),

  /**
   * Fired when the user releases a key.
   */
  ONKEYUP(0x00200),

  /**
   * Fired when an element (normally an IMG) finishes loading.
   */
  ONLOAD(0x08000),

  /**
   * Fired when an element that has mouse capture loses it.
   */
  ONLOSECAPTURE(0x02000),

  /**
   * Fired when the user depresses a mouse button over an element.
   */
  ONMOUSEDOWN(0x00004),

  /**
   * Fired when the mouse is moved within an element's area.
   */
  ONMOUSEMOVE(0x00040),

  /**
   * Fired when the mouse is moved out of an element's area.
   */
  ONMOUSEOUT(0x00020),

  /**
   * Fired when the mouse is moved into an element's area.
   */
  ONMOUSEOVER(0x00010),

  /**
   * Fired when the user releases a mouse button over an element.
   */
  ONMOUSEUP(0x00008),

  /**
   * Fired when the user scrolls the mouse wheel over an element.
   */
  ONMOUSEWHEEL(0x20000),

  /**
   * Fired when a scrollable element's scroll offset changes.
   */
  ONSCROLL(0x04000),

  /**
   * Fired when the user requests an element's context menu (usually by right-clicking).
   * 
   * Note that not all browsers will fire this event (notably Opera, as of 9.5).
   */
  ONCONTEXTMENU(0x40000);
  
  private int event;
  
  private HTMLEventTypes(int event){
   this.event = event;
  }
  
  public int getEvent(){
   return event;
  }
}
