package com.nozomi.almanac.activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;

import com.nozomi.almanac.R;
import com.nozomi.almanac.model.TableItem;
import com.nozomi.almanac.util.CommUtils;
import com.nozomi.almanac.util.LunarUtil;
import com.umeng.analytics.MobclickAgent;
import com.umeng.scrshot.adapter.UMAppAdapter;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.sensor.UMSensor.OnSensorListener;
import com.umeng.socialize.sensor.UMSensor.WhitchButton;
import com.umeng.socialize.sensor.controller.UMShakeService;
import com.umeng.socialize.sensor.controller.impl.UMShakeServiceFactory;
import com.umeng.update.UmengUpdateAgent;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.SensorEvent;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class AlmanacActivity extends Activity {

	private UMShakeService mShakeController;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.almanac_activity);

		UmengUpdateAgent.setUpdateOnlyWifi(false);
		UmengUpdateAgent.update(this);

		initView();

		mShakeController = UMShakeServiceFactory
				.getShakeService("share almanac");
	}

	private void initView() {
		ImageButton backView = (ImageButton) findViewById(R.id.back);
		backView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});

		ImageButton logoHeaderView = (ImageButton) findViewById(R.id.logo_header);
		logoHeaderView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				CommUtils.makeToast(AlmanacActivity.this, "�����������");

			}
		});

		ArrayList<Integer> avatars = new ArrayList<Integer>();
		for (int i = 1; i <= 54; i++) {
			avatars.add(getResources().getIdentifier(
					"ac_" + String.format("%02d", i), "drawable",
					getPackageName()));
		}
		Random random = new Random();
		ImageView avatarHeaderView = (ImageView) findViewById(R.id.avatar_header);
		avatarHeaderView.setImageResource(avatars.get(1 + random.nextInt(50)));

		ImageButton settingView = (ImageButton) findViewById(R.id.setting);
		settingView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(AlmanacActivity.this,
						SettingActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.push_left_in,
						R.anim.push_left_out);
			}
		});

		Calendar calendar = Calendar.getInstance(Locale.CHINA);
		String[] dayOfWeek = { "��", "һ", "��", "��", "��", "��", "��" };

		TextView itemDateCalendarView = (TextView) findViewById(R.id.item_date_calendar);
		itemDateCalendarView.setText(calendar.get(Calendar.YEAR) + "��"
				+ (1 + calendar.get(Calendar.MONTH)) + "��"
				+ calendar.get(Calendar.DATE) + "�� ����"
				+ dayOfWeek[calendar.get(Calendar.DAY_OF_WEEK) - 1]);

		TextView itemSignCalendarView = (TextView) findViewById(R.id.item_sign_calendar);
		final Pair<Long, String> fortunePair = CommUtils.getFortune();

		itemSignCalendarView.setText(fortunePair.second);
		itemSignCalendarView.setTextColor(Color.rgb(
				(int) (255 * (10 + fortunePair.first * 0.8) / 100), 51, 51));
		itemSignCalendarView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				CommUtils.makeToast(AlmanacActivity.this, "��������ָ����"
						+ fortunePair.first + "%");
			}
		});

		TextView itemSubdateCalendarView = (TextView) findViewById(R.id.item_subdate_calendar);
		itemSubdateCalendarView
				.setText(LunarUtil.GetLunarDay(calendar.get(Calendar.YEAR),
						(1 + calendar.get(Calendar.MONTH)),
						calendar.get(Calendar.DATE)));

		Pair<ArrayList<TableItem>, ArrayList<TableItem>> tableItemArrayPair = CommUtils
				.getTableItemArray(this);

		ListView goodRightView = (ListView) findViewById(R.id.good_right);
		TableItemAdapter goodTableItemAdapter = new TableItemAdapter(this,
				tableItemArrayPair.first);
		goodRightView.setAdapter(goodTableItemAdapter);

		ListView badRightView = (ListView) findViewById(R.id.bad_right);
		TableItemAdapter badTableItemAdapter = new TableItemAdapter(this,
				tableItemArrayPair.second);
		badRightView.setAdapter(badTableItemAdapter);

		ImageView shackView = (ImageView) findViewById(R.id.shake);
		RotateAnimation animation  = new RotateAnimation(0, 45, Animation.RELATIVE_TO_SELF,
				0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		animation.setDuration(400);
		animation.setRepeatMode(Animation.REVERSE);
		animation.setRepeatCount(Animation.INFINITE);	
		shackView.startAnimation(animation);
			
		
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

				holder.avatarView = (ImageView) convertView
						.findViewById(R.id.avatar);
				holder.nameView = (TextView) convertView
						.findViewById(R.id.name);
				holder.contentView = (TextView) convertView
						.findViewById(R.id.content);
				convertView.setTag(holder);
			} else {
				holder = (GViewHolder) convertView.getTag();
			}

			TableItem tableItem = tableItemArray.get(position);
			holder.avatarView.setImageResource(tableItem.getAvatar());
			holder.nameView.setText(tableItem.getName());
			holder.contentView.setText(tableItem.getContent());

			return convertView;
		}

		private class GViewHolder {
			ImageView avatarView;
			TextView nameView;
			TextView contentView;
		}
	}

	private OnSensorListener mSensorListener = new OnSensorListener() {

		@Override
		public void onStart() {
		}

		/**
		 * ������ɺ�ص�
		 */
		@Override
		public void onComplete(SHARE_MEDIA platform, int eCode,
				SocializeEntity entity) {
			if (eCode == 200) {
				CommUtils.makeToast(AlmanacActivity.this, "����ɹ�");
			}
		}

		/**
		 * @Description: ҡһҡ������ɺ�ص�
		 */
		@Override
		public void onActionComplete(SensorEvent event) {
			// Toast.makeText(YourActivity.this, "�û�ҡһҡ����������ͣ��Ϸ",
			// Toast.LENGTH_SHORT).show();
		}

		/**
		 * @Description: �û���������ڵ�ȡ���ͷ���ť�����Ļص�
		 * @param button
		 *            �û��ڷ����ڵ���İ�ť����ȡ���ͷ���������ť
		 */
		@Override
		public void onButtonClick(WhitchButton button) {
			if (button == WhitchButton.BUTTON_CANCEL) {
				// Toast.makeText(YourActivity.this, "ȡ������,��Ϸ���¿�ʼ",
				// Toast.LENGTH_SHORT).show();
			} else {
				// ������, ( �û�����˷���ť )
			}
		}
	};

	@Override
	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
		UMAppAdapter appAdapter = new UMAppAdapter(AlmanacActivity.this);
		// ����ҡһҡ��������ʱ�û���ѡ��ƽ̨�����֧�����ƽ̨
		ArrayList<SHARE_MEDIA> platforms = new ArrayList<SHARE_MEDIA>();
		platforms.add(SHARE_MEDIA.SINA);
		// ����ҡһҡ�������������
		mShakeController.setShareContent("test");
		// ע��ҡһҡ����������,mSensorListener��2.1.2�ж���
		mShakeController.registerShakeListender(AlmanacActivity.this,
				appAdapter, platforms, mSensorListener);
	}

	@Override
	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);

		mShakeController.unregisterShakeListener(AlmanacActivity.this);
	}

	@Override
	public void onBackPressed() {
		finish();
		overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
	}
}
