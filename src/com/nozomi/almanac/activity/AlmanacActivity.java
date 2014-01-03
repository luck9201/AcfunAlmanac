package com.nozomi.almanac.activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import com.nozomi.almanac.R;
import com.nozomi.almanac.model.ListItem;
import com.nozomi.almanac.model.TableItem;
import com.nozomi.almanac.util.LunarUtil;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class AlmanacActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);

		initView();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void initView() {

		Calendar calendar = Calendar.getInstance(Locale.CHINESE);
		String[] dayOfWeek = { "��", "һ", "��", "��", "��", "��", "��" };

		TextView itemDateCalendarView = (TextView) findViewById(R.id.item_date_calendar);
		itemDateCalendarView.setText(calendar.get(Calendar.YEAR) + "��"
				+ (1 + calendar.get(Calendar.MONTH)) + "��"
				+ calendar.get(Calendar.DATE) + "�� ����"
				+ dayOfWeek[calendar.get(Calendar.DAY_OF_WEEK) - 1]);

		long seed = calendar.get(Calendar.YEAR) * 37621
				+ (calendar.get(Calendar.MONTH) + 1) * 539
				+ calendar.get(Calendar.DATE);

		TextView itemSignCalendarView = (TextView) findViewById(R.id.item_sign_calendar);
		f(itemSignCalendarView, seed);

		TextView itemSubdateCalendarView = (TextView) findViewById(R.id.item_subdate_calendar);
		itemSubdateCalendarView
				.setText(LunarUtil.GetLunarDay(calendar.get(Calendar.YEAR),
						(1 + calendar.get(Calendar.MONTH)),
						calendar.get(Calendar.DATE)));

		ArrayList<ListItem> list = new ArrayList<ListItem>();
		list.add(new ListItem("��AV", "�ͷ�ѹ������������", "�ᱻ����ײ��"));
		list.add(new ListItem("��ģ��", "���������������", "���񲻼��а����������"));
		list.add(new ListItem("Ͷ�������", "����Բ�����", "�ᱻ�����˷���"));
		list.add(new ListItem("��������", "����ҲҪ���ⱱ", "����ɥʬ��ɹ��"));
		list.add(new ListItem("��Ů������", "Ů��øж�����", "��ȥϴ���ˣ��Ǻ�"));
		list.add(new ListItem("žžž", "žžžžžžž", "�Ῠ������"));
		list.add(new ListItem("��ҹ", "ҹ���Ч�ʸ���", "�����к���Ҫ����"));
		list.add(new ListItem("����", "�˷��Ӹ�������������", "�����˼���"));
		list.add(new ListItem("ɢ��", "��������������ڨ", "��·��ȵ�ˮ��"));
		list.add(new ListItem("����λ��", "���������Ϸ�500", "�ҷ����˹һ�"));
		list.add(new ListItem("�㱨����", "���佱��������", "�ϰ�͵����Ϸ���۹���"));
		list.add(new ListItem("����è��", "�Ų�������������", "�������޴�������"));
		list.add(new ListItem("�޹�", "����Ů���޹���ڨ", "������ش�С�㱻����"));
		list.add(new ListItem("���", "�ڰ�����������������", "�ѵ�����ǡ��������ǿ��ɣ�"));
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

		ArrayList<Integer> avatars = new ArrayList<Integer>();
		for (int i = 1; i <= 50; i++) {
			avatars.add(getResources().getIdentifier(
					"ac_" + String.format("%02d", i), "drawable",
					getPackageName()));
		}

		long sg = rnd(seed, 8) % 100;
		ArrayList<TableItem> goodTableItemArray = new ArrayList<TableItem>();
		for (long i = 0, l = rnd(seed, 9) % 3 + 2; i < l; i++) {
			int n = (int) (sg * 0.01 * list.size());
			ListItem a = list.get(n);
			int m = (int) (rnd(seed, (3 + i)) % 100 * 0.01 * avatars.size());
			// System.out.println(avatars.get(m) + ".gif " + a.getName() + " "
			// + a.getGood());
			goodTableItemArray.add(new TableItem(avatars.get(m), a.getName(), a
					.getGood()));
			list.remove(n);
			avatars.remove(m);
		}
		ListView goodRightView = (ListView) findViewById(R.id.good_right);
		TableItemAdapter goodTableItemAdapter = new TableItemAdapter(this,
				goodTableItemArray);
		goodRightView.setAdapter(goodTableItemAdapter);

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
		ListView badRightView = (ListView) findViewById(R.id.bad_right);
		TableItemAdapter badTableItemAdapter = new TableItemAdapter(this,
				badTableItemArray);
		badRightView.setAdapter(badTableItemAdapter);

	}

	private long rnd(long a, long b) {
		long n = a % 11117;
		for (long i = 0; i < 25 + b; i++) {
			n = n * n;
			n = n % 11117;
		}
		return n;
	}

	private void f(TextView itemSignCalendarView, long seed) {
		//
		final long n = rnd(seed * 624755, 6) % 100;
		String t = "ĩ��";
		// if
		if (n < 5) {
			// 5%
			t = "����";
		} else if (n < 20) {
			// 15%
			t = "��";
		} else if (n < 50) {
			// 30%
			t = "ĩ��";
		} else if (n < 60) {
			// 10%
			t = "�뼪";
		} else if (n < 70) {
			// 10%
			t = "��";
		} else if (n < 80) {
			// 10%
			t = "С��";
		} else if (n < 90) {
			// 10%
			t = "�м�";
		} else {
			// 5%
			t = "��";
		}

		itemSignCalendarView.setText(t);
		itemSignCalendarView.setTextColor(Color.rgb(
				(int) (255 * (10 + n * 0.8) / 100), 51, 51));
		itemSignCalendarView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(AlmanacActivity.this, "��������ָ����" + n + "%", Toast.LENGTH_SHORT)
						.show();

			}
		});
	}

	private class TableItemAdapter extends BaseAdapter {

		private Context context;
		private ArrayList<TableItem> tableItemArray;

		public TableItemAdapter(Context context,
				ArrayList<TableItem> tableItemArray) {
			this.context = context;
			this.tableItemArray = tableItemArray;
		}

		@Override
		public int getCount() {
			return tableItemArray.size();
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			final GViewHolder holder;
			if (convertView == null) {
				holder = new GViewHolder();
				convertView = LayoutInflater.from(context).inflate(
						R.layout.table_item, null, false);

				holder.cView = (ImageView) convertView.findViewById(R.id.c);
				holder.aView = (TextView) convertView.findViewById(R.id.a);
				holder.bView = (TextView) convertView.findViewById(R.id.b);
				convertView.setTag(holder);
			} else {
				holder = (GViewHolder) convertView.getTag();
			}

			TableItem tableItem = tableItemArray.get(position);
			holder.cView.setImageResource(tableItem.getC());
			holder.aView.setText(tableItem.getA());
			holder.bView.setText(tableItem.getB());

			return convertView;
		}

		private class GViewHolder {
			ImageView cView;
			TextView aView;
			TextView bView;
		}
	}

}