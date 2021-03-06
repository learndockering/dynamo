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

/**
 * Implementation of an object that holds paging information
 * 
 * @author bas.rutten
 *
 */
public class PageableImpl implements Pageable {

    private final int pageNumber;

    private final int pageSize;

    private final SortOrders sortOrders;

    /**
     * Constructor
     * 
     * @param pageNumber
     * @param pageSize
     * @param sortOrders
     */
    public PageableImpl(int pageNumber, int pageSize, SortOrder... sortOrders) {
        this(pageNumber, pageSize, new SortOrders(sortOrders));
    }

    /**
     * Constructor
     * 
     * @param pageNumber
     * @param pageSize
     * @param sortOrders
     */
    public PageableImpl(int pageNumber, int pageSize, SortOrders sortOrders) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.sortOrders = sortOrders;
    }

    @Override
    public int getPageNumber() {
        return pageNumber;
    }

    @Override
    public int getPageSize() {
        return pageSize;
    }

    @Override
    public int getOffset() {
        return pageNumber * pageSize;
    }

    @Override
    public SortOrders getSortOrders() {
        return sortOrders;
    }

}
