package com.example.youtubeapimvvm.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.youtubeapimvvm.R;
import com.example.youtubeapimvvm.ui.YouTubeActivity;
import com.example.youtubeapimvvm.models.YoutubeResponse;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

public class VideosAdapter extends RecyclerView.Adapter<VideosAdapter.VideoViewHolder> {
    private List<YoutubeResponse.Item> items = new ArrayList<>();
    Context context;

    public VideosAdapter(Context context) {
        this.context = context;
    }

    @Override
    public VideoViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_item_video, parent, false);
        return new VideoViewHolder(view);    }

    @Override
    public void onBindViewHolder(final VideoViewHolder holder, final int position) {
        final YoutubeResponse.Item.Snippet snippet = items.get(position).getSnippet();
        String url = snippet.getThumbnails().getDefault().getUrl();
        Glide.with(holder.itemView.getContext()).load(url).into(holder.logo);

        holder.title.setText(snippet.getTitle());
        holder.description.setText(snippet.getChannelTitle());
//        holder.date.setText(snippet.getPublishedAt());

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String no=String.valueOf(position);
                String no=items.get(position).getId();
                String name=snippet.getTitle();
                String description=snippet.getDescription();

//                Toast.makeText(context,no,Toast.LENGTH_LONG).show();

                Intent intent=new Intent(context, YouTubeActivity.class);
                intent.putExtra("videoID",no);
                intent.putExtra("name",name);
                intent.putExtra("desc",description);
                // Get the transition name from the string
                String transitionName = context.getString(R.string.transition);

                ActivityOptionsCompat options =

                        ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context,
                                holder.linearLayout,   // Starting view
                                transitionName    // The String
                        );

                ActivityCompat.startActivity(context, intent, options.toBundle());


//                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    public void addAll(Collection<YoutubeResponse.Item> items) {
        int currentItemCount = this.items.size();
        this.items.addAll(items);
        notifyItemRangeInserted(currentItemCount, items.size());
    }

    public void replaceWith(Collection<YoutubeResponse.Item> items) {
        if (items != null) {
            int oldCount = this.items.size();
            int newCount = items.size();
            int delCount = oldCount - newCount;
            this.items.clear();
            this.items.addAll(items);
            if (delCount > 0) {
                notifyItemRangeChanged(0, newCount);
                notifyItemRangeRemoved(newCount, delCount);
            } else if (delCount < 0) {
                notifyItemRangeChanged(0, oldCount);
                notifyItemRangeInserted(oldCount, -delCount);
            } else {
                notifyItemRangeChanged(0, newCount);
            }
        }

    }

    static class VideoViewHolder extends RecyclerView.ViewHolder {
        ImageView logo;
        TextView title, description, date;
        LinearLayout linearLayout;

        VideoViewHolder(View itemView) {
            super(itemView);
            logo = itemView.findViewById(R.id.logo);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            linearLayout = itemView.findViewById(R.id.l1);
//            date = itemView.findViewById(R.id.date);
        }
    }
}
