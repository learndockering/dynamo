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
package com.ocs.dynamo.dao;

import org.junit.Assert;
import org.junit.Test;

import com.ocs.dynamo.dao.SortOrder.Direction;
import com.ocs.dynamo.exception.OCSRuntimeException;

public class SortOrderDaoTest {

    @Test
    public void testDefaultAscending() {
        SortOrder order = new SortOrder("property");

        Assert.assertEquals(Direction.ASC, order.getDirection());
        Assert.assertEquals("property", order.getProperty());
    }

    @Test
    public void testPropertyAndDirection() {
        SortOrder order = new SortOrder(Direction.DESC, "property");

        Assert.assertEquals(Direction.DESC, order.getDirection());
        Assert.assertEquals("property", order.getProperty());
    }

    @Test
    public void testPropertyAndDirectionFromString() {
        SortOrder order = new SortOrder(Direction.fromString("ASC"), "property");

        Assert.assertEquals(Direction.ASC, order.getDirection());
        Assert.assertEquals("property", order.getProperty());
    }

    @Test
    public void testPropertyAndDirectionFromString2() {
        SortOrder order = new SortOrder(Direction.fromString("DESC"), "property");

        Assert.assertEquals(Direction.DESC, order.getDirection());
        Assert.assertEquals("property", order.getProperty());
    }

    @Test(expected = OCSRuntimeException.class)
    public void testPropertyAndDirectionFromString_Wrong() {
        new SortOrder(Direction.fromString("AS"), "property");
    }

    @Test
    public void testEquals() {
        SortOrder order = new SortOrder("property");
        SortOrder order2 = new SortOrder("property");
        SortOrder order3 = new SortOrder(Direction.DESC, "property");

        Assert.assertFalse(order.equals(null));
        Assert.assertFalse(order.equals(new Object()));
        Assert.assertTrue(order.equals(order));
        Assert.assertTrue(order.equals(order2));
        Assert.assertFalse(order.equals(order3));
    }
}
