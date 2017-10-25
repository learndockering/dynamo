/*
   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package com.ocs.dynamo.envers.ui;

import com.ocs.dynamo.constants.DynamoConstants;
import com.ocs.dynamo.domain.AbstractEntity;
import com.ocs.dynamo.domain.model.EntityModel;
import com.ocs.dynamo.envers.domain.RevisionKey;
import com.ocs.dynamo.envers.domain.VersionedEntity;
import com.ocs.dynamo.service.BaseService;
import com.ocs.dynamo.service.MessageService;
import com.ocs.dynamo.ui.ServiceLocator;
import com.ocs.dynamo.ui.composite.dialog.BaseModalDialog;
import com.ocs.dynamo.ui.composite.layout.FormOptions;
import com.ocs.dynamo.ui.composite.layout.ServiceBasedSplitLayout;
import com.ocs.dynamo.ui.composite.type.AttributeGroupMode;
import com.ocs.dynamo.ui.composite.type.ScreenMode;
import com.ocs.dynamo.ui.container.QueryType;
import com.ocs.dynamo.ui.utils.VaadinUtils;
import com.vaadin.data.Container.Filter;
import com.vaadin.data.util.filter.Compare;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;

/**
 * 
 * @author bas.rutten
 *
 */
public class ViewRevisionDialog<ID, T extends AbstractEntity<ID>, U extends VersionedEntity<ID, T>>
		extends BaseModalDialog {

	private static final long serialVersionUID = -8950374678949377884L;

	private ServiceBasedSplitLayout<RevisionKey<ID>, U> layout;

	private BaseService<RevisionKey<ID>, U> service;

	private EntityModel<U> entityModel;

	private MessageService messageService = ServiceLocator.getMessageService();

	private ID id;

	/**
	 * Constructor
	 * 
	 * @param service
	 * @param entityModel
	 * @param id
	 */
	public ViewRevisionDialog(BaseService<RevisionKey<ID>, U> service, EntityModel<U> entityModel, ID id) {
		this.service = service;
		this.entityModel = entityModel;
		this.id = id;
	}

	@Override
	protected void doBuild(Layout parent) {
		FormOptions fo = new FormOptions().setReadOnly(true).setScreenMode(ScreenMode.VERTICAL)
				.setAttributeGroupMode(AttributeGroupMode.TABSHEET);
		layout = new ServiceBasedSplitLayout<RevisionKey<ID>, U>(service, entityModel, QueryType.PAGING, fo, null) {

			private static final long serialVersionUID = -5302678717934028964L;

			@Override
			protected Filter constructFilter() {
				// always filter on
				return new Compare.Equal(DynamoConstants.ID, id);
			}
		};
		layout.setPageLength(5);
		parent.addComponent(layout);
	}

	@Override
	protected void doBuildButtonBar(HorizontalLayout buttonBar) {
		Button closeButton = new Button(messageService.getMessage("ocs.close", VaadinUtils.getLocale()));
		closeButton.addClickListener(e -> close());
		buttonBar.addComponent(closeButton);
	}

	@Override
	protected String getTitle() {
		return messageService.getMessage("ocs.revision.history", VaadinUtils.getLocale());
	}

}