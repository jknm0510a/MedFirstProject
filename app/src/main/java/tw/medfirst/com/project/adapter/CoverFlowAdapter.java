package tw.medfirst.com.project.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import tw.medfirst.com.project.Entity.CoverFlowEntity;
import tw.medfirst.com.project.R;

public class CoverFlowAdapter extends BaseAdapter {
	
	private ArrayList<CoverFlowEntity> mData = new ArrayList<>(0);
	private Context mContext;

	public CoverFlowAdapter(Context context) {
		mContext = context;
	}
	
	public void setData(ArrayList<CoverFlowEntity> data) {
		mData = data;
	}
	
	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public Object getItem(int pos) {
		return mData.get(pos);
	}

	@Override
	public long getItemId(int pos) {
		return pos;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

        View rowView = convertView;

        if (rowView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.coverflow_item, null);

			ViewHolder viewHolder = new ViewHolder();
			viewHolder.text = (TextView) rowView.findViewById(R.id.icon_name_home);
			viewHolder.image = (ImageView) rowView.findViewById(R.id.icon_image_home);
			rowView.setTag(viewHolder);
        }

        ViewHolder holder = (ViewHolder) rowView.getTag();

        holder.image.setImageResource(mData.get(position).imageResId);
        holder.text.setText(mData.get(position).titleResId);

		return rowView;
	}


    static class ViewHolder {
        public TextView text;
        public ImageView image;
    }
}
