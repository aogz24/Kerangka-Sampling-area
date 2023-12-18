package com.example.kerangkasamplingarea;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kerangkasamplingarea.model.JadwalResponse;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import retrofit2.Callback;

public class JadwalAdapter extends RecyclerView.Adapter<JadwalAdapter.ViewHolder> {

    private List<JadwalResponse> jadwalList;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public JadwalAdapter(List<JadwalResponse> jadwalList) {
        this.jadwalList = jadwalList;
        this.context = context;
    }

    public interface OnItemClickListener {
        void onItemClick(String itemId);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_jadwal, parent, false);
        return new ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        JadwalResponse jadwal = jadwalList.get(position);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String formattedDate = sdf.format(jadwal.getTanggalPendataan());

        holder.tanggalTextView.setText(formattedDate);
        holder.segmenTextView.setText(jadwal.getSegmen());
        holder.nkabTextView.setText(jadwal.getNkab());
        holder.nprovTextView.setText(jadwal.getNprov());
    }

    @Override
    public int getItemCount() {
        return jadwalList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements com.example.kerangkasamplingarea.ViewHolder {
        TextView tanggalTextView;
        TextView segmenTextView;
        TextView nkabTextView;
        TextView nprovTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tanggalTextView = itemView.findViewById(R.id.tanggalTextView);
            segmenTextView = itemView.findViewById(R.id.segmenTextView);
            nkabTextView = itemView.findViewById(R.id.nkabTextView);
            nprovTextView = itemView.findViewById(R.id.nprovTextView);
        }
    }
}
