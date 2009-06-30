/*
 * Copyright 2008 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.gwtent.showcase.client;

import com.google.gwt.i18n.client.Constants;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TabListener;
import com.google.gwt.user.client.ui.Widget;

/**
 * <p>
 * A widget used to show GWT examples in the ContentPanel. It includes a tab bar
 * with options to view the example, view the source, or view the css style
 * rules.
 * </p>
 * <p>
 * This {@link Widget} uses a lazy initialization mechanism so that the content
 * is not rendered until the onInitialize method is called, which happens the
 * first time the {@link Widget} is added to the page.. The data in the source
 * and css tabs are loaded using an RPC call to the server.
 * </p>
 * <h3>CSS Style Rules</h3>
 * <ul class="css">
 * <li>.sc-ContentWidget { Applied to the entire widget }</li>
 * <li>.sc-ContentWidget-tabBar { Applied to the TabBar }</li>
 * <li>.sc-ContentWidget-deckPanel { Applied to the DeckPanel }</li>
 * <li>.sc-ContentWidget-name { Applied to the name }</li>
 * <li>.sc-ContentWidget-description { Applied to the description }</li>
 * </ul>
 */
public abstract class ContentWidget extends Composite implements TabListener {
  /**
   * The constants used in this Content Widget.
   */
  public static interface CwConstants extends Constants {
    String contentWidgetExample();

    String contentWidgetSource();

    String contentWidgetStyle();
  }


  /**
   * Constructor.
   * 
   * @param constants the constants
   */
  public ContentWidget(CwConstants constants) {

  }

}
