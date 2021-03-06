package pl.hypeapp.wykopolka.ui.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.pascalwelsch.compositeandroid.activity.CompositeActivity;

import net.grandcentrix.thirtyinch.internal.TiPresenterProvider;
import net.grandcentrix.thirtyinch.plugin.TiActivityPlugin;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.hypeapp.wykopolka.R;
import pl.hypeapp.wykopolka.adapter.DashboardPagerAdapter;
import pl.hypeapp.wykopolka.plugin.CrashlyticsPlugin;
import pl.hypeapp.wykopolka.plugin.ToolbarActivityPlugin;
import pl.hypeapp.wykopolka.presenter.DashboardPresenter;
import pl.hypeapp.wykopolka.view.DashboardView;

public class DashboardActivity extends CompositeActivity implements DashboardView {
    private DashboardPagerAdapter mDashboardPagerAdapter;
    private DashboardPresenter mDashboardPresenter;
    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.viewpager) ViewPager mViewPager;
    @BindView(R.id.viewpager_tab) SmartTabLayout mSmartTabLayout;

    private final ToolbarActivityPlugin mToolbarPlugin = new ToolbarActivityPlugin();
    private final CrashlyticsPlugin mCrashlyticsPlugin = new CrashlyticsPlugin();

    private final TiActivityPlugin<DashboardPresenter, DashboardView> mPresenterPlugin =
            new TiActivityPlugin<>(new TiPresenterProvider<DashboardPresenter>() {
                @NonNull
                @Override
                public DashboardPresenter providePresenter() {
                    mDashboardPresenter = new DashboardPresenter(getIntent());
                    return mDashboardPresenter;
                }
            });

    public DashboardActivity() {
        addPlugin(mPresenterPlugin);
        addPlugin(mToolbarPlugin);
        addPlugin(mCrashlyticsPlugin);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ButterKnife.bind(this);
        Toolbar toolbar = mToolbarPlugin.initToolbar(mToolbar);
        mToolbarPlugin.setNavigationDrawer(toolbar);
        initViewPager(mViewPager, mSmartTabLayout);
        mDashboardPresenter = mPresenterPlugin.getPresenter();
    }

    private void initViewPager(ViewPager viewPager, SmartTabLayout smartTabLayout) {
        mDashboardPagerAdapter = new DashboardPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mDashboardPagerAdapter);

        final LayoutInflater layoutInflater = LayoutInflater.from(this);
        final Resources resources = getResources();
        smartTabLayout.setCustomTabView(new SmartTabLayout.TabProvider() {
            @Override
            public View createTabView(ViewGroup container, int position, PagerAdapter adapter) {
                View itemView = layoutInflater.inflate(R.layout.tab_dashboard_icon_text, container, false);
                TextView text = (TextView) itemView.findViewById(R.id.custom_tab_text);
                text.setAllCaps(true);
                ImageView icon = (ImageView) itemView.findViewById(R.id.custom_tab_icon);
                switch (position) {
                    case 0:
                        icon.setImageDrawable(resources.getDrawable(R.drawable.ic_thumb_up_white_36dp));
                        text.setText(getString(R.string.tab_title_recommended));
                        break;
                    case 1:
                        icon.setImageDrawable(resources.getDrawable(R.drawable.ic_hourglass_full_white_36dp));
                        text.setText(getString(R.string.tab_title_waiting));
                        break;
                    case 2:
                        icon.setImageDrawable(resources.getDrawable(R.drawable.ic_favorite_white_36dp));
                        text.setText(getString(R.string.tab_title_wishlist));
                        break;
                    default:
                        throw new IllegalStateException("Invalid position: " + position);
                }
                return itemView;
            }
        });
        smartTabLayout.setViewPager(viewPager);
    }

    @Override
    public void setCurrentPage(int page) {
        mViewPager.setCurrentItem(page);
    }

    @Override
    @OnClick(R.id.fab_add_book)
    public void addBook() {
        Intent intent = new Intent(this, AddBookActivity.class);
        startActivity(intent);
    }
}
