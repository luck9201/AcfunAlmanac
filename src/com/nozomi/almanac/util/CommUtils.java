package com.nozomi.almanac.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import com.nozomi.almanac.R;
import com.nozomi.almanac.activity.AlarmReceiver;
import com.nozomi.almanac.activity.SplashActivity;
import com.nozomi.almanac.model.ListItem;
import com.nozomi.almanac.model.TableItem;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Pair;
import android.view.View;
import android.widget.Toast;

public class CommUtils {

	private static Toast toast = null;

	public static void makeToast(Context context, String text) {
		if (toast == null) {
			toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
			View backgroundView = toast.getView();
			backgroundView.setBackgroundResource(R.drawable.toast_background);
			toast.setView(backgroundView);
		} else {
			toast.setText(text);
		}
		toast.show();
	}

	public static void makeNotification(Context context, String title,
			String text) {
		NotificationManager notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		Intent intent = new Intent(context, SplashActivity.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
				intent, 0);
		Notification notification = new Notification();
		notification.icon = R.drawable.ic_launcher;
		notification.tickerText = title + ";" + text;
		notification.defaults |= Notification.DEFAULT_SOUND;
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		// ����֪ͨ��ʾ�Ĳ���
		notification.setLatestEventInfo(context, title, text, pendingIntent);
		notificationManager.notify(0, notification);
	}

	public static Pair<ArrayList<TableItem>, ArrayList<TableItem>> getTableItemArray(
			Context context) {

		ArrayList<Integer> avatars = new ArrayList<Integer>();
		for (int i = 1; i <= 50; i++) {
			avatars.add(context.getResources().getIdentifier(
					"ac_" + String.format("%02d", i), "drawable",
					context.getPackageName()));
		}

		Calendar calendar = Calendar.getInstance(Locale.CHINA);

		long seed = calendar.get(Calendar.YEAR) * 37621
				+ (calendar.get(Calendar.MONTH) + 1) * 539
				+ calendar.get(Calendar.DATE);

		ArrayList<ListItem> list = new ArrayList<ListItem>();
		list.add(new ListItem("��AV", "�ͷ�ѹ������������", "�ᱻ����ײ��"));
		list.add(new ListItem("��ģ��", "���������������", "���񲻼��а����������"));
		list.add(new ListItem("Ͷ�������", "����Բ�����", "�ᱻ�����˷���"));
		list.add(new ListItem("��������", "����ҲҪ���ⱱ", "����ɥʬ��ɹ��"));
		list.add(new ListItem("��Ů������", "Ů��øж�����", "��ȥϴ���ˣ��Ǻ�"));
		list.add(new ListItem("žžž", "žžžžžžž", "�Ῠ������"));
		list.add(new ListItem("��ҹ", "ҹ���Ч�ʸ���", "�����к���Ҫ����"));
		list.add(new ListItem("����", "�˷��Ӹ������������", "�����˼���"));
		list.add(new ListItem("ɢ��", "��������������ڨ", "��·��ȵ�ˮ��"));
		list.add(new ListItem("����λ��", "���������Ϸ�500", "�ҷ����˹һ�"));
		list.add(new ListItem("�㱨����", "���佱��������", "�ϰ�͵����Ϸ���۹���"));
		list.add(new ListItem("����è��", "�Ų�������������", "�������޴�������"));
		list.add(new ListItem("�޹�", "����Ů���޹���ڨ", "������ش�С�㱻����"));
		list.add(new ListItem("���", "�ڰ����������������", "�ѵ�����ǡ��������ǿ��ɣ�"));
		list.add(new ListItem("���", "��ʵ��Ҳϲ����þ���", "�Բ�������һ������"));
		list.add(new ListItem("��վ����", "������������", "�յ������«��"));
		list.add(new ListItem("׷�·�", "���֮ǰ�Ҿ�������", "�ᱻ��͸"));
		list.add(new ListItem("���ճ�", "ŭ����ҳ", "�ᱻ�ϰ巢��"));
		list.add(new ListItem("�¸���", "���Ĭ��һ��ͨ��", "�ᱻ��ɢ��"));
		list.add(new ListItem("��ɳ��", "ɳ�����ֵ����鷢", "�ᱻ�������߳�play"));
		list.add(new ListItem("����", "��Ʒ�����", "�����Ʒ��Ҫ�˻�"));
		list.add(new ListItem("����", "�¹��������������", "����һ�̾ͼ�н��"));
		list.add(new ListItem("����", "֪ʶ��������", "ע������ȫ�޷�����"));
		list.add(new ListItem("��˯", "��˯����������", "���ڰ�ҹ������Ȼ��ʧ��"));
		list.add(new ListItem("���", "�����������Ż�", "����������"));

		long sg = rnd(seed, 8) % 100;
		ArrayList<TableItem> goodTableItemArray = new ArrayList<TableItem>();
		for (long i = 0, l = rnd(seed, 9) % 3 + 2; i < l; i++) {
			int n = (int) (sg * 0.01 * list.size());
			ListItem a = list.get(n);
			int m = (int) (rnd(seed, (3 + i)) % 100 * 0.01 * avatars.size());
			goodTableItemArray.add(new TableItem(avatars.get(m), a.getName(), a
					.getGood()));
			list.remove(n);
			avatars.remove(m);
		}

		long sb = rnd(seed, 4) % 100;
		ArrayList<TableItem> badTableItemArray = new ArrayList<TableItem>();
		for (long i = 0, l = rnd(seed, 7) % 3 + 2; i < l; i++) {
			int n = (int) (sb * 0.01 * list.size());
			ListItem a = list.get(n);
			int m = (int) (rnd(seed, (2 + i)) % 100 * 0.01 * avatars.size());
			badTableItemArray.add(new TableItem(avatars.get(m), a.getName(), a
					.getBad()));
			list.remove(n);
			avatars.remove(m);
		}

		return new Pair<ArrayList<TableItem>, ArrayList<TableItem>>(
				goodTableItemArray, badTableItemArray);

	}

