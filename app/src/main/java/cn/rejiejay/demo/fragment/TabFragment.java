package cn.rejiejay.demo.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.rejiejay.demo.MainActivity;
import cn.rejiejay.demo.R;

public class TabFragment extends Fragment {

    public TextView mTV;

    /**
     * 这个要从父组件传值进来
     * 要依赖于静态的工厂方法
     */
    public String mmmtitle;


    /**
     * 这个就是为了实现事件分发的
     * 而是通过 Fragment 触发事件，然后Activity去进行相应操作
     */
    public static interface OnTitle_OnClick {
        void onClick(String title);
    }
    public OnTitle_OnClick mListrner;
    public void setOnTitleClickListenrt(OnTitle_OnClick listrner) {
        mListrner = listrner;
    }


    /**
     * 这个方法主要目的是 通过 bundle 传递数据 title
     *
     * @param inputTitle 外部传值进来的title
     */
    public static TabFragment newInstance(String inputTitle) {
        /**
         * Bundle作用？
         * 因为 TabFragment 需要用到
         * Bundle主要用于传递数据；它保存的数据，是以key-value(键值对)的形式存在的。
         */
        Bundle bundle = new Bundle();
        bundle.putString("BUNDLE_KEY_TITLE", inputTitle);

        /**
         * 将Bundle里面的值初始化进来，差不多完成
         */
        TabFragment tabFragment = new TabFragment();
        tabFragment.setArguments(bundle);
        return tabFragment;
//        tabFragment.myTitle = "???"; 这样在Fragment里面设置值也是可行的，
//        但是要有关于自动销毁和回复的机制
//        所以，如果你这样自行添加，系统是无法判断的，所以就容易恢复失败！
//        所以需要使用 setArguments
//        这样自动恢复就可以成功了
    }

    /**
     * 创建的生命周期
     * 这里的主要目的是获取 Fragment 里面传值进来的参数
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle argument = getArguments();

        if (argument == null) {
            return;
        }

        mmmtitle = argument.getString("BUNDLE_KEY_TITLE", "");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // return super.onCreateView(inflater, container, savedInstanceState); // 实际上这货什么都没做，返回的是个null

        /**
         * 下面这段话就是设置fragment
         * 第二个参数是设置到哪里去
         */
        return inflater.inflate(R.layout.fragment_tab, container, false);
    }

    /**
     * 在view创建完成的时候调用
     * 在此处完成，view创建后的动作
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTV = view.findViewById(R.id.tv_title);

        /**
         * 这里的 mmtitle 是从其他地方传值进来的
         * 早在前面的生命周期已经初始化好了
         */
        mTV.setText(mmmtitle);

        /**
         * 绑定点击事件的目的是为了 传值
         */
        mTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 * 第一种传值的方法是通过获取main Activity
                 * 但是这里应该是会报错的
                 */
//                MainActivity activity = (MainActivity) getActivity();
//                activity.changeTitle2("1111122222");

                /**
                 * 第二种传值的方法是通过获取main Activity
                 * 一定程度上确实是可以这样做，可以避免崩溃的问题；
                 * 但是这种代码必须要有预知能力，其实有更好的方法、
                 */
//                Activity mActivity = getActivity();
//                if (mActivity instanceof MainActivity) {
//                    ((MainActivity) mActivity).changeTitle2("1111122222");
//                }

                /**
                 * 可以换一种思考方式，不是调用那里的方法，而是通过 Fragment 触发事件，然后Activity去进行相应
                 * 这种叫做事件分发？
                 */
                if (mListrner != null) {
                    mListrner.onClick("1231321321312");
                }


//                SecondActivity: TabFragment;
            }
        });
    }

    /**
     * 因为这个方法就相当于一个普通的类，所以要注意并发的问题
     */
    public void changeTitle(String name) {

        /**
         * 为了保证初始化完成才可进行调用的方法
         */
        if (!isAdded()) {
            return;
        }

        mTV.setText(name);
    }
}
