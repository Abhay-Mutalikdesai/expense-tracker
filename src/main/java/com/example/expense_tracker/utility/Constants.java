package com.example.expense_tracker.utility;

public class Constants {
    public static final String EMPTY_STRING = "";
    public static final String STATUS_SUCCESS = "Success";
    public static final String STATUS_FAILED = "Failed";
    public static final String CATEGORY_OTHER = "other";

    public static final String CATEGORY_MSG_PREFIX = "CAT";
    public static final String EXPENSE_MSG_PREFIX = "EXP";
    public static final String PREFIX_SEPARATOR = ".";

    public static final String CATEGORY_CREATED = CATEGORY_MSG_PREFIX + PREFIX_SEPARATOR + "1";
    public static final String CATEGORY_UPDATED = CATEGORY_MSG_PREFIX + PREFIX_SEPARATOR + "2";
    public static final String CATEGORY_DELETED = CATEGORY_MSG_PREFIX + PREFIX_SEPARATOR + "3";
    public static final String DEFAULT_CATEGORY_EDIT = CATEGORY_MSG_PREFIX + PREFIX_SEPARATOR + "4";
    public static final String DEFAULT_CATEGORY_DELETE = CATEGORY_MSG_PREFIX + PREFIX_SEPARATOR + "5";
    public static final String INVALID_CATEGORY = CATEGORY_MSG_PREFIX + PREFIX_SEPARATOR + "6";
    public static final String CATEGORY_ALREADY_PRESENT = CATEGORY_MSG_PREFIX + PREFIX_SEPARATOR + "7";
    public static final String CATEGORY_ID_NOT_FOUND = CATEGORY_MSG_PREFIX + PREFIX_SEPARATOR + "8";
    public static final String CATEGORY_NOT_EXISTS = CATEGORY_MSG_PREFIX + PREFIX_SEPARATOR + "9";
    public static final String CATEGORY_IN_USE = CATEGORY_MSG_PREFIX + PREFIX_SEPARATOR + "10";

    public static final String EXPENSE_CREATED = EXPENSE_MSG_PREFIX + PREFIX_SEPARATOR + "1";
    public static final String EXPENSE_UPDATED = EXPENSE_MSG_PREFIX + PREFIX_SEPARATOR + "2";
    public static final String EXPENSE_DELETED = EXPENSE_MSG_PREFIX + PREFIX_SEPARATOR + "3";
    public static final String EXPENSE_NOT_FOUND = EXPENSE_MSG_PREFIX + PREFIX_SEPARATOR + "4";
}
