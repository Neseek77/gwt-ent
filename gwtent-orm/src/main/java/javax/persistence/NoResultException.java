/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

//
// This source code implements specifications defined by the Java
// Community Process. In order to remain compliant with the specification
// DO NOT add / change / or delete method signatures!
//
package javax.persistence;

/**
 * @version $Rev: 489260 $ $Date: 2006-12-21 15:15:07 +1100 (Thu, 21 Dec 2006) $
 */
public class NoResultException extends PersistenceException {

    /**
     * 
     */
    private static final long serialVersionUID = 4123871304568481301L;

    public NoResultException() {
        super();
    }

    public NoResultException(String message) {
        super(message);
    }

}
