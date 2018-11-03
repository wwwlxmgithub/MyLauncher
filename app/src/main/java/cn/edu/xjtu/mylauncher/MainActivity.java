package cn.edu.xjtu.mylauncher;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private List<ResolveInfo> mApps;
    private GridView gridView;
    private GridViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridView = findViewById(R.id.gridView);
        loadApps();

        adapter = new GridViewAdapter();
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ResolveInfo info = mApps.get(position);

                //该应用的包名
                String pkg = info.activityInfo.packageName;
                //应用的主activity类
                String cls = info.activityInfo.name;

                ComponentName component = new ComponentName(pkg, cls);
                if (new Random().nextInt() % 2 == 0) {
                    Intent i = new Intent();
                    i.setComponent(component);
                    startActivityForResult(i, 120);
                } else {
                    Toast.makeText(MainActivity.this, "hello " + pkg, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 120:
                // 我回来啦
                Toast.makeText(MainActivity.this, "我回来啦", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void loadApps() {
        Intent mainIntent = new Intent(Intent.ACTION_MAIN);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        mApps = getPackageManager().queryIntentActivities(mainIntent, 0);
    }
    interface OnMyItemClickListener{
        void click(View view, int position);
        void click(View v);
    }
    class GridViewAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return mApps.size();
        }

        @Override
        public Object getItem(int position) {
            return mApps.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item_layout, parent, false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            ActivityInfo info = mApps.get(position).activityInfo;
            holder.imageView.setImageDrawable(info.loadIcon(getPackageManager()));
            holder.textView.setText(info.loadLabel(getPackageManager()));
            return convertView;
        }

        class ViewHolder {
            TextView textView;
            ImageView imageView;
            ViewHolder(View itemView) {
               textView = itemView.findViewById(R.id.appName);
                imageView = itemView.findViewById(R.id.appIcon);
                /*imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.out.println("fgfh");
                    }
                });*/
            }
        }
    }
}
