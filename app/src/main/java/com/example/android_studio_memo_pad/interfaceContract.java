package com.example.android_studio_memo_pad;

public interface interfaceContract {
    public interface interfaceView {
        void addMemoToView(String memoText);
        void deleteMemoFromView();
        void updateRecyclerView(RecyclerViewAdapter adapter);
    }
    public interface interfacePresenter {

        void addMemo(Memo memo);
        void deleteMemo(int id);
        void updateRecyclerView(String memoText, boolean isAdd);
    }
}
