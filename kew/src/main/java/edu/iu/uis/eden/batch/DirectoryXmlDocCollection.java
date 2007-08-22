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
package edu.iu.uis.eden.batch;

import java.io.File;
import java.io.FileFilter;

/**
 * An XmlDocCollection backed by a directory of XML files
 * @author Aaron Hamid (arh14 at cornell dot edu)
 */
public class DirectoryXmlDocCollection extends BaseXmlDocCollection {
    private static class DirectoryXmlDocsCollectionFileFilter implements FileFilter {
        public boolean accept(File file) {
            return file.isFile() && file.getName().toLowerCase().endsWith(".xml");
        }
    }
    private static final DirectoryXmlDocsCollectionFileFilter FILTER = new DirectoryXmlDocsCollectionFileFilter();

    public DirectoryXmlDocCollection(File dir) {
        super(dir);
        File[] xmlDataFiles = file.listFiles(FILTER);
        if (xmlDataFiles != null) {
            for (int i = 0; i < xmlDataFiles.length; i++) {
                xmlDocs.add(new FileXmlDoc(xmlDataFiles[i], this));
            }
        }
    }
}