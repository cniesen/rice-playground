/*
 * Copyright 2007-2010 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.rice.kns.bo;


import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.kuali.test.KNSTestCase;

/**
 * This is a description of what this class does - chang don't forget to fill this in. 
 * 
 * @author Kuali Rice Team (rice.collab@kuali.org)
 *
 */
public class DocumentAttachmentTest extends KNSTestCase{

	DocumentAttachment dummyDocumentAttachment;
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
		dummyDocumentAttachment = new DocumentAttachment();
	}

	/**
	 * This method ...
	 * 
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		super.tearDown();
		dummyDocumentAttachment = null;
	}
	
	@Test
	public void testDocumentNumber(){
		dummyDocumentAttachment.setDocumentNumber("c122");
		assertEquals("Testing DocumentNumber in DocumnetAttchment", "c122", dummyDocumentAttachment.getDocumentNumber());
	}
	@Test
	public void testToStringMapper(){
		PersistableAttachmentBase dummy = new DocumentAttachment();
		dummy.setFileName("cs101");
		dummy.setContentType("txt");
		((DocumentAttachment)dummy).setDocumentNumber("001");
		
		HashMap resultHashMap = dummy.toStringMapper();
		assertEquals("Testing toStringMapper in DocumentAttachment","cs101",resultHashMap.get("fileName") );
		assertEquals("Testing toStringMapper in DocumentAttachment","001",resultHashMap.get("documentNumber") );
		assertEquals("Testing toStringMapper in DocumentAttachment","txt",resultHashMap.get("contentType") );
	}
}