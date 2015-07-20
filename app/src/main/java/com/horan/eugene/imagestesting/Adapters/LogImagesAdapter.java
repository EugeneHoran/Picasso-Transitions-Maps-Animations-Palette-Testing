package com.horan.eugene.imagestesting.Adapters;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
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

import com.horan.eugene.imagestesting.DetailsActivity;
import com.horan.eugene.imagestesting.MainActivity;
import com.horan.eugene.imagestesting.R;
import com.horan.eugene.imagestesting.Util.Equations;
import com.squareup.picasso.Picasso;

import java.util.List;

public class LogImagesAdapter extends RecyclerView.Adapter<LogImagesAdapter.ViewHolder> implements View.OnClickListener {
    private List<LogImages> mLogImages;
    private OnRecyclerViewItemClickListener<LogImages> itemClickListener;
    private int itemLayout;
    public static Context context;
    int width;
    int height;
    boolean listOrGridView;
    int vibrant;

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
            height = 1200;
        } else {
            width = size.x / 2;
            height = 900;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
        v.setOnClickListener(this);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final LogImages item = mLogImages.get(position);
        holder.itemView.setTag(item);
        holder.txtDescription.setText(item.getDescription());
        holder.txtLocation.setText(item.getTitle());
        Picasso.with(context)
                .load(item.getImage()).centerCrop().resize(width, height)
                .into(holder.image, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        Bitmap bitmap = ((BitmapDrawable) holder.image.getDrawable()).getBitmap();
                        if (bitmap != null) {
                            Palette palette = Palette.from(bitmap).generate();
                            vibrant = palette.getDarkVibrantColor(0x000000);
                            holder.txtBack.setBackgroundColor(Equations.getColorWithAlpha(vibrant, 0.5f));
                            holder.progress.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onError() {
                        holder.imageError.setImageResource(R.mipmap.ic_broken_image_white_24dp);
                        holder.progress.setVisibility(View.GONE);
                    }
                });
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    MainActivity mainActivity = (MainActivity) context;
                    Intent intent = new Intent(mainActivity, DetailsActivity.class);
                    intent.putExtra("POSITION", position);
                    intent.putExtra("STATUS_BAR", vibrant);
                    intent.putExtra("COLOR", Equations.getColorWithAlpha(vibrant, 0.5f));
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(mainActivity,
                            Pair.create((View) holder.txtBack, "back"),
                            Pair.create((View) holder.image, "cardImage"),
                            Pair.create((View) holder.txtLocation, "location"));
                    mainActivity.startActivity(intent, options.toBundle());
                } else {

                }
            }
        });
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
        public TextView txtDescription;
        public TextView txtLocation;
        public ImageView image;
        public RelativeLayout back;
        public ProgressBar progress;
        public LinearLayout txtBack;
        public ImageView imageError;

        public ViewHolder(final View itemView) {
            super(itemView);
            txtDescription = (TextView) itemView.findViewById(R.id.txtDescription);
            txtLocation = (TextView) itemView.findViewById(R.id.txtLocation);
            image = (ImageView) itemView.findViewById(R.id.image);
            image.setDrawingCacheEnabled(true);
            back = (RelativeLayout) itemView.findViewById(R.id.back);
            progress = (ProgressBar) itemView.findViewById(R.id.progress);
            txtBack = (LinearLayout) itemView.findViewById(R.id.txtBack);
            back.setMinimumHeight(900);
            imageError = (ImageView) itemView.findViewById(R.id.imageError);
        }
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener<LogImages> listener) { // Click Lister
        this.itemClickListener = listener;
    }
}