/**********************************************************************************
 * $URL: $
 * $Id:  $
 ***********************************************************************************
 *
 * Copyright (c) 2006,2007 The Sakai Foundation.
 * 
 * Licensed under the Educational Community License, Version 1.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at
 * 
 *      http://www.opensource.org/licenses/ecl1.php
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License.
 *
 **********************************************************************************/

package org.sakaiproject.poll.tool.producers;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import uk.org.ponder.messageutil.MessageLocator;
import uk.org.ponder.rsf.components.UIContainer;
import uk.org.ponder.rsf.components.UIELBinding;
import uk.org.ponder.rsf.components.UIOutput;
import uk.org.ponder.rsf.components.UIInternalLink;
import uk.org.ponder.rsf.components.UIForm;
import uk.org.ponder.rsf.components.UICommand;
import uk.org.ponder.rsf.components.UIVerbatim;

import uk.org.ponder.rsf.view.ComponentChecker;
import uk.org.ponder.rsf.view.ViewComponentProducer;
import uk.org.ponder.rsf.viewstate.ViewParameters;

import org.sakaiproject.poll.logic.PollListManager;
import org.sakaiproject.poll.model.VoteCollection;
import org.sakaiproject.poll.model.Poll;
import org.sakaiproject.poll.model.Option;
import org.sakaiproject.poll.tool.params.OptionViewParamaters;
import org.sakaiproject.poll.tool.params.VoteBean;

import uk.org.ponder.rsf.flow.jsfnav.NavigationCase;
import uk.org.ponder.rsf.flow.jsfnav.NavigationCaseReporter;
import uk.org.ponder.rsf.viewstate.ViewParamsReporter;
import uk.org.ponder.rsf.viewstate.SimpleViewParameters;
import uk.org.ponder.rsf.viewstate.EntityCentredViewParameters;
import uk.org.ponder.localeutil.LocaleGetter;
import uk.org.ponder.rsf.components.UIInput;
import uk.org.ponder.beanutil.entity.EntityID;

import java.util.List;
import java.util.ArrayList;


public class PollOptionDeleteProducer implements ViewComponentProducer,NavigationCaseReporter,ViewParamsReporter{
//,
	public static final String VIEW_ID = "pollOptionDelete";
	private static Log m_log = LogFactory.getLog(PollOptionDeleteProducer.class);
	private VoteBean voteBean;
	
	
	private MessageLocator messageLocator;
	private LocaleGetter localegetter;
	
	
	
	
	public String getViewID() {
		
		return VIEW_ID;
	}
	
	  public void setVoteBean(VoteBean vb){
		  this.voteBean = vb;
	  }

		
	  public void setMessageLocator(MessageLocator messageLocator) {
			  
		  this.messageLocator = messageLocator;
	  }

	  public void setLocaleGetter(LocaleGetter localegetter) {
		this.localegetter = localegetter;
	  }
	  
	  private PollListManager pollListManager;
	  public void setPollListManager(PollListManager p){
		  this.pollListManager = p;
	  }
	  
	public void fillComponents(UIContainer tofill, ViewParameters viewparams,
			ComponentChecker arg2) {
		m_log.debug("rendering view");
		Poll poll = null;
		
		UIOutput.make(tofill,"confirm_delete",messageLocator.getMessage("delete_confirm"));
		UIOutput.make(tofill,"error",messageLocator.getMessage("delete_option_message"));
		
		Option option = null;
		OptionViewParamaters aivp = (OptionViewParamaters) viewparams;
		if(aivp.id != null) {
			m_log.debug("got a paramater with id: " + new Long(aivp.id));
			// passed in an id so we should be modifying an item if we can find it
			option = pollListManager.getOptionById(new Long(aivp.id));
		} 

		UIVerbatim.make(tofill,"poll_text",option.getOptionText());
		UIForm form = UIForm.make(tofill,"opt-form");
		UIInput.make(form,"opt-text","#{option.optionText}",option.getOptionText());
		
		form.parameters.add(new UIELBinding("#{option.optionId}",
		           option.getOptionId()));
		form.parameters.add(new UIELBinding("#{option.id}",
		           option.getId()));
		form.parameters.add(new UIELBinding("#{option.pollId}",
		           option.getPollId()));
		 
		  UICommand saveAdd = UICommand.make(form, "submit-option-add", messageLocator.getMessage("delete_option_confirm"),
		  "#{pollToolBean.proccessActionDeleteOption}");
		  saveAdd.parameters.add(new UIELBinding("#{option.status}", "delete"));
		  
		  UICommand cancel = UICommand.make(form, "cancel",messageLocator.getMessage("new_poll_cancel"),"#{pollToolBean.cancel}");
		   cancel.parameters.add(new UIELBinding("#{option.status}", "cancel"));
		   
	}

	
	  public List reportNavigationCases() {
		    List togo = new ArrayList();
		    togo.add(new NavigationCase(null, new SimpleViewParameters(this.VIEW_ID)));
		    togo.add(new NavigationCase("success", new EntityCentredViewParameters(AddPollProducer.VIEW_ID, 
	    			new EntityID("Poll", "0"))));
		    togo.add(new NavigationCase("cancel", new EntityCentredViewParameters(AddPollProducer.VIEW_ID, 
	    			new EntityID("Poll", "0"))));
		    return togo;
		  }
	 
	  public ViewParameters getViewParameters() {
		  return new OptionViewParamaters();

	  }
	 
}
