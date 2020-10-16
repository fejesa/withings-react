package io.myhealth.withings.api;

import java.util.List;

public interface Page<T> {

    /** List of items on the current page. */
    List<T> getContent();

    /** In case of there are more results use this value to retrieve next available rows. */
    int getOffset();

    /** Page size. */
    int getPageSize();

    /** Current page number. */
    int getPageNumber();
}
