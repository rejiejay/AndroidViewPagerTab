package cn.rejiejay.demo;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.rejiejay.demo.fragment.TabFragment;

public class MainActivity extends AppCompatActivity {

    public ViewPager vpMainPager;

    public Button Btn1;
    public Button Btn2;
    public Button Btn3;
    public Button Btn4;

    public List<TabFragment> mTabFragment = new ArrayList<>();

    public List<String> myTitle = new ArrayList<>(Arrays.asList("123123", "12312313213123213123", "ga a wq v", "asd zcx"));

    public void initPageComponent() {
        vpMainPager = findViewById(R.id.vp_main);

        Btn1 = findViewById(R.id.btn_1);
        Btn2 = findViewById(R.id.btn_2);
        Btn3 = findViewById(R.id.btn_3);
        Btn4 = findViewById(R.id.btn_4);

        /**
         * 这里是 与 Fragment 进行通信
         * 首先给第一个按钮绑定方法
         * 这里的目的是传值给 Fragment
         * 需要注意的一点就是要进行保存、 fragment 也就是 public Object instantiateItem 这个方法
         * 否则会有 安卓切换屏幕的BUG（但是这是极小的情况
         */
        Btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 先获取，再传值
                TabFragment mmTabFragment = mTabFragment.get(0);

                if (mmTabFragment == null) {
                    return;
                }

                mmTabFragment.changeTitle("12312312312312");
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initPageComponent();

        /**
         * 这里的目的就是要初始化 Fragment 进去
         * FragmentPagerAdapter 和 FragmentStatePagerAdapter 都可以使用
         * 区别是回收Fragment执行方法不一样,一个会销毁，一个不会进行销毁、
         * 还有细节一些创建方法不一样，但是对于我来说区别不大、
         */
//        vpMainPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
        vpMainPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
//                /**
//                 * 这里直接调用我们的构造函数
//                 */
//                return TabFragment.newInstance(myTitle.get(i));
                TabFragment fragment = TabFragment.newInstance(myTitle.get(i));

                if (i == 0) {
                    /**
                     * 这个就是通过 Fragment 触发事件，然后Activity去进行相应操作
                     */
                    fragment.setOnTitleClickListenrt(new TabFragment.OnTitle_OnClick() {
                        @Override
                        public void onClick(String title) {
                            /**
                             * 相当于是监听
                             * 在做什么操作都可以
                             */
                            changeTitle2(title);
                        }
                    });
                }

                return fragment;
            }

            /**
             * 这里是设置多少页
             */
            @Override
            public int getCount() {
                return myTitle.size();
            }

            /**
             * 这里主要作用就是解决 屏幕横向问题 保存fragment状态到mTabFragment ArrayList里面去
             */
            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                TabFragment fragment = (TabFragment) super.instantiateItem(container, position);

                mTabFragment.add(position, fragment);

                return fragment;
            }

            /**
             * 这里主要作用也是解决 屏幕横向问题
             * 手动清空一下fragment
             */
            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                mTabFragment.remove(position);

                super.destroyItem(container, position, object);
            }
        });
    }

    /**
     * 这里演示第二种通信的方法
     */
    public void changeTitle2(String name) {
        Btn2.setText(name);
    }

}
