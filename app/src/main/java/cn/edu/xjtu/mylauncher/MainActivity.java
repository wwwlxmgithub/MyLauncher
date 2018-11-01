package cn.edu.xjtu.mylauncher;

import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<ResolveInfo> mApps;
    private RecyclerView recycleView;
    private RecyclerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recycleView = findViewById(R.id.recyclerView);
        loadApps();
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        recycleView.setLayoutManager(layoutManager);
        adapter = new RecyclerAdapter();
        recycleView.setAdapter(adapter);
        recycleView.setItemAnimator(new DefaultItemAnimator());
        System.out.println(mApps);
    }

    private void loadApps() {
        Intent mainIntent = new Intent(Intent.ACTION_MAIN);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        mApps = getPackageManager().queryIntentActivities(mainIntent, 0);
    }
    class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>{

        @Override
        public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item_layout, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            String name = mApps.get(position).activityInfo.loadLabel(getPackageManager()).toString();
            holder.textView.setText(name);
            holder.imageView.setImageDrawable(mApps.get(position).activityInfo.loadIcon(getPackageManager()));
        }

        @Override
        public int getItemCount() {
            return mApps == null ? 0 : mApps.size();
        }
        class ViewHolder extends RecyclerView.ViewHolder {
            TextView textView;
            ImageView imageView;
            public ViewHolder(View itemView) {
                super(itemView);
                textView = itemView.findViewById(R.id.appName);
                imageView = itemView.findViewById(R.id.appIcon);
            }
        }
    }
}
