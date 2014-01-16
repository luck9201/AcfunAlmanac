package com.nozomi.almanac.activity;

import java.util.ArrayList;

import com.nozomi.almanac.model.TableItem;
import com.nozomi.almanac.util.CommUtils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Pair;

public class AlarmReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {

		Pair<Long, String> fortunePair = CommUtils.getFortune(context);
		StringBuilder sb = new StringBuilder();

		sb.append("��");
		Pair<ArrayList<TableItem>, ArrayList<TableItem>> tableItemArrayPair = CommUtils
				.getTableItemArray(context);
		boolean isFirst = true;
		for (TableItem tableItem : tableItemArrayPair.first) {
			if (isFirst) {
				isFirst = false;
				sb.append(":");
			} else {
				sb.append(",");
			}
			sb.append(tableItem.getName());
		}
		sb.append(";��");
		isFirst = true;
		for (TableItem tableItem : tableItemArrayPair.second) {
			if (isFirst) {
				isFirst = false;
				sb.append(":");
			} else {
				sb.append(",");
			}
			sb.append(tableItem.getName());
		}
		sb.append("��");
		CommUtils.makeNotification(context, "��������:" + fortunePair.second,
				sb.toString());
	}
}