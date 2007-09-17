/*
 * Copyright 2005-2006 The Kuali Foundation.
 * 
 * 
 * Licensed under the Educational Community License, Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.opensource.org/licenses/ecl1.php
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS
 * IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 */
package edu.iu.uis.eden.docsearch;

import java.sql.ResultSet;
import java.sql.SQLException;

import edu.iu.uis.eden.routeheader.DocumentRouteHeaderValue;

/**
 * Model bean mapped to ojb that holds a single piece of searchable data for a document.
 * 
 * @author bh79
 * @author delyea
 * 
 */
public interface SearchableAttributeValue {

    public String getAttributeDataType();

    public String getAttributeTableName();

    public boolean allowsWildcards();

    public boolean allowsCaseInsensitivity();

    public boolean allowsRangeSearches();

    public boolean isPassesDefaultValidation(String valueEntered);

    public Boolean isRangeValid(String lowerValue, String upperValue);

    public void setupAttributeValue(String value);

    public void setupAttributeValue(ResultSet resultSet, String columnName) throws SQLException;

    public String getSearchableAttributeDisplayValue();

    public String getOjbConcreteClass();

    public void setOjbConcreteClass(String ojbConcreteClass);

    public DocumentRouteHeaderValue getRouteHeader();

    public void setRouteHeader(DocumentRouteHeaderValue routeHeader);

    public Long getRouteHeaderId();

    public void setRouteHeaderId(Long routeHeaderId);

    public String getSearchableAttributeKey();

    public void setSearchableAttributeKey(String searchableAttributeKey);

    public Long getSearchableAttributeValueId();

    public void setSearchableAttributeValueId(Long searchableAttributeValueId);

    public Object getSearchableAttributeValue();
}
