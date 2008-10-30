/*
 * Copyright 2005-2007 The Kuali Foundation.
 * 
 * 
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.opensource.org/licenses/ecl1.php
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.rice.core.database.platform;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.persistence.EntityManager;

import org.apache.ojb.broker.PersistenceBroker;
import org.apache.ojb.broker.accesslayer.LookupException;

public class MySQLPlatform extends ANSISqlPlatform {

    public String getLockRouteHeaderQuerySQL(Long routeHeaderId, boolean wait) {
        return "SELECT DOC_HDR_ID FROM KREW_DOC_HDR_T WHERE DOC_HDR_ID=? FOR UPDATE";
    }

    public Long getNextValSQL(String sequenceName,	PersistenceBroker persistenceBroker) {
  		PreparedStatement statement = null;
  		ResultSet resultSet = null;
  		try {
  			Connection connection = persistenceBroker.serviceConnectionManager().getConnection();
  			statement = connection.prepareStatement("INSERT INTO " + sequenceName + " VALUES (NULL);");
  			statement.executeUpdate();
  			statement = connection.prepareStatement("SELECT LAST_INSERT_ID()");
  			resultSet = statement.executeQuery();

  			if (!resultSet.next()) {
  				throw new RuntimeException("Error retrieving next option id for action list from sequence.");
  			}
  			return new Long(resultSet.getLong(1));
  		} catch (SQLException e) {
  			throw new RuntimeException("Error retrieving next option id for action list from sequence.", e);
  		} catch (LookupException e) {
  			throw new RuntimeException("Error retrieving next option id for action list from sequence.", e);
  		} finally {
  			if (statement != null) {
  				try {
  					statement.close();
  				} catch (SQLException e) {
  				}
  			}
  			if (resultSet != null) {
  				try {
  					resultSet.close();
  				} catch (SQLException e) {
  				}
  			}
  		}
  	}
    
    public Long getNextValSQL(String sequenceName, EntityManager entityManager) {
        entityManager.createNativeQuery("INSERT INTO " + sequenceName + " VALUES (NULL);").executeUpdate();
        return new Long(((BigInteger) entityManager.createNativeQuery("SELECT LAST_INSERT_ID()").getSingleResult()).longValue());           
    }

    public boolean isSITCacheSupported() {
    	return false;
    }

    public String toString() {
        return "[MySQLPlatform]";
    }
    
    public String getSelectForUpdateSuffix(long waitMillis) {
        return "for update";
    }

}
