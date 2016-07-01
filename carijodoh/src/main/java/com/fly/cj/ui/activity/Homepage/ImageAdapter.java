package com.fly.cj.ui.activity.Homepage;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fly.cj.api.obj.ActiveUserReceive;
import com.fly.cj.R;
import com.fly.cj.base.AQuery;
import com.fly.cj.utils.App;
import com.fly.cj.utils.SharedPrefManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class ImageAdapter extends BaseAdapter {

    private final Context context;
    private int lastPosition = -1;
    private final List<ActiveUserReceive.ListUser> obj;
    private String module;
    private SharedPrefManager pref;
    private AQuery aq;
    public ImageAdapter(Context context, List<ActiveUserReceive.ListUser> paramObj) {
        this.context = context;
        this.obj = paramObj;
        this.module = module;
        aq = new AQuery(context);

    }

    @Override
    public int getCount() {
        return obj == null ? 0 : obj.size();
    }

    @Override
    public Object getItem(int position) {
        return obj == null ? null : obj.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    static class ViewHolder {
        @InjectView(R.id.expanded_text)
        TextView expanded_text;

        @InjectView(R.id.expanded_image)
        ImageView expanded_image;

        @InjectView(R.id.expanded_text2)
        TextView expanded_text2;

        @InjectView(R.id.expanded_text3)
        TextView expended_text3;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        pref = new SharedPrefManager(context);

        ViewHolder vh;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.home2, parent, false);
            vh = new ViewHolder();
            ButterKnife.inject(vh, view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) view.getTag();
        }
        aq.recycle(view);

        String dob = obj.get(position).getUserDOB();

        String dateString = dob;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date date = dateFormat.parse(dateString);
            System.out.println(date);
            int d = getDay(date);
            System.out.println(d);

            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);

            int real = year - d;
            vh.expended_text3.setText(String.valueOf(real) + " tahun");

        } catch (ParseException e) {
            e.printStackTrace();
        }

        vh.expanded_text.setText(obj.get(position).getUserStatus());
        vh.expanded_text2.setText(obj.get(position).getUserId());

        String image = obj.get(position).getUserImage();
        String imageUrl = App.IMAGE_URL + image;

        Log.e("imagePath", imageUrl);

        if (image.equals("")){
            vh.expanded_image.setImageResource(R.drawable.default_profile2);
        }
        else {
            aq.id(R.id.expanded_image).image(imageUrl);
        }

        pref.setUserStatus(obj.get(position).getUserStatus());
        //pref.setImageUrl(imageUrl);

        Animation animation = AnimationUtils.loadAnimation(view.getContext(), (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        vh.expanded_image.startAnimation(animation);
        lastPosition = position;

        return view;
    }

    public static int getDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }
}
