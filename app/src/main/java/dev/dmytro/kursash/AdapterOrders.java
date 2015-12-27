package dev.dmytro.kursash;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import dev.dmytro.kursash.QueryObjects.Order;

/**
 * Created by Dmytro on 27.12.2015.
 */
public class AdapterOrders  extends ArrayAdapter<Order> {
    Context ctx;
    ArrayList<Order> arrOfOrders;

    public AdapterOrders(Context context, ArrayList<Order> orders) {
        super(context, R.layout.activity_orderslist,orders);
        this.ctx = context;
        this.arrOfOrders = orders;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rootView = inflater.inflate(R.layout.item_order, parent, false);
        TextView description = (TextView) rootView.findViewById(R.id.txtView_item_order_description);
        TextView date = (TextView) rootView.findViewById(R.id.txtView_item_order_date);
        TextView status = (TextView) rootView.findViewById(R.id.txtView_item_order_status);
        ImageView imgView = (ImageView) rootView.findViewById(R.id.imgView_item_order);

        description.setText("Описание: " + arrOfOrders.get(position).getDescription() );
        String [] tempDate = arrOfOrders.get(position).getDate().split("-");
        date.setText("Дата: " + tempDate[2] + "." + tempDate[1] + "." + tempDate[0]);

        if(arrOfOrders.get(position).isDone()==1)
        {
            status.setTextColor(Color.GREEN);
            status.setText("Готово");
        }
        else
        {
            status.setTextColor(Color.rgb(238,128,90));
            status.setText("Выполняется");
        }
        try{
            Glide.with(ctx).load( arrOfOrders.get(position).getPicture() ).into(imgView);
        }catch (Exception e) {}

        return rootView;
    }


}
