package com.example.android_studio_memo_pad;

public interface interfaceContract {
    interface interfaceView {
        void onDeleteMemo();
        void onAddMemo();
    }
    interface interfacePresenter {
        void addMemo(Memo memo);
        void deleteMemo(int id);
    }
}
