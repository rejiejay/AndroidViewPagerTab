package cn.rejiejay.demo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.rejiejay.demo.fragment.TabFragment;

public class MainActivity extends AppCompatActivity {

    public ViewPager vpMainPager;

    public List<String> myTitle = new ArrayList<>(Arrays.asList("123123", "12312313213123213123", "ga a wq v", "asd zcx"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vpMainPager = findViewById(R.id.vp_main);

        /**
         * 这里的目的就是要初始化 Fragment 进去
         */
        vpMainPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                /**
                 * 这里直接调用我们的构造函数
                 */
                return TabFragment.newInstance(myTitle.get(i));
            }

            /**
             * 这里是设置多少页
             */
            @Override
            public int getCount() {
                return myTitle.size();
            }
        });
    }
}
