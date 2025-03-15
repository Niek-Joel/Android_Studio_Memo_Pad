package com.example.android_studio_memo_pad;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import com.example.android_studio_memo_pad.databinding.MemoItemBinding;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private MemoActivityView activity;
    private MemoItemBinding binding;
    private List<Memo> data;

    public RecyclerViewAdapter(List<Memo> data, MemoActivityView activity) {
        super();
        this.data = data;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = MemoItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        binding.getRoot().setOnClickListener(activity.getItemClick()); // the click handler
        return new ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setMemo(data.get(position));
        holder.bindData();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public Memo getItem(int position) {
        return data.get(position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private Memo memo;
        private TextView memoLabel;

        public ViewHolder(View itemView) {
            super(itemView);
            memoLabel = itemView.findViewById(R.id.memoLabel);
        }


        public Memo getMemo() {
            return memo;
        }

        public void setMemo(Memo memo) {
            this.memo = memo;
        }


        public void bindData() {

            if (memoLabel == null) {
                memoLabel = (TextView) itemView.findViewById(R.id.memoText);
            }
            memoLabel.setText(memo.getMemo());

        }

    }

}
