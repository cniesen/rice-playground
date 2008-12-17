
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


public abstract class ANSISqlPlatform implements Platform 
{
    /**
     * @see org.kuali.rice.core.database.platform.Platform#getTruncateTableSql(String)
     */
    public String getTruncateTableSql(String tableName) 
    {
        return "truncate table " + tableName;
    }
    
    /**
     * @see org.kuali.rice.core.database.platform.Platform#getCreateTableFromTableSql(String, String)
     */
    public String getCreateTableFromTableSql(String tableToCreate, String fromTable) 
     {
        return new StringBuffer("create table ").append(tableToCreate)
                .append(" as select * from ").append(fromTable).toString();
    }
    
    /**
     * @see org.kuali.rice.core.database.platform.Platform#getInsertDataFromTableSql(String, String)
     */
    public String getInsertDataFromTableSql(String restoreTableName, String fromTableName) 
    {
        return new StringBuffer("insert into ").append(restoreTableName)
                .append(" select * from ").append(fromTableName).toString();
    }
    
    /**
     * @see org.kuali.rice.core.database.platform.Platform#getDropTableSql(String)
     */
    public String getDropTableSql(String tableName) {
    	return new StringBuffer("drop table ").append(tableName).toString();
    }
    
    /**
     * Returns an expression equivalent to oracle's NVL statement using the CASE and IS NULL expressions, which should
     * be supported by many database systems
     * 
     * @see org.kuali.rice.core.database.platform.Platform#getIsNullFunction(java.lang.String, java.lang.String)
     */
    public String getIsNullFunction(String exprToTest, String exprToReplaceIfTestExprNull) {
    	return new StringBuilder(" case when ").append(exprToTest).append(" is null then ").append(exprToReplaceIfTestExprNull)
        .append(" else ").append(exprToTest).append(" end ").toString();
    }
    
    public String getDateSQL(String date, String time) {
        // SQL 92 date literal syntax:
        // http://www.stanford.du/dept/itss/docs/oracle/9i/java.920/a96654/ref.htm#1005145
        String d = date.replace('/', '-');
        if (time == null) {
            return "{d '" + d + "'}";    
        } else {
            return "{ts '" + d + " " + time + "'}"; 
        }
    }
    
    /**
     * @see org.kuali.rice.core.database.platform.Platform#getUpperCaseFunction()
     * @return the String "UPPER"
     */
    //chb: this was copied over from the legacy code, but is it really necessary?
    public String getUpperCaseFunction() {
    	return "UPPER";
    }

    public String toString() {
        return "[ANSISqlPlatform]";
    }
    
    
}