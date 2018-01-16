package com.example.jinhui.cardverification.ui;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.jinhui.cardverification.R;

public class BankCardActivity extends AppCompatActivity {

    private ListView mListView;
    private Toolbar mToolbar;
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank);
        initView();
        initListener();
        mListView = (ListView) findViewById(R.id.listview);
        mListView.setAdapter(new BankAdapter());
    }

    private void initListener() {
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BankCardActivity.this, AddBankActivity.class));
            }
        });
    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar_add_bink);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        // 这里不设置图片，不显示图片
        mToolbar.setNavigationIcon(R.mipmap.community_main_details_back);
        imageView = findViewById(R.id.iv_add_bank);

    }

    public class BankAdapter extends BaseAdapter {

        private int[] icons = new int[]{R.drawable.f1, R.drawable.f2, R.drawable.f3, R.drawable.f4,
                R.drawable.f5, R.drawable.f6, R.drawable.f7, R.drawable.f8};

        @Override
        public int getCount() {
            return icons.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            BankViewHolder holder;
            if (convertView == null) {
                holder = new BankViewHolder();
                convertView = LayoutInflater.from(BankCardActivity.this).inflate(R.layout.item_bank, null);
                holder.parent = convertView.findViewById(R.id.bg);
                holder.icon = (ImageView) convertView.findViewById(R.id.img);
                convertView.setTag(holder);
            } else {
                holder = (BankViewHolder) convertView.getTag();
            }
            holder.icon.setImageResource(icons[position]);
            final View bg = holder.parent;
            Palette.from(BitmapFactory.decodeResource(getResources(), icons[position])).generate(new Palette.PaletteAsyncListener() {
                @Override
                public void onGenerated(Palette palette) {
                    Palette.Swatch lightVibrantSwatch = palette.getDarkVibrantSwatch();
                    if (lightVibrantSwatch == null) {
                        for (Palette.Swatch swatch : palette.getSwatches()) {
                            if (null != swatch) {
                                lightVibrantSwatch = swatch;
                                break;
                            }
                        }
                    }
                    GradientDrawable drawable = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT,
                            new int[]{colorLight(lightVibrantSwatch.getRgb()), colorBurn(lightVibrantSwatch.getRgb())});
                    bg.setBackgroundDrawable(drawable);
                }
            });
            return convertView;
        }

        private int colorBurn(int rgb) {
            //加深颜色
            //int all= (int) (rgb*1.1f);
            //红色
            int  red = rgb>>16&0xFF;
            int green = rgb>>8&0xFF;
            int blue = rgb&0xFF;

            red = (int) Math.floor(red * (1 - 0.2));
            green = (int) Math.floor(green * (1 - 0.2));
            blue = (int) Math.floor(blue * (1 - 0.2));
            return Color.rgb(red, green, blue);

        }

        private int colorLight(int rgb) {
            //颜色变浅
            //int all= (int) (rgb*1.1f);
            //红色
            int red = rgb>>16&0xFF;
            int green = rgb>>8&0xFF;
            int blue = rgb&0xFF;

            red = (int) Math.floor(red * (1 + 0.2));
            green = (int) Math.floor(green * (1 + 0.2));
            blue = (int) Math.floor(blue * (1 + 0.2));
            return Color.rgb(red, green, blue);

        }
    }

    public class BankViewHolder {
        private View parent;
        private ImageView icon;
    }
}
