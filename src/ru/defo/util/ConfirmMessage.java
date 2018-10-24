package ru.defo.util;
import java.util.ArrayList;
public enum ConfirmMessage {

	ASK_CLEAR_UNIT("Очистить поддон [%0] удалив с него весь товар?"),
	ASK_DEL_UNITBIN("Удалить поддон [%0] из ячейки [%1] и весь товар с него?"),
	AKS_MOVE_UNIT_FROM_BIN("Поддон [%0] числится в ячейке [%1]. Переместить его в ячейку [%2]?"),
	ASK_DEL_QUANT_FROM_UNIT("Удалить товар с поддона [%0] ?"),
	ASK_ASSIGN_BC_SKU("Присвоить штрих-код [%0] упаковке [%1] товара [%2] ?"),
	ASK_DELETE_BARCODE_SKU("Штрих-код [%0] ранее назначен артикулу [%1]. забрать этот штрих-код у артикула?"),
	ASK_BC_UNKNOWN_ASSIGN("Штрих-код [%0] не связан с артикулом. Связать этот штрих-код и артикул?"),
	ASK_UNIT_RECONTROL("Поддон [%0] уже проконтролирован для отгрузки [%1]. Выполнить его повторный контроль?"),
	ASK_SHIPMENT_UNIT("Весь товар с поддона [%0] загружен в машину ?"),
	ASK_CLEAR_BIN_RESERV_FOR_UNIT("Ячейка [%0] ЗАБЛОКИРОВАНА поддоном [%1].<br>Освободить ячейку?");
	private String message;

	ConfirmMessage(String message) { this.message = message; }

	public String message(ArrayList<String> params) {
		String msg = message;
		for (int i = 0; i < params.size(); i++) {
			msg = msg.replace(("%" + i), params.get(i));
		}
		return msg;
	}

}
