package ru.defo.util;
import java.util.ArrayList;
public enum ConfirmMessage {

	ASK_CLEAR_UNIT("�������� ������ [%0] ������ � ���� ���� �����?"),
	ASK_DEL_UNITBIN("������� ������ [%0] �� ������ [%1] � ���� ����� � ����?"),
	AKS_MOVE_UNIT_FROM_BIN("������ [%0] �������� � ������ [%1]. ����������� ��� � ������ [%2]?"),
	ASK_DEL_QUANT_FROM_UNIT("������� ����� � ������� [%0] ?"),
	ASK_ASSIGN_BC_SKU("��������� �����-��� [%0] �������� [%1] ������ [%2] ?"),
	ASK_DELETE_BARCODE_SKU("�����-��� [%0] ����� �������� �������� [%1]. ������� ���� �����-��� � ��������?"),
	ASK_BC_UNKNOWN_ASSIGN("�����-��� [%0] �� ������ � ���������. ������� ���� �����-��� � �������?"),
	ASK_UNIT_RECONTROL("������ [%0] ��� ���������������� ��� �������� [%1]. ��������� ��� ��������� ��������?"),
	ASK_SHIPMENT_UNIT("���� ����� � ������� [%0] �������� � ������ ?"),
	ASK_CLEAR_BIN_RESERV_FOR_UNIT("������ [%0] ������������� �������� [%1].<br>���������� ������?");
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
