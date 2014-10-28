package com.jumige.mobile.news.activity;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jumige.mobile.news.db.NewsTypeDataDb;
import com.jumige.mobile.news.view.fragment.SlidingMenuFragmentLeft;
import com.jumige.mobile.news.view.fragment.SlidingMenuFragmentRight;
import com.jumige.mobile.news.view.fragment.ViewPagerFragment;
import com.mobile.jumige.news.R;
import com.viewpagerindicator.TabPageIndicator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Window;

public class MainActivity extends FragmentActivity {
	
	private SlidingMenu menuRight;
	
	private NewsTypeDataDb newsTypeDataDb;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 设置标题栏
		
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title);

		// 滑动菜单
		initSlidingMenuRight();
		// 初始化栏目数据
		newsTypeDataDb = new NewsTypeDataDb();
		newsTypeDataDb.initNewsType();

		
		// ViewPager的代码块
		FragmentPagerAdapter adapter = new GoogleMusicAdapter(
				getSupportFragmentManager());

		ViewPager pager = (ViewPager) findViewById(R.id.pager);
		pager.setAdapter(adapter);

		TabPageIndicator indicator = (TabPageIndicator) findViewById(R.id.indicator);
		indicator.setViewPager(pager);
	}

	/*
	 * 初始化滑动菜单
	 */
	private void initSlidingMenuRight() {

		// 设置菜单的属性
		menuRight = new SlidingMenu(this);
		// 设置滑动菜单的范围，全屏or边缘
		menuRight.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		// 设置阴影图片
		menuRight.setShadowDrawable(R.drawable.shadow_slidingmenuright);
		menuRight
				.setSecondaryShadowDrawable(R.drawable.shadow_slidingmenuright);
		// 滑动时的渐变程度
		// menuRight.setFadeDegree(0.5f);
		// 使SlidingMenu附加在Activity上
		menuRight.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		// 设置right滑动菜单的宽度
		menuRight.setBehindWidth(480);
		// 设置left滑动菜单的宽度
		menuRight.setRightBehindWidth(550);
		// 设置左右都可以划出SlidingMenu菜单
		menuRight.setMode(SlidingMenu.LEFT_RIGHT);
		// 设置滑动菜单左侧布局文件
		menuRight.setMenu(R.layout.menuframright);
		// 设置滑动菜单右侧布局文件
		menuRight.setSecondaryMenu(R.layout.menuframleft);

		// 动态判断自动关闭或开启SlidingMenu
		// menuRight.toggle();
		// 显示SlidingMenu
		// menuRight.showMenu();
		// 显示内容
		// menuRight.showContent();
		// 监听SlidingMenu打开
		// menuRight.setOnOpenListener(onOpenListener);
		// 关于关闭menu有两个监听，简单的来说，对于menu close事件，一个是when,一个是after
		// 监听SlidingMenu关闭时事件
		// menuRight.OnClosedListener(OnClosedListener);
		// 监听SlidingMenu关闭后事件
		// menuRight.OnClosedListener(OnClosedListener);

		// 设置右侧菜单的布局文件
		menuRight.setSecondaryMenu(R.layout.menuframleft);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.menuframright, new SlidingMenuFragmentRight())
				.commit();

		getSupportFragmentManager().beginTransaction()
				.replace(R.id.menuframleft, new SlidingMenuFragmentLeft())
				.commit();
		// 右侧菜单的阴影图片
		// menuRight.setSecondaryShadowDrawable(R.drawable.shadowright);
	}

	@Override
	public void onBackPressed() {
		// 点击返回键关闭滑动菜单
		if (menuRight.isMenuShowing()) {
			menuRight.showContent();
		} else {
			super.onBackPressed();
		}

	}

	/*
	 * ViewPager复写的方法//有待研究
	 */
	class GoogleMusicAdapter extends FragmentPagerAdapter {
		public GoogleMusicAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			return ViewPagerFragment.newInstance();
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return newsTypeDataDb.onNewsTypeMap.get(position).toUpperCase();

		}

		@Override
		public int getCount() {
			return newsTypeDataDb.onNewsTypeMap.size();
		}
	}

	
}
