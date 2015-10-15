package edu.temple.checkerboard;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapterClass extends BaseAdapter {
    private int c = 0;
    private Context context;
    String[] numbers;

    private int counter = 0;
    private boolean color_flag = true;

    private ArrayList<TextView> list = new ArrayList<TextView>();
    public CustomAdapterClass(int n, Context context, String[] numbers) {
        c = n;
        this.context = context;
        this.numbers = numbers;
    }

    @Override
    public int getCount() {
        return c;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        TextView text = new TextView(context);
        text.setText(numbers[position]);
        text.setGravity(Gravity.CENTER);
        text.setTextSize(10);
        text.setMinHeight(110);
        text.setPadding(2, 2, 2, 2);

        if (color_flag) {
            if ((position % 2 == 0)) {
                text.setBackgroundColor(Color.GREEN);
                text.setTextColor(Color.WHITE);
            }
            if (counter == 1)
            {
                text.setBackgroundColor(Color.GREEN);
                text.setTextColor(Color.WHITE);
            }
            if (counter == 0)
            {
                text.setBackgroundColor(Color.WHITE);
                text.setTextColor(Color.GRAY);
            }
            counter++;
        }
        else {
            if (!(position % 2 ==0)) {
                text.setBackgroundColor(Color.GREEN);
                text.setTextColor(Color.WHITE);
            }
            if (counter == 0)
            {
                text.setBackgroundColor(Color.GREEN);
                text.setTextColor(Color.WHITE);
            }
            if (counter == 1)
            {
                text.setBackgroundColor(Color.WHITE);
                text.setTextColor(Color.GRAY);
            }
            counter++;
        }
        if (counter == Math.sqrt(c)) {
            color_flag = !color_flag;
            counter =0;
        }

        return text;
    }
}
