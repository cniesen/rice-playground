/*
 * Copyright 2005-2006 The Kuali Foundation.
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
package edu.iu.uis.eden.plugin;

/**
 * An interface representing an Entity that can be reloaded.  Calls to reload should only be executed if
 * this Reloadable has flagged itself as reloadable according to the isReloadable() method.
 * 
 * <p>If invocations of isReloadable() return false then calls to reload() should be
 * no-ops.
 * 
 * @author ewestfal
 */
public interface Reloadable {

	/**
	 * Indicates whether or not this Reloadable currently supports being reloaded.
	 * If this method returns false then calls to reload() are effectively no-ops.
	 * 
	 * @return true if this Reloadable can be reloaded, false otherwise
	 */
    public boolean isReloadable();

    public boolean isReloadNeeded();
    
    /**
     * Reloads the Reloadable.  If isReloadable() returns false then this method
     * should be a no-op.
     */
    public void reload() throws Exception;
    
}
