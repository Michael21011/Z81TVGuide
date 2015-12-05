package info.z81.z81tvguide;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NowAdapter extends BaseAdapter{
    private final Context context;
    private final NowList list;
    
    public NowAdapter(Context context, NowList values) {
        super();
    	this.list = values;
        this.context = context;

    }
    
    static class ViewHolder {
        public ImageView imageView;
        public TextView textView;
        public TextView programmView;
        public TextView diffView;
        public TextView descView;
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
            rowView = inflater.inflate(R.layout.rowlayout, null, true);
            holder = new ViewHolder();
            holder.textView = (TextView) rowView.findViewById(R.id.channel);
            holder.programmView = (TextView) rowView.findViewById(R.id.programm);
            holder.diffView = (TextView) rowView.findViewById(R.id.diff);
            holder.imageView = (ImageView) rowView.findViewById(R.id.icon);
            holder.descView = (TextView) rowView.findViewById(R.id.desc);
            rowView.setTag(holder);
        } else {
            holder = (ViewHolder) rowView.getTag();
        }
        NowItem currentItem = (NowItem)list.GetItem(position);
        holder.textView.setText(currentItem.ChannelName);
        holder.diffView.setText(currentItem.getAgoTime());
        holder.programmView.setText(currentItem.Title);
        holder.imageView.setImageResource(currentItem.getChannelLogo());
        holder.descView.setText(currentItem.Description);
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
