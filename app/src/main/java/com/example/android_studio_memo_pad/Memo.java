package com.example.android_studio_memo_pad;

public class Memo {
    private int id;
    private String memo;

    public Memo(int id, String memo) {
        this.id = id;
        this.memo = memo;
    }
    public Memo(String memo) {
        this.memo = memo;
    }

    public String getMemo() {return memo;}
    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
    public void setMemo(String memo) {this.memo = memo;}

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(id).append("\n");
        sb.append("Memo: ").append(memo).append("\n");
        return sb.toString();
    }
}
