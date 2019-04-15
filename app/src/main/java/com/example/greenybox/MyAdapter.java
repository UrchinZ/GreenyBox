package com.example.greenybox;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public abstract class MyAdapter<T> extends BaseAdapter {

    private ArrayList<T> mData;
    private int mLayoutRes;

    /**
     * Constructor
     * @param mData
     * @param mLayoutRes
     */
    public MyAdapter(ArrayList<T> mData, int mLayoutRes) {
        this.mData = mData;
        this.mLayoutRes = mLayoutRes;
    }

    @Override
    public int getCount() {
        return mData != null ? mData.size() : 0;
    }

    @Override
    public T getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Construct proper subview for the item
     * @param position
     * @param convertView
     * @param parent
     * @return subview of the item
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = ViewHolder.bind(parent.getContext(), convertView, parent, mLayoutRes
                , position);
        bindView(holder, getItem(position));

        //temporary subview
        View tmp = holder.getItemView();

        //another code block that can set the background status
        /*
        //We know T is for sure Item
        final T item = mData.get(position);
        Item i = (Item)item;
        int fresh = i.Freshness();

        //grab image view
        ImageView status = (ImageView)tmp.findViewById((R.id.status_icon));
        //set background status color
        if(fresh < 0){
            status.setBackgroundResource(R.color.discard);
        } else if (fresh > 1){
            status.setBackgroundResource(R.color.fresh);
        } else {
            status.setBackgroundResource(R.color.urgent);
        }
        */
        return tmp;
    }

    public abstract void bindView(ViewHolder holder, T obj);


    public static class ViewHolder {

        private SparseArray<View> mViews;   //Store ListView item's View
        private View item;                  //Store convertView
        private int position;
        private Context context;

        private ViewHolder(Context context, ViewGroup parent, int layoutRes) {
            mViews = new SparseArray<>();
            this.context = context;
            View convertView = LayoutInflater.from(context).inflate(layoutRes, parent, false);
            convertView.setTag(this);
            item = convertView;
        }

        /**
         * @param context
         * @param convertView
         * @param parent
         * @param layoutRes
         * @param position
         * @return
         */
        public static ViewHolder bind(Context context, View convertView, ViewGroup parent,
                                      int layoutRes, int position) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder(context, parent, layoutRes);
            } else {
                holder = (ViewHolder) convertView.getTag();
                holder.item = convertView;
            }
            holder.position = position;
            return holder;
        }

        /**
         * @param id
         * @param <T>
         * @return
         */
        @SuppressWarnings("unchecked")
        public <T extends View> T getView(int id) {
            T t = (T) mViews.get(id);
            if (t == null) {
                t = (T) item.findViewById(id);
                mViews.put(id, t);
            }
            return t;
        }

        /**
         * @return
         */
        public View getItemView() {
            return item;
        }

        /**
         * Get item position
         */
        public int getItemPosition() {
            return position;
        }

        /**
         * Set Text
         */
        public ViewHolder setText(int id, CharSequence text) {
            View view = getView(id);
            if (view instanceof TextView) {
                ((TextView) view).setText(text);
            }
            return this;
        }

        /**
         * Set Image
         */
        public ViewHolder setImageResource(int id, int drawableRes) {
            View view = getView(id);
            if (view instanceof ImageView) {
                ((ImageView) view).setImageResource(drawableRes);
            } else {
                view.setBackgroundResource(drawableRes);
            }
            return this;
        }


        /**
         * Click event
         */
        public ViewHolder setOnClickListener(int id, View.OnClickListener listener) {
            getView(id).setOnClickListener(listener);
            return this;
        }

        /**
         * Set visible
         */
        public ViewHolder setVisibility(int id, int visible) {
            getView(id).setVisibility(visible);
            return this;
        }

        /**
         * Set Tag
         */
        public ViewHolder setTag(int id, Object obj) {
            getView(id).setTag(obj);
            return this;
        }

    }

}
