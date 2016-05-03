package supergao.www.recyclerviewanddesign.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import supergao.www.recyclerviewanddesign.R;
import supergao.www.recyclerviewanddesign.bean.DataInfo;

/**
 *list适配器
 *@author superGao
 *creat at 2016/3/12
 */
public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {


    private Context context;
    private List<DataInfo> data;
    private boolean vertical;
    private boolean isHeader;
    View header;
    private static final int ITEM_VIEW_TYPE_HEADER = 0;
    private static final int ITEM_VIEW_TYPE_ITEM = 1;
    private OnItemClickListener listener;
    private int currentPosition;

    /**
     * item点击接口
     */
    public interface OnItemClickListener {
        void onClick(View itemView, int position);
        void onLongClick(View itemView, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public ListAdapter(Context context, List<DataInfo> data, boolean vertical) {
        header = View.inflate(context, R.layout.header_layout, null);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        header.setLayoutParams(layoutParams);
        this.context = context;
        this.data = data;
        this.vertical = vertical;
    }

    //当viewholder创建的时候回调
    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = null;
        if (vertical && viewType == 0) {
            return new ListViewHolder(header);
        }

        //垂直于水平使用不同的item布局
        if (vertical) {
            view = View.inflate(context, R.layout.item_list, null);
        } else {
            view = View.inflate(context, R.layout.item_grid, null);
        }


        return new ListViewHolder(view);
    }

    //当viewholder和数据绑定时回调
    @Override
    public void onBindViewHolder(final ListViewHolder holder, final int position) {
        DataInfo dataInfo = data.get(position);
        if (isHeader(position)) {
            return;
        }
        /*holder.itemView.setFocusable(true);
        holder.itemView.setTag(position);*/
        holder.descTv.setText(dataInfo.getText());
        holder.iv.setImageResource(dataInfo.getIcon());

        if(listener!=null){
            holder.itemView.setTag(position);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentPosition= (int)holder.itemView.getTag();
                    listener.onClick(holder.itemView, currentPosition);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener.onLongClick(holder.itemView, position);
                    return true;
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        if (data != null)
            return data.size();
        return 0;
    }

    class ListViewHolder extends RecyclerView.ViewHolder {
        private TextView descTv;
        private ImageView iv;
        private RelativeLayout rl_item;

        public ListViewHolder(View itemView) {
            super(itemView);
            rl_item=(RelativeLayout)itemView.findViewById(R.id.rl_item);
            descTv = (TextView) itemView.findViewById(R.id.item_list_tv_name);
            iv = (ImageView) itemView.findViewById(R.id.item_list_iv_icon);

        }
    }

    public boolean isHeader(int position) {
        return position == 0;
    }

    @Override
    public int getItemViewType(int position) {
        return isHeader(position) ? ITEM_VIEW_TYPE_HEADER : ITEM_VIEW_TYPE_ITEM;
    }
}
