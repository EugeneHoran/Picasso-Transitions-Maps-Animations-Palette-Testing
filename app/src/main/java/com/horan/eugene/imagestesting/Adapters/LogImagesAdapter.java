package com.horan.eugene.imagestesting.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.horan.eugene.imagestesting.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class LogImagesAdapter extends RecyclerView.Adapter<LogImagesAdapter.ViewHolder> implements View.OnClickListener {
    private List<LogImages> mLogImages;
    private OnRecyclerViewItemClickListener<LogImages> itemClickListener;
    private int itemLayout;
    private Context context;
    int width;
    boolean listOrGridView;

    public LogImagesAdapter(Context contex, List<LogImages> log, int itemLayout, boolean listOrGrid) {
        context = contex;
        this.mLogImages = log;
        this.itemLayout = itemLayout;
        this.listOrGridView = listOrGrid;
        // Set Image width
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        if (listOrGridView == true) {
            width = size.x;
        } else {
            width = size.x / 2;
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
        v.setOnClickListener(this);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final LogImages item = mLogImages.get(position);
        holder.itemView.setTag(item);
        holder.text.setText(item.getDescription());
        holder.txtTitle.setText(item.getTitle());
        Picasso.with(context)
                .load(item.getImage()).centerCrop().resize(width, 900)
                .into(holder.image, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        Bitmap bitmap = ((BitmapDrawable) holder.image.getDrawable()).getBitmap();
                        if (bitmap != null) {
                            Palette palette = Palette.from(bitmap).generate();
                            int vibrant = palette.getDarkVibrantColor(0x000000);
                            holder.txtBack.setBackgroundColor(getColorWithAlpha(vibrant, 0.5f));
                            holder.progress.setVisibility(View.GONE);

                        }
                    }

                    @Override
                    public void onError() {
                        holder.imageError.setImageResource(R.mipmap.ic_broken_image_white_24dp);
                        holder.progress.setVisibility(View.GONE);
                    }
                });
    }


    public int getColorWithAlpha(int color, float ratio) {
        int alpha = Math.round(Color.alpha(color) * ratio);
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);
        return Color.argb(alpha, r, g, b);
    }


    @Override
    public int getItemCount() {
        return mLogImages.size();
    }

    @Override
    public void onClick(View view) {
        if (itemClickListener != null) {
            LogImages model = (LogImages) view.getTag();
            itemClickListener.onItemClick(view, model);
        }
    }

    public void add(LogImages item) {
        mLogImages.add(item);
    }

    public void remove(LogImages item) {
        int position = mLogImages.indexOf(item);
        mLogImages.remove(position);
        notifyItemRemoved(position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView text;
        public ImageView image;
        public RelativeLayout back;
        public ProgressBar progress;
        public LinearLayout txtBack;
        public TextView txtTitle;
        public ImageView imageError;

        public ViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.txtItem);
            image = (ImageView) itemView.findViewById(R.id.image);
            back = (RelativeLayout) itemView.findViewById(R.id.back);
            progress = (ProgressBar) itemView.findViewById(R.id.progress);
            txtBack = (LinearLayout) itemView.findViewById(R.id.txtBack);
            back.setMinimumHeight(900);
            txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
            imageError = (ImageView) itemView.findViewById(R.id.imageError);
        }
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener<LogImages> listener) { // Click Lister
        this.itemClickListener = listener;
    }
}