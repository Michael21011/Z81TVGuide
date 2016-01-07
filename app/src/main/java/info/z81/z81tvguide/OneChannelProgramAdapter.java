package info.z81.z81tvguide;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class OneChannelProgramAdapter extends BaseAdapter{
    private final Context context;
    private final ProgramList list;

    public OneChannelProgramAdapter(Context context, ProgramList values) {
        super();
        this.list = values;
        this.context = context;

    }

    static class ViewHolder {
        public TextView itemTimeView;
        public TextView itemCaptionView;
        public TextView dayHeaderView;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        ViewHolder holder;

        View rowView = convertView;
        if (rowView == null) {
            //LayoutInflater inflater = this.context.getLayoutInflater();
            LayoutInflater inflater = LayoutInflater.from(context);
            rowView = inflater.inflate(R.layout.one_channel_program_row, null, true);
            holder = new ViewHolder();
            holder.itemTimeView = (TextView) rowView.findViewById(R.id.itemTime);
            holder.itemCaptionView = (TextView) rowView.findViewById(R.id.itemCaption);
            holder.dayHeaderView = (TextView) rowView.findViewById(R.id.dayHeader);

            rowView.setTag(holder);

        } else {
            holder = (ViewHolder) rowView.getTag();
        }

        ProgramItem currentItem = (ProgramItem)list.GetItem(position);
        holder.itemTimeView.setText(currentItem.GetDateStartTimeOnly());
        holder.itemCaptionView.setText(currentItem.Title);
        if (list.IsFirstItemForDay(position)) {
            holder.dayHeaderView.setVisibility(View.VISIBLE);
            holder.dayHeaderView.setText(currentItem.GetDayHeader());
        }
        else
            holder.dayHeaderView.setVisibility(View.GONE);

        if (list.GetCurrentItemIndex(0)==position)
        {
            //rowView.setBackgroundColor(Color.GRAY);
            holder.itemTimeView.setTypeface(holder.itemTimeView.getTypeface(), Typeface.BOLD);
            holder.itemCaptionView.setTypeface(holder.itemCaptionView.getTypeface(), Typeface.BOLD);
            holder.itemCaptionView.setText(currentItem.Title+" идет сейчас");
        }
        else
        {
            holder.itemTimeView.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            holder.itemCaptionView.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));


        }

/*        if (currentItem.Description.isEmpty())
            holder.descView.setVisibility(View.GONE);
        else
            holder.descView.setVisibility(View.VISIBLE);
  */


        return rowView;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.Count();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return list.GetItem(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return list.GetItemId(arg0);
    }


}



