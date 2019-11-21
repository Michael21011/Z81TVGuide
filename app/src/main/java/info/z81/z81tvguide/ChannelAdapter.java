package info.z81.z81tvguide;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ChannelAdapter extends BaseAdapter{
    private final Context context;
    private final TVProgram list;
    private final boolean showOnlyFavorites;



    public ChannelAdapter(Context context, TVProgram values, String FilterString, boolean ShowOnlyFavorites) {
        super();
    	this.list = values;
        this.list.ApplyFilter(FilterString, ShowOnlyFavorites);
        this.context = context;
        this.showOnlyFavorites = ShowOnlyFavorites;
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
        ProgramList currentItem = list.GetFilteredItem(position);
        holder.textView.setText(currentItem.ChannelName);

        if (currentItem.getChannelLogo()==R.drawable.empty)
        {
            Utils.LoadBitmapFromInternet(holder.imageView, currentItem.ChannelName, currentItem.IconUrl);
        }
            else
        {
        holder.imageView.setImageResource(currentItem.getChannelLogo());}


        holder.imageView.setTag(currentItem.ChannelId);

        holder.NumberDigitalView.setText(currentItem.getDigitalNumberLabel(true));
        holder.NumberDigitalView.setTag(currentItem.ChannelId);

        holder.channelIdView.setText(currentItem.ChannelId);

        if (currentItem.isStared()) {
            holder.starView.setImageResource(android.R.drawable.star_on);
        } else {
        holder.starView.setImageResource(android.R.drawable.star_off);}

        holder.starView.setTag(currentItem.ChannelId);
        if (currentItem.ShouldHighligt())
        {
            rowView.setBackgroundColor(ContextCompat.getColor(context, android.support.v7.appcompat.R.color.highlighted_text_material_light));
        }

        return rowView;
    }

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.FilteredChannelCount();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.GetFilteredItem(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return list.GetFilteredItemId(arg0);
	}

}
