package com.ocs.dynamo.ui.composite.layout;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.ocs.dynamo.domain.TestEntity;
import com.ocs.dynamo.domain.model.EntityModelFactory;
import com.ocs.dynamo.service.TestEntityService;
import com.ocs.dynamo.test.BaseIntegrationTest;
import com.ocs.dynamo.ui.composite.form.FormOptions;
import com.ocs.dynamo.ui.container.QueryType;
import com.ocs.dynamo.ui.utils.VaadinUtils;
import com.vaadin.data.Container.Filter;
import com.vaadin.data.sort.SortOrder;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.ui.Table;

public class SimpleSearchLayoutTest extends BaseIntegrationTest {

	@Inject
	private EntityModelFactory entityModelFactory;

	@Inject
	private TestEntityService testEntityService;

	private TestEntity e1;

	@Before
	public void setup() {
		e1 = new TestEntity("Bob", 11L);
		e1 = testEntityService.save(e1);

		TestEntity e2 = new TestEntity("Kevin", 12L);
		e2 = testEntityService.save(e2);

		TestEntity e3 = new TestEntity("Stewart", 13L);
		e3 = testEntityService.save(e3);
	}

	@Test
	public void testSimpleSearchLayout() {
		SimpleSearchLayout<Integer, TestEntity> layout = createLayout(new FormOptions());
		layout.build();

		Assert.assertTrue(layout.getAddButton().isVisible());
		Assert.assertTrue(layout.getEditButton().isVisible());
		Assert.assertFalse(layout.getRemoveButton().isVisible());

		Assert.assertEquals(new SortOrder("name", SortDirection.ASCENDING), layout.getSortOrders().get(0));

		TestEntity entity = layout.createEntity();
		Assert.assertNotNull(entity);

		Table table = layout.getTableWrapper().getTable();
		Assert.assertEquals(3, table.size());

		Object id = table.getItemIds().iterator().next();
		TestEntity t = (TestEntity) VaadinUtils.getEntityFromContainer(table.getContainerDataSource(), id);

		layout.detailsMode(t);
	}

	@Test
	public void testSimpleSearchLayout_AddButton() {
		SimpleSearchLayout<Integer, TestEntity> layout = createLayout(new FormOptions());
		layout.build();

		// click the add button and verify that a new item is added
		layout.getAddButton().click();
		Assert.assertNotNull(layout.getSelectedItem());
	}

	@Test
	public void testSimpleSearchLayout_EditButton() {
		FormOptions options = new FormOptions();
		options.setShowEditButton(true);

		SimpleSearchLayout<Integer, TestEntity> layout = createLayout(options);
		layout.build();

		Assert.assertTrue(layout.getEditButton().isVisible());

		// click the add button and verify that a new item is added
		layout.setSelectedItem(e1);
		layout.getEditButton().click();
		Assert.assertEquals(e1, layout.getSelectedItem());
	}

	/**
	 * Test the user of a filter
	 */
	@Test
	public void testSimpleSearchLayout_Filter() {
		SimpleSearchLayout<Integer, TestEntity> layout = createLayout(new FormOptions());

		List<Filter> filters = new ArrayList<>();
		filters.add(new com.vaadin.data.util.filter.Compare.Equal("name", "Bob"));

		layout.setDefaultFilters(filters);
		layout.build();

		Assert.assertEquals(new SortOrder("name", SortDirection.ASCENDING), layout.getSortOrders().get(0));

		Table table = layout.getTableWrapper().getTable();

		Assert.assertEquals(1, table.size());
	}

	/**
	 * Test the selection of an item (single item)
	 */
	@Test
	public void testSimpleSearchLayout_Select() {
		SimpleSearchLayout<Integer, TestEntity> layout = createLayout(new FormOptions());
		layout.build();

		Integer id = e1.getId();
		layout.select(id);

		Assert.assertEquals(e1, layout.getSelectedItem());
	}

	@Test
	public void testSimpleSearchLayout_SelectCollection() {
		SimpleSearchLayout<Integer, TestEntity> layout = createLayout(new FormOptions());
		layout.build();

		Integer id = e1.getId();
		layout.select(Lists.newArrayList(id));

		Assert.assertEquals(e1, layout.getSelectedItem());
	}

	/**
	 * Test the selection of an item (single item)
	 */
	@Test
	public void testSimpleSearchLayout_Remove() {
		FormOptions options = new FormOptions();
		options.setShowRemoveButton(true);

		SimpleSearchLayout<Integer, TestEntity> layout = createLayout(options);
		layout.build();

		Assert.assertTrue(layout.getRemoveButton().isVisible());

		layout.setSelectedItem(e1);
		layout.checkButtonState(layout.getSelectedItem());
		layout.getRemoveButton().click();

		// check that nothing is selected any more and the item has been removed
		Assert.assertNull(layout.getSelectedItem());
		Table table = layout.getTableWrapper().getTable();
		Assert.assertEquals(2, table.size());
	}

	/**
	 * Test setting a pre-defined search value
	 */
	@Test
	public void testSimpleSearchLayout_setSearchValue() {
		SimpleSearchLayout<Integer, TestEntity> layout = createLayout(new FormOptions());
		layout.build();

		layout.setSearchValue("age", "13", "15");
		layout.search();
		
		Assert.assertEquals(1, layout.getTableWrapper().getTable().size());
	}
	
	private SimpleSearchLayout<Integer, TestEntity> createLayout(FormOptions fo) {
		return new SimpleSearchLayout<>(testEntityService, entityModelFactory.getModel(TestEntity.class),
		        QueryType.ID_BASED, fo, new SortOrder("name", SortDirection.ASCENDING));

	}
}
