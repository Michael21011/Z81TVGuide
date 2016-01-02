package info.z81.z81tvguide;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ChannelAdapter extends BaseAdapter{
    private final Context context;
    private final TVProgram list;



    public ChannelAdapter(Context context, TVProgram values) {
        super();
    	this.list = values;
        this.context = context;
    }
    

    static class ViewHolder {
        public ImageView imageView;
        public TextView textView;
        public ImageView starView;
        public TextView NumberDigitalView;
        public TextView channelIdView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        ViewHolder holder;

        View rowView = convertView;
        if (rowView == null) {
            //LayoutInflater inflater = this.context.getLayoutInflater();
            LayoutInflater inflater = LayoutInflater.from(context);
            rowView = inflater.inflate(R.layout.channel_row_layout, null, true);
            holder = new ViewHolder();
            holder.textView = (TextView) rowView.findViewById(R.id.channel);
            holder.imageView = (ImageView) rowView.findViewById(R.id.channellogo);
            holder.starView = (ImageView) rowView.findViewById(R.id.star);
            holder.NumberDigitalView = (TextView) rowView.findViewById(R.id.numberDigital);
            holder.channelIdView = (TextView) rowView.findViewById(R.id.channelId);

            rowView.setTag(holder);
        } else {
            holder = (ViewHolder) rowView.getTag();
        }
        ProgramList currentItem = list.GetItem(position);
        holder.textView.setText(currentItem.ChannelName);
        holder.imageView.setImageResource(currentItem.getChannelLogo());
        holder.NumberDigitalView.setText(currentItem.getDigitalNumberLabel());
        holder.channelIdView.setText(currentItem.ChannelId);


        if (currentItem.isStared()) {
            holder.starView.setImageResource(android.R.drawable.star_on);
        } else {
        holder.starView.setImageResource(android.R.drawable.star_off);}
        holder.starView.setTag(Integer.valueOf(currentItem.ChannelId));
        return rowView;
    }

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.ChannelCount();
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
