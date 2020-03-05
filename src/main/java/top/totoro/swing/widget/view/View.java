package top.totoro.swing.widget.view;

import top.totoro.swing.widget.base.BaseAttribute;
import top.totoro.swing.widget.bean.ViewAttribute;
import top.totoro.swing.widget.context.Context;
import top.totoro.swing.widget.listener.OnClickListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

//import javax.swing.JComponent;
//import java.awt.Color;

public class View<Attribute extends BaseAttribute, Component extends JComponent> implements MouseListener {

    private int minWidth = 0;
    private int minHeight = 0;

    private View parent;
    private String parentId = "";
    private LinkedList<View> sonViews = new LinkedList<>();
    private Map<String, View> containViewsById = new ConcurrentHashMap<>();
    private top.totoro.swing.widget.base.LayoutManager layoutManager;
    private OnClickListener clickListener;
    protected Attribute attribute;
    protected Component component;
    protected Context context;

    public View(View parent) {
        this.parent = parent;
        if (parent != null && parent.attribute != null) {
            parentId = parent.attribute.getId();
            setContext(parent.context);
        }
    }

    public void addOnClickListener(OnClickListener clickListener) {
        this.clickListener = clickListener;
        component.addMouseListener(this);
    }

    public void removeAllSon() {
        sonViews.clear();
    }

    public LinkedList<View> getSonViews() {
        return sonViews;
    }

    public top.totoro.swing.widget.base.LayoutManager getLayoutManager() {
        return layoutManager;
    }

    public void setLayoutManager(top.totoro.swing.widget.base.LayoutManager layoutManager) {
        this.layoutManager = layoutManager;
    }

    /**
     * 当前布局刷新时，确定可以冒泡刷新的最大范围
     */
    protected void invalidateSuper() {
        if (context != null) {
            context.invalidate();
        } else if (getLayoutManager() != null) {
            getLayoutManager().invalidate();
        } else if (getParent() != null) {
            getParent().invalidate();
        } else {
            invalidate();
        }
    }

    public void setSize(int width, int height) {
        component.setSize(width, height);
    }

    public int getWidth() {
        return component.getWidth();
    }

    public int getHeight() {
        return component.getHeight();
    }

    /**
     * 添加一个子View，只有Layout需要添加子View
     *
     * @param son 子View
     */
    public void addSon(View son) {
        sonViews.add(son);
    }

    /**
     * 根据这个View下子View的位置，获取这个子View的ID
     *
     * @param index 子View的位置
     * @return 子View的ID，不存在则返回“”
     */
    public View getSonByIndex(int index) {
        return sonViews.size() == 0 ? null : sonViews.get(index);
    }

    /**
     * 获取父节点的ID
     *
     * @return 父节点ID，不存在则返回“”
     */
    public String getParentId() {
        return parentId == null ? "" : parentId;
    }

    /**
     * 获取父View，正常的话是个Layout
     *
     * @return
     */
    public View getParent() {
        return parent;
    }

    /**
     * 获取这个View的ID
     *
     * @return id，默认是创建这个View的系统时间，或在xml或通过setID指定
     */
    public String getId() {
        return attribute == null ? "" : attribute.getId();
    }

    public Component getComponent() {
        return component;
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setAttribute(Attribute attribute) {
        if (attribute == null) return;
        this.attribute = attribute;
//        setId(attribute.getId());
        component.setVisible(attribute.getVisible() == ViewAttribute.VISIBLE);
        component.setOpaque(attribute.getOpaque() == ViewAttribute.OPAQUE);
        if (attribute.getBackground().startsWith("#")) {
            component.setBackground(Color.decode(attribute.getBackground()));
        }
    }

    public int getMinWidth() {
        return minWidth;
    }

    public int getMinHeight() {
        return minHeight;
    }

    public void setMinWidth(int minWidth) {
        this.minWidth = minWidth;
    }

    public void setMinHeight(int minHeight) {
        this.minHeight = minHeight;
    }

    /**
     * 自定义View的ID
     *
     * @param id 新的ID
     */
    public void setId(String id) {
        try {
            if (!bindViewWithId(id, this)) {
                throw new Exception("setId时，" + id + "无法被设置。");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 将View和ID进行绑定，成功的话View的所有父节点都将更新这个View的ID
     *
     * @param id   将要绑定的id
     * @param view 将要绑定的View
     * @return 绑定是否成功，如果失败的话，其所有父节点也不会修改这个View的ID
     */
    private boolean bindViewWithId(String id, View view) {
        View v = containViewsById.get(id);
        if (v != null) {
            return false;
        } else {
            if (parent != null) {
                if (parent.bindViewWithId(id, view)) {
                    containViewsById.put(id, view);
                } else {
                    return false;
                }
            } else {
                containViewsById.put(id, view);
            }
        }
        return true;
    }

    public View findViewById(String id) {
        View view = containViewsById.get(id);
        try {
            if (view == null) throw new Exception("找不到id为" + id + "的View");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    public void setBackgroundColor(Color bg) {
        attribute.setBackground(bg.toString());
        component.setBackground(bg);
    }

    public void invalidate() {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        clickListener.onClick(this);
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (clickListener != null) {
            component.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (clickListener != null) {
            component.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }

    public void setParent(View parent) {
        this.parent = parent;
    }
}