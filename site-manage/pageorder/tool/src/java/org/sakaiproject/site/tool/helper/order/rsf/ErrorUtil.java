/**
 * Copyright (c) 2003-2016 The Apereo Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *             http://opensource.org/licenses/ecl2
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/*
 * Created on 11 Sep 2008
 */
package org.sakaiproject.site.tool.helper.order.rsf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.org.ponder.rsf.components.UIBranchContainer;
import uk.org.ponder.rsf.components.UIContainer;
import uk.org.ponder.rsf.components.UIOutput;

public class ErrorUtil {
  private static Logger M_log = LoggerFactory.getLogger(PageListProducer.class);
  
    public static final void renderError(UIContainer tofill, Exception e) {
        UIBranchContainer mode = UIBranchContainer.make(tofill, "mode-failed:");
        UIOutput.make(mode, "message", e.getLocalizedMessage());
        
        M_log.warn(e.getMessage());
    }
}
