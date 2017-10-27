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
package com.ocs.dynamo.ui.component;

import java.io.Serializable;

import com.ocs.dynamo.domain.AbstractEntity;
import com.ocs.dynamo.domain.model.AttributeModel;
import com.ocs.dynamo.domain.model.EntityModel;
import com.ocs.dynamo.service.BaseService;
import com.ocs.dynamo.ui.BaseUI;
import com.ocs.dynamo.ui.utils.VaadinUtils;
import com.vaadin.data.Container.Filter;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;

/**
 * Base class for components that display an Entity or collection of entities and that allow the
 * user to easily add new values on the fly
 * 
 * @author bas.rutten
 *
 * @param <ID>
 *            the type of the primary key
 * @param <T>
 *            the type of the entity
 * @param <U>
 *            the type of the value of the component (can be an entity, or a collection of entities)
 */
public abstract class QuickAddEntityField<ID extends Serializable, T extends AbstractEntity<ID>, U>
        extends CustomEntityField<ID, T, U> {

    private static final long serialVersionUID = 7118578276952170818L;

    private UI ui = UI.getCurrent();

    /**
     * The button that brings up the dialog for adding a new entity
     */
    private Button addButton;

    /**
     * The button that navigates directly to detailscreen of the selected entity
     */
    private Button directNavigationButton;

    /**
     * Additional filter for cascading
     */
    private Filter additionalFilter;

    /**
     * Constructor
     * 
     * @param service
     *            the service used to interact with the storage
     * @param entityModel
     *            the entity model of the entity that is being displayed
     * @param attributeModel
     *            the attribute model
     * @param filter
     *            the search filter
     */
    public QuickAddEntityField(BaseService<ID, T> service, EntityModel<T> entityModel, AttributeModel attributeModel,
            Filter filter) {
        super(service, entityModel, attributeModel, filter);
    }

    /**
     * Method that is called after a new entity has been successfully created. Use this to add the
     * new entity to the component and select it
     * 
     * @param entity
     */
    protected abstract void afterNewEntityAdded(T entity);

    /**
     * Constructs the button that brings up the dialog that allows the user to add a new item
     * 
     * @return
     */
    protected Button constructAddButton() {
        addButton = new Button(getMessageService().getMessage("ocs.add", VaadinUtils.getLocale()));
        addButton.addClickListener(event -> {
            AddNewValueDialog<ID, T> dialog = new AddNewValueDialog<ID, T>(getEntityModel(), getAttributeModel(),
                    getService(), getMessageService()) {

                private static final long serialVersionUID = 2040216794358094524L;

                @Override
                protected void afterNewEntityAdded(T entity) {
                    QuickAddEntityField.this.afterNewEntityAdded(entity);
                }

            };
            dialog.build();
            ui.addWindow(dialog);
        });
        return addButton;
    }

    /**
     * Constructs the button that navigates directly to
     *
     * @return
     */
    protected Button constructDirectNavigationButton() {
        directNavigationButton = new Button(
                getMessageService().getMessage("ocs.direct.navigate", VaadinUtils.getLocale()));
        directNavigationButton.addClickListener(event -> ((BaseUI) ui).navigateToEntityScreenDirectly(getValue()));
        return directNavigationButton;
    }

    public Button getAddButton() {
        return addButton;
    }

    public Button getDirectNavigationButton() {
        return directNavigationButton;
    }

    @Override
    public Filter getAdditionalFilter() {
        return additionalFilter;
    }

    public UI getUi() {
        return ui;
    }

    @Override
    public void setAdditionalFilter(Filter additionalFilter) {
        this.additionalFilter = additionalFilter;
    }

    @Override
    public void clearAdditionalFilter() {
        this.additionalFilter = null;

    }
}