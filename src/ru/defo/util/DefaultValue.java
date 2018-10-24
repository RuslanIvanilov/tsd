package ru.defo.util;

public final class DefaultValue {

	public static final long DEFAULT_CLIENT_ID = 1; //ООО Дэфо

	public static final long UNIT_NUMBER_SIZE = 10;
	public static final long ARCHIVE_STATUS = 8;
	public static final int TABLE_ROW_COUNT = 25;
	public static final long DOK_BIN_TYPE = 3;
	public static final Long QUALITY_STATE = 1L;

	public static final Long INITIATOR_WMS = 1L;
	public static final Long INITIATOR_HOST = 2L;
	public static final String DELIMITER = ":";

	public static final String BIN_RESERV_FOR_UNIT = "ячейка заблокирована поддоном";
    public static final String INVENT_UNIT_TO_BIN_TEXT = "Инвентаризация.Поддон помещен в ячейку";
    public static final String INVENT_BIN_RESERV_UNIT = "Инвентаризация.Ячейка занята поддоном";
    public static final String INVENT_CREATE_UNIT_TEXT = "Инвентаризация.Поддон создан";
    public static final String BIN_RESERVED = "Ячейка занята другим поддоном";
    public static final String CREATE_UNIT_TEXT = "Поддон создан";
    public static final String INVENT_DEL_UNITBLK_TEXT = "Инвентаризация.Удаление из набивн.ячейки";
    public static final String INVENT_DEL_UNIT_TEXT = "Инвентаризация.Удаление из ячейки";
    public static final String DEL_UNIT_FROM_BIN = "Удаление из ячейки";
    public static final String INVENT_CLEAR_UNIT_TEXT = "Инвентаризация.Очистка поддона";
    public static final String INVENT_ADD_QUANT_UNIT_TEXT = "Инвентаризация.Добавление товара на поддон";
    public static final String TRANSF_UNIT_TO_BIN_TEXT = "Перемещение.Поддон помещен в ячейку(без задания)";
    public static final String TRANSF_UNIT_FROM_BIN_TEXT = "Перемещение.из ячейки";
    public static final String ASSIGN_BARCODE_SKU = "Присвоение.штрих-код";
    public static final String DEL_QUANT = "Удаление кванта";
    public static final String MINUS_QUANT = "Изъятие кванта";
    public static final String PLUS_QUANT = "Добавление кванта";
    public static final String SPLIT = "Перекладка";
    public static final String INVENTORY = "Инвентаризация";
    public static final String SHIPMENT = "Отгрузка";
    public static final String AUTHORIZATION_TEXT = "Авторизация";
    public static final String BASE_SKU_NAME = "ШТ.";
    public static final int EAN_SIGN_1C = 268;
    public static final String INCOME_AREA_CODE = "DOK";
    public static final String CONTROL_AREA_CODE = "DOK";
    public static final String CONTROL_SHPT_AREA_CODE = "SHP";
    public static final String LOST_AREA_CODE = "LST";
    public static final String PRINT_USER_BC = "Распечатан личный штрих-код";
    public static final String SPLIT_CREATE_UNIT_TEXT = "Перекладка.Поддон создан";
    public static final String ADVICE = "Приемка";
    public static final String ADVICE_STARTED = "Приемка. Начата работа";
    public static final String ADVICE_POS_ADDED = "Приемка. добавление строки к клиентскому номеру";
    public static final String ASSIGN_DEL_BARCODE = "ASSIGN.DELETE";
    public static final String TOSAVE = "Сохранить";
    public static final String TOSHIPMENT = "Отгрузить";
    public static final String TODELETE = "Удалить";
    public static final String CONTROL = "Контроль";
    public static final String DIFF_TEXT = "Разница";
    public static final long SHIPPED_UNIT_STATUS = 6L;
    public static final long COMPLETE_STATUS = 4L;
    public static final long PICKED_END_STATUS = 4L;
    public static final long PICKED_CLOSE_STATUS = 8L;
    public static final long STATUS_PREORDER_LINKED = 3L;
    public static final long STATUS_START_ADVICE = 3L;
    public static final long STATUS_INTEGR_ERROR = 10L;
    public static final long STATUS_CONTROL_FINISHED = 5L;
    public static final long STATUS_LOST = 7L;
    public static final long BIN_TYPE_PICK_AVAL = 2L;
    public static final long STATUS_CREATED = 1L;
    public static final String QUANT_QTY = "Кол-во прописанное";
    public static final String LOAD_DATA_HISTORY = "WebService загружен объект";
    public static final String HOST_NAME = "1C";
    public static final String ISO_UNIT_TYPE_NAME = "IS";
    public static final long STATUS_CLOSED = 8L;
    public static final String EMPTY_OPTION = "<пусто>";

    public static final String FORM_START = "/index.jsp";
    public static final String FORM_INFO_TXT = "/form_templates/info_text.jsp";
    public static final String FORM_REQUEST_TXT = "/form_templates/request_text_ex.jsp";
    public static final String FORM_REQUEST_NUM = "/form_templates/request_num_ex.jsp";
    public static final String FORM_LIST = "/form_templates/kv_list.jsp";

}
