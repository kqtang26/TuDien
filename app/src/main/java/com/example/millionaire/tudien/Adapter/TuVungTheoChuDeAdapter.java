package com.example.millionaire.tudien.Adapter;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.millionaire.tudien.R;
import com.example.millionaire.tudien.Class.TuVungTheoChuDe;

import java.util.List;

/**
 * Created by BoBo on 4/1/2017.
 */

public class TuVungTheoChuDeAdapter extends RecyclerView.Adapter<TuVungTheoChuDeAdapter.ViewHolder> {

    private Context context;
    private List<TuVungTheoChuDe> lst;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtViewTuKhoa, txtViewPhatAm, txtViewNghia;
        ImageView imgView;

        public ViewHolder(View itemView) {
            super(itemView);
            txtViewTuKhoa = (TextView) itemView.findViewById(R.id.txtViewTuKhoa);
            txtViewPhatAm = (TextView) itemView.findViewById(R.id.txtViewPhatAm);
            txtViewNghia = (TextView) itemView.findViewById(R.id.txtViewNghia);
            imgView = (ImageView) itemView.findViewById(R.id.imgView);
        }
    }

    public TuVungTheoChuDeAdapter(Context context, List<TuVungTheoChuDe> lst){
        this.context = context;
        this.lst = lst;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        return new ViewHolder(itemView);
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        final TuVungTheoChuDe tuVung = lst.get(position);
        holder.txtViewTuKhoa.setText(tuVung.getTuKhoa());
        holder.txtViewPhatAm.setText(tuVung.getPhatAm());
        holder.txtViewNghia.setText(tuVung.getNghia());
        CardView cardView = (CardView)  holder.itemView.findViewById(R.id.card_view);
        holder.imgView.setImageBitmap(tuVung.getHinhAnh());

        //Tạo kết nối âm thanh cho từng CardView.
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int temp = 0;
                String audio = tuVung.getTuKhoa().toLowerCase().replace(" ", "");
                audio = audio.replace("-","");

                //Lấy file audio theo tên.
                temp = context.getResources().getIdentifier(audio, "raw", context.getPackageName());

                //Lấy file audio theo tên mới nếu không tồn tại.
                if(temp == 0){
                    temp = context.getResources().getIdentifier(audio+"1", "raw", context.getPackageName());
                }

                //Thông báo không có audio tương ứng.
                if(temp == 0){
                    Toast.makeText(context, "Không có audio", Toast.LENGTH_SHORT).show();
                }
                else{
                    MediaPlayer mp = MediaPlayer.create(context, temp);
                    mp.start();
                    mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            mp.stop();
                            if (mp != null) {
                                mp.release();
                            }
                        }
                    });
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return lst.size();
    }
}
