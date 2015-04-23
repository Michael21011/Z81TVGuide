package info.z81.z81tvguide;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ChannelAdapter extends BaseAdapter{
    private final Context context;
    private final ChannelList list;

    public ChannelAdapter(Context context, ChannelList values) {
        super();
    	this.list = values;
        this.context = context;

    }
    
    // ����� ��� ���������� �� ������� ����� � ��� ����������� �������
    // �� �������� ������
    static class ViewHolder {
        public ImageView imageView;
        public TextView textView;
        public ImageView starView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // ViewHolder ������������ ������ ��������� ����� ������� ��������

        ViewHolder holder;
        // ������� ������������ ������, ���� �������� �����
        // �������� ������ ���� ������� ������ ��� ���� ������� ���� � ��� ��
        View rowView = convertView;
        if (rowView == null) {
            //LayoutInflater inflater = this.context.getLayoutInflater();
            LayoutInflater inflater = LayoutInflater.from(context);
            rowView = inflater.inflate(R.layout.channel_row_layout, null, true);
            holder = new ViewHolder();
            holder.textView = (TextView) rowView.findViewById(R.id.channel);
            holder.imageView = (ImageView) rowView.findViewById(R.id.channellogo);
            holder.starView = (ImageView) rowView.findViewById(R.id.star);
            rowView.setTag(holder);
        } else {
            holder = (ViewHolder) rowView.getTag();
        }
        ChannelItem currentItem = (ChannelItem)list.GetItem(position);
        holder.textView.setText(currentItem.ChannelName);
        holder.imageView.setImageResource(currentItem.getChannelLogo());
        if (currentItem.Stared) {
            holder.starView.setImageResource(R.drawable.ic_layout_star_selected);
        } else {
        holder.starView.setImageResource(R.drawable.ic_layout_star_op);}
        // ��������� ������ ��� Windows � iPhone
   /*     String s = names[position];
        if (s.startsWith("Windows7") || s.startsWith("iPhone")
                || s.startsWith("Solaris")) {

            holder.imageView.setImageResource(R.drawable.no);
        } else {
            holder.imageView.setImageResource(R.drawable.ok);
        } */

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
