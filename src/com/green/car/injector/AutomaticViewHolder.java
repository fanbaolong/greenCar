package com.green.car.injector;

import android.view.LayoutInflater;
import android.view.View;

/**
 * 使用时请注意View类型
 *
 */
public abstract class AutomaticViewHolder {

    protected View root;

    public AutomaticViewHolder(View root) {
        this.root = root;
        if (root == null) {
            throw new NullPointerException("参数root不能为空");
        }
        init();
    }

    public AutomaticViewHolder(LayoutInflater inflater, int layout) {
        root = inflater.inflate(layout, null);
        if (root == null) {
            throw new NullPointerException("指定的layout(id:" + layout + ")不存在");
        }
        init();
    }

    public View getRootView() {
        return root;
    }

    @SuppressWarnings("unchecked")
    public <V extends View> V findViewById(int id) {
        return (V) root.findViewById(id);
    }

    @SuppressWarnings("unchecked")
    public <V extends View> V findViewById(View parent, int id) {
        return (V) parent.findViewById(id);
    }

    public void show() {
        root.setVisibility(View.VISIBLE);
    }

    public void hide() {
        root.setVisibility(View.GONE);
    }

    private void init() {

        AutomaticViewHolderUtil.findAllViews(this, root);
    }

    @SuppressWarnings("serial")
    public static class IllegalViewHolderException extends RuntimeException {

        IllegalViewHolderException(String msg) {
            super(msg);
        }

        IllegalViewHolderException() {
            super("使用Res标记字段应确保使用正确的类型，必须是View或其子类型，并且确保layout文件使用的控件是该字段的类型或该字段的子类型！");
        }
    }
}
