package com.boom.hyithelper;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhuangfei.timetable.TimetableView;
import com.zhuangfei.timetable.listener.ISchedule;
import com.zhuangfei.timetable.listener.IWeekView;
import com.zhuangfei.timetable.model.Schedule;
import com.zhuangfei.timetable.view.WeekView;

import java.util.List;

/**
 * 作者：created by zjr on 2019/4/14 16 13
 * 邮箱：2759442036@qq.com
 */
public class Fragment2 extends Fragment {
    private ListView listView;
    private WeekView mnotebook_weekselect;
    private TimetableView mid_timetableView;
    private TextView mnotebook_title;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view= inflater.inflate(R.layout.fragment2, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initTimetableView();

    }
    /**
     * 初始化课程控件
     */
    private void initTimetableView() {
        //获取控件
        mnotebook_weekselect = getView().findViewById(R.id.notebook_weekselect);
        mid_timetableView = getView().findViewById(R.id.notebook_timetableView);
        mnotebook_title=getView().findViewById(R.id.notebook_title);
        //设置周次选择属性mySubjects
        mnotebook_weekselect.source(Database.loadDefaultSubjects())//在这里放你封装好的List<mySubjects>
                .curWeek(1)
                .callback(new IWeekView.OnWeekItemClickedListener() {
                    @Override
                    public void onWeekClicked(int week) {
                        int cur = mid_timetableView.curWeek();
                        //更新切换后的日期，从当前周cur->切换的周week
                        mid_timetableView.onDateBuildListener()
                                .onUpdateDate(cur, week);
                        mid_timetableView.changeWeekOnly(week);
                    }
                })
                .callback(new IWeekView.OnWeekLeftClickedListener() {
                    @Override
                    public void onWeekLeftClicked() {
                        //onWeekLeftLayoutClicked();
                    }
                })
                .isShow(false)//设置隐藏，默认显示
                .showView();

        mid_timetableView.source(Database.loadDefaultSubjects2())
                .curWeek(1)
                .curTerm("大二下学期")
                .maxSlideItem(12)
                .monthWidthDp(40)
                //透明度
                //日期栏0.1f、侧边栏0.1f，周次选择栏0.6f
                //透明度范围为0->1，0为全透明，1为不透明
                .alpha(0.6f, 0.6f, 0.8f)
                .callback(new ISchedule.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, List<Schedule> scheduleList) {
                        //display(scheduleList);
                        /*Intent intent=new Intent(getActivity(), ViewCourseActivity.class);
                        intent.putExtra("date",temp_curWeek);
                        Bundle bundle=new Bundle();
                        bundle.putSerializable("SuperLessons",(Serializable) scheduleList);
                        intent.putExtras(bundle);
                        startActivity(intent);*/
                    }
                })
                .callback(new ISchedule.OnItemLongClickListener() {
                    @Override
                    public void onLongClick(View v, int day, int start) {
                        Toast.makeText(getActivity(),
                                "长按:周" + day  + ",第" + start + "节",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .callback(new ISchedule.OnWeekChangedListener() {
                    @Override
                    public void onWeekChanged(int curWeek) {
                        mnotebook_title.setText("第" + curWeek + "周");
                    }
                })
                //旗标布局点击监听
                .callback(new ISchedule.OnFlaglayoutClickListener() {
                    @Override
                    public void onFlaglayoutClick(int day, int start) {
                        mid_timetableView.hideFlaglayout();
                        Toast.makeText(getActivity(),
                                "点击了旗标:周" + (day + 1) + ",第" + start + "节",
                                Toast.LENGTH_SHORT).show();
                        /*Intent intent=new Intent(MainActivity.this, EditCourseActivity.class);
                        startActivity(intent);*/
                    }
                })
                .showView();
    }
}