	public static Pair<Long, String> getFortune(Context context) {
		Calendar calendar = Calendar.getInstance(Locale.CHINA);

		long seed = calendar.get(Calendar.YEAR) * 37621
				+ (calendar.get(Calendar.MONTH) + 1) * 539
				+ calendar.get(Calendar.DATE);

		// Aվ�õ���uid��������ʱ�������
		SharedPreferences sp = context.getSharedPreferences("acfun_almanac",
				Context.MODE_PRIVATE);
		int uid = sp.getInt(CommDef.SP_UID, 0);
		if (uid == 0) {
			uid = (int) (System.currentTimeMillis() % 1000000);
			Editor editor = sp.edit();
			editor.putInt(CommDef.SP_UID, uid);
			editor.commit();
		}

		long fortune = rnd(seed * 624755, 6) % 100;
		String fortuneLevel = "ĩ��";
		// if
		if (fortune < 5) {
			// 5%
			fortuneLevel = "����";
		} else if (fortune < 20) {
			// 15%
			fortuneLevel = "��";
		} else if (fortune < 50) {
			// 30%
			fortuneLevel = "ĩ��";
		} else if (fortune < 60) {
			// 10%
			fortuneLevel = "�뼪";
		} else if (fortune < 70) {
			// 10%
			fortuneLevel = "��";
		} else if (fortune < 80) {
			// 10%
			fortuneLevel = "С��";
		} else if (fortune < 90) {
			// 10%
			fortuneLevel = "�м�";
		} else {
			// 5%
			fortuneLevel = "��";
		}
		return new Pair<Long, String>(fortune, fortuneLevel);
	}

	private static long rnd(long a, long b) {
		long n = a % 11117;
		for (long i = 0; i < 25 + b; i++) {
			n = n * n;
			n = n % 11117;
		}
		return n;
	}

	public static void setAlarm(Context context) {
		AlarmManager am = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);

		Intent intent = new Intent(context, AlarmReceiver.class);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1,
				intent, 0);
		am.cancel(pendingIntent);

		SharedPreferences sp = context.getSharedPreferences("acfun_almanac",
				Context.MODE_PRIVATE);
		boolean notificationState = sp.getBoolean(
				CommDef.SP_NOTIFICATION_STATE, false);
		if (notificationState) {
			String notificationTime = sp.getString(
					CommDef.SP_NOTIFICATION_TIME, "09:00");
			String[] notificationTimeSplit = notificationTime.split(":");
			int hourOfDay = 9;
			int minute = 0;
			try {
				hourOfDay = Integer.valueOf(notificationTimeSplit[0]);
				minute = Integer.valueOf(notificationTimeSplit[1]);
			} catch (Exception e) {

			}
			Calendar calendar = Calendar.getInstance(Locale.CHINA);
			calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
			calendar.set(Calendar.MINUTE, minute);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			if (calendar.getTimeInMillis() < System.currentTimeMillis()) {
				calendar.add(Calendar.DATE, 1);
			}
			am.setRepeating(AlarmManager.RTC_WAKEUP,
					calendar.getTimeInMillis(), (24 * 60 * 60 * 1000),
					pendingIntent);
		}
	}
}
